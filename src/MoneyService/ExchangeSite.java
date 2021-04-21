package MoneyService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import MoneyService.Order.TransactionMode;

public class ExchangeSite implements MoneyService {
	public static String name;
	public static List <Transaction> transactionList = new ArrayList<>();
	private Report backupReport;
	private static MoneyBox theBox;
	private static Map<String, Currency> currencyMap;
	private static List<ExchangeRate> rates;	


	public ExchangeSite(String Name) {
		ExchangeSite.name = Name;
		backupReport = new Report(LocalDateTime.now(), transactionList);
		ExchangeSite.currencyMap = new TreeMap<String, Currency>();
		ExchangeSite.theBox= new MoneyBox(currencyMap);
		ExchangeSite.rates = new ArrayList<ExchangeRate>();
	}

	public void startTheDay() {
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
	public static int calculatePrice(String currencyCode, int amount) {

		Map<String, Currency> currencyMap= MoneyBox.getCurrencyMap();
		float calcPrice = currencyMap.get(currencyCode).sellRate.floatValue();

		double price = amount*calcPrice;
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
		double exRate = calculatePrice(currency, value);
		
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

		if(destination.contains("syso")) {
			for(int i = 0; i < transactionList.size(); i++) {
				System.out.println(transactionList.get(i));
			}
		}else if(destination.contains(".txt")) {
			MoneyServiceIO.saveDailyTransactionListAsText(transactionList, backupReport.getUniqueFileName());
		}
	}

	@Override
	public void shutDownService(String destination) {

		if(destination.contains(".txt")) {
			MoneyServiceIO.saveDailyTransactionListAsText(transactionList, backupReport.getUniqueFileName());
		}else if(destination.contains(".db")) {
			MoneyServiceIO.saveSerializedDailyTransactions(transactionList, backupReport.getUniqueFileName());
		}
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

	public void completeOrder(Order orderData) {		
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();

		Optional<Double> customerCur = getAvailableAmount(currency);
		String refCurrency = MoneyServiceIO.getReferenceCurrency();
		Optional<Double> companyCur = getAvailableAmount(refCurrency);


		if(orderData.getTransactionType() == TransactionMode.BUY) {
			int buy = customerCur.get().intValue();
			MoneyBox.getCurrencyMap().get(currency).setTotalValue(value + buy);
			int sell = companyCur.get().intValue();
			int total = calculatePrice(currency, value);
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(sell - total);
			transactionList.add(new Transaction(orderData));
		}

		else if(orderData.getTransactionType() == TransactionMode.SELL) {
			int sell = customerCur.get().intValue();
			MoneyBox.getCurrencyMap().get(currency).setTotalValue(sell - value);
			int ourCurrency = companyCur.get().intValue();
			int total = calculatePrice(currency, value);
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(ourCurrency + total);
			transactionList.add(new Transaction(orderData));
		}
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
