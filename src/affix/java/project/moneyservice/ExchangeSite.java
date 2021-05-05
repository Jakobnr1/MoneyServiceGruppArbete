package affix.java.project.moneyservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;





public class ExchangeSite implements MoneyService {
	public static String name;
	public static List <Transaction> transactionList = new ArrayList<>();
	private Report backupReport;
	private static MoneyBox theBox;
	private static Map<String, Currency> currencyMap;
	private static List<ExchangeRate> rates;

	private static Logger logger;

	static {
		logger = Logger.getLogger(Config.getLogName());
	}
	public ExchangeSite(String Name) {
		this(Name,LocalDateTime.now());
	}


	public ExchangeSite(String Name, LocalDateTime TimeStamp) {
		ExchangeSite.name = Name;
		backupReport = new Report(TimeStamp, transactionList);
		ExchangeSite.currencyMap = new TreeMap<String, Currency>();
		ExchangeSite.theBox= new MoneyBox(currencyMap);
		ExchangeSite.rates = new ArrayList<ExchangeRate>();
	}

	public void startTheDay() {
		logger.fine("Starting the day!");
		Config.fillTheMoneyBox(ExchangeSite.theBox, ExchangeSite.currencyMap);

		ExchangeSite.rates = Config.setTheRates();

		Config.setRatesInCurrency(ExchangeSite.rates, currencyMap);
	}

	/**
	 * Skip the one in Order and use this one instead to get price.
	 * @param currencyCode
	 * @param amount
	 * @return int price
	 */
	public static int calculatePrice(String currencyCode, int amount,TransactionMode transactionType) {
		Map<String, Currency> currencyMap= MoneyBox.getCurrencyMap();
		float calcPrice =0;
		double price =0;

		if(transactionType == TransactionMode.SELL) {
			calcPrice = currencyMap.get(currencyCode).sellRate.floatValue();

		}
		else if(transactionType == TransactionMode.BUY) {
			calcPrice = currencyMap.get(currencyCode).buyRate.floatValue();
		}
		price = amount*calcPrice;
		logger.finer("Calculate price for: "+currencyCode+ " "+amount+" price: "+(int) Math.round(price));
		return (int) Math.round(price);
	}

	@Override
	public boolean buyMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();

		String refCurrency = MoneyServiceIO.getReferenceCurrency();
		Optional<Double> totalRefCurrency = getAvailableAmount(refCurrency);
		Optional<Double> customerCur = getAvailableAmount(currency);


		if(customerCur.isEmpty() || totalRefCurrency.isEmpty()) {
			return false;
		}
		double exRate = calculatePrice(currency, value,TransactionMode.BUY);

		return (exRate<totalRefCurrency.get())?true : false;


	}

	@Override
	public boolean sellMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue(); 
		String currency = orderData.getCurrencyCode(); 

		Optional<Double> totalRefCurrency = getAvailableAmount(currency);

		if(totalRefCurrency.isEmpty()) {
			return false;	
		}

		return (value<=totalRefCurrency.get())?true : false;
	}

	@Override
	public void printSiteReport(String destination) {
		MoneyServiceIO.saveTxtMoneyBox(MoneyBox.getCurrencyMap(), "SiteReport_"+Config.getSiteName()+"_"+LocalDate.now()+".txt");
	}

	@Override
	public void shutDownService(String destination) {

		if(destination.contains(".txt")) {
			logger.fine("Saving daily transactions as text");
			MoneyServiceIO.saveDailyTransactionListAsText(transactionList, backupReport.getUniqueFileName());
		}else if(destination.contains(".db")) {
			logger.fine("Saving daily transactions as serialized");
			MoneyServiceIO.saveSerializedDailyTransactions(transactionList, backupReport.getUniqueFileName());
		}
		MoneyServiceIO.saveDailyTransactionListAsText(transactionList, "tempFile.txt");
		
		printSiteReport(destination);
		
	}

	@Override
	public Map<String, Currency> getCurrencyMap() {
		Map<String,Currency> tempMap = MoneyBox.getCurrencyMap();
		return tempMap; 
	}

	@Override
	public Optional<Double> getAvailableAmount(String currencyCode) 
	{

		if(MoneyBox.getCurrencyMap().get(currencyCode) == null) {

			return Optional.empty();
		}
		Optional <Double> temp = Optional.of(MoneyBox.getCurrencyMap().get(currencyCode).getTotalValue());
		return temp;

	}

	public Order completeOrder(Order orderData) {		
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();

		Optional<Double> customerCur = getAvailableAmount(currency);
		String refCurrency = MoneyServiceIO.getReferenceCurrency();
		Optional<Double> companyCur = getAvailableAmount(refCurrency);


		if(orderData.getTransactionType() == TransactionMode.BUY) {
			int buy = customerCur.get().intValue();
			MoneyBox.getCurrencyMap().get(currency).setTotalValue(value + buy);
			logger.finest("Adding "+buy+" "+currency+ " to theBox. Total in box after: "+MoneyBox.getCurrencyMap().get(currency).getTotalValue());
			int sell = companyCur.get().intValue();
			int total = calculatePrice(currency, value,TransactionMode.BUY);
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(sell - total);
			logger.finest("Selling "+sell+" "+companyCur+ " total in box after: "+MoneyBox.getCurrencyMap().get(refCurrency).getTotalValue());
			transactionList.add(new Transaction(orderData));
		}

		else if(orderData.getTransactionType() == TransactionMode.SELL) {
			int sell = customerCur.get().intValue();
			MoneyBox.getCurrencyMap().get(currency).setTotalValue(sell - value);
			int ourCurrency = companyCur.get().intValue();
			int total = calculatePrice(currency, value,TransactionMode.SELL);
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(ourCurrency + total);
			transactionList.add(new Transaction(orderData));
		}
		logger.fine("Completed order: "+orderData.toString());
		return orderData;
	}

	/**
	 * @param "d" Order
	 * @return A list of Order from d
	 */
	public static List<Order> addOrderToQueue(Order d){
		List<Order> orderList = new LinkedList<Order>();
		orderList.add(d);


		return orderList;
	}

	/**
	 * @param orderList. A list of Orders
	 * @return orderList. Updated a list of orders that didn't go trough.
	 */
	public List<Order> processOrderQueue(List<Order> orderList){
		for(Order o : orderList)
		{
			if(o.getTransactionType() == (TransactionMode.BUY)) {
				if(buyMoney(o)) {
					completeOrder(o);
					orderList.remove(o);
				}
				else {
					//System.out.println();
					logger.finer("Order :"+ o +" did not go trough");
				}
			}
			else if(o.getTransactionType() == (TransactionMode.SELL)) {
				if(sellMoney(o)) {
					completeOrder(o);
					orderList.remove(o);
				}
				else {
				//	System.out.println("Order :"+ o +" did not go trough");
					logger.finer("Order :"+ o +" did not go trough");
				}
			}
		}
		return orderList;
	}


	public static List<Transaction> getTransactionList(){
		return transactionList;
	}



	public static List<ExchangeRate> getRates() {
		return rates;
	}

	public void setName(String name) {
		ExchangeSite.name = name;
	}

}