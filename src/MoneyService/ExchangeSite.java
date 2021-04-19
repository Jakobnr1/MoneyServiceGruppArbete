package MoneyService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import MoneyService.MoneyServiceIO;
import MoneyService.Order.TransactionMode;

public class ExchangeSite implements MoneyService {
	public static String name;
	public static List <Transaction> transactionList = new ArrayList<>();
	private Rapport backupReport = new Rapport(LocalDateTime.now(), transactionList);


	/*
//public ExchangeSite(String name, long id, Map<String, List<Currency>> currencyMap) {
//		super();
//		// TODO Auto-generated constructor stub
//	}

//	private final long id;

//	Map<String,Float> exchangeRates;

	public ExchangeSite(long id, Map<String, Float> exchangeRates) {
//		this.id = id;
//		this.exchangeRates = exchangeRates;
	}


	public static boolean parseOrder(int value) {

		if(value>)


		return false;
	}


	public Map<String, Float> getExchangeRates() {
		return exchangeRates;
	}

	public void setExchangeRates(Map<String, Float> exchangeRates) {
		this.exchangeRates = exchangeRates;
	}

	public long getId() {
		return id;
	}
//	public Map<String,Double> tempMap;

	 */

	// Doublecheck this/test
	@Override
	public boolean buyMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();

		int totalCurrency = MoneyBox.getCurrencyMap().get(currency).getTotalValue();

		return (value<totalCurrency)?true : false;
	}

	// Doublecheck this/test
	@Override
	public boolean sellMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue();
		String currency = MoneyServiceIO.getReferenceCurrency();

		int totalCurrency = MoneyBox.getCurrencyMap().get(currency).getTotalValue();
		//TODO maybe change float to double?

//		float totalPrice = Currency.calculatePrice(orderData.getCurrencyCode(), value, Currency.getExchangeRate(orderData.getCurrencyCode()));// TODO this method needs to be created if currency should hold exchange rates
		float totalPrice = Currency.calculatePrice(orderData.getCurrencyCode(), value);
		//		 float totalPrice = Order.calculatePrice(orderData.getCurrencyCode(), value, Config.getExchangeRateList());

		return (totalPrice<totalCurrency)?true : false;
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
		// TODO do we need this?????

		return tempMap; 
	}

	@Override
	public Optional<Double> getAvailableAmount(String currencyCode) 
	{

		Double temp= (double)MoneyBox.getCurrencyMap().get(currencyCode).getTotalValue();

		if(temp != 0){//Should be null???? if null else = dead code

			return Optional.of(temp);
		}else {
			return Optional.empty(); 
		}
	}

	public static void completeOrder(Order orderData) {
		Map<String,Currency> tempMap = MoneyBox.getCurrencyMap();
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();
		String refCurrency = MoneyServiceIO.getReferenceCurrency();
		int totalCurrency = MoneyBox.getCurrencyMap().get(currency).getTotalValue();
		int totalRefCurrency = MoneyBox.getCurrencyMap().get(refCurrency).getTotalValue();
		if(orderData.getTransactionType() == TransactionMode.BUY) {

			MoneyBox.getCurrencyMap().get(currency).setTotalValue(totalCurrency-value);
			float exRate = tempMap.get(refCurrency).getSellRate(); //Changed to getSellRate() Karl
			float total = value * exRate;//* 1.005F; //Have already calculated *1.005F in currency! Karl
			//TODO correct round of Float to int..
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(totalRefCurrency + (int)total);
			// TODO add transaction to list.
			transactionList.add(new Transaction(orderData));
		}else if(orderData.getTransactionType() == TransactionMode.SELL) {
			MoneyBox.getCurrencyMap().get(currency).setTotalValue(totalCurrency+value);
			float exRate = tempMap.get(currency).getBuyRate(); //Changed this to getBuyRate() Karl
			float total = value * exRate* 0.995F;
			//TODO correct round of Float to int..
			MoneyBox.getCurrencyMap().get(refCurrency).setTotalValue(totalRefCurrency - (int)total);
			// TODO add transaction to list.
			transactionList.add(new Transaction(orderData));
		}

	}

	public static List<Transaction> getTransactionList(){
		return transactionList;
	}



}
