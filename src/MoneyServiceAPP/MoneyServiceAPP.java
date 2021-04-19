package MoneyServiceAPP;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import MoneyService.Currency;
import MoneyService.ExchangeRate;
import MoneyService.ExchangeSite;
import MoneyService.MoneyBox;
import MoneyService.MoneyServiceIO;
import MoneyService.Order;
import MoneyService.Transaction;
import MoneyService.Rapport;

public class MoneyServiceAPP {

	public static void main(String[] args) {

		
		//TODO Create Menu system

		//Start the day, fill the box and set the rates.

	//	ExchangeSite theSite = new ExchangeSite();
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();
		MoneyBox theBox = new MoneyBox(currencyMap);
		List<ExchangeRate> rates = new ArrayList<ExchangeRate>();

		fillTheMoneyBox(theBox, currencyMap);

		rates=setTheRates();

		Currency.setRates(rates, currencyMap);
		int price= Currency.calculatePrice("EUR", 500);
		Currency.getExchangeRate("EUR");
		
		//Creaeate a order
//		Order myOrder = new Order(500,"EUR", typeOfTransaction.BUY);
//
//		//TODO add function to return the cost of the amount bought
//
//		List<Transaction> DailyTransactions = new ArrayList<>();

//		//Pass the order to buyMoney, and put it in list of transactions. 
//		if(theSite.buyMoney(myOrder)) {
//			Transaction testTransaction = new Transaction(myOrder.getCurrencyCode(), myOrder.getValue(),myOrder.getTransactionType());
//			DailyTransactions.add(testTransaction);
//		}

		 
//		List<Transaction> DailyTransactions = new ArrayList<>();
//		Transaction t1 = new Transaction("Eur", 3400, typeOfTransaction.BUY);
//		Transaction t2 = new Transaction("Eur", 5000, typeOfTransaction.SELL);
//		Transaction t3 = new Transaction("Eur", 7500, typeOfTransaction.SELL);
//		DailyTransactions.add(t1);
//		DailyTransactions.add(t2);
//		DailyTransactions.add(t3);
//
//		//		int i = 0;
//		//		System.out.println("These are transactions from the list:");
//		//		for(Transaction t: DailyTransactions) {
//		//			System.out.println(t.toString());
//		//			System.out.println(i++);
//		//		}
//
//		for(int i1 = 0, j = 0; i1<100;i1++, j++) {
//			Transaction T = new Transaction("SEK",j, typeOfTransaction.BUY);
//			DailyTransactions.add(T);
//		}
//		/*
//		System.out.println(t1.toString());
//		System.out.println(t2.toString());
//		System.out.println(t3.toString());
//
//		System.out.println(t1.getId());
//		System.out.println(t2.getId());
//		System.out.println(t3.getId());
//
//		System.out.println(t1.getTimeStamp().toLocalDate());
//		System.out.println(t2.getTimeStamp().toLocalDate());
//		System.out.println(t3.getTimeStamp().toLocalDate());
//
//
//		System.out.println(t1.getAmount());
//		System.out.println(t2.getAmount());
//		System.out.println(t3.getAmount());
//
//		System.out.println(t1.getCurrencyCode());
//		System.out.println(t2.getCurrencyCode());
//		System.out.println(t3.getCurrencyCode());
//		 */
//		ExchangeSite ES = new ExchangeSite();
//		ES.name = "NORTH";
//		Rapport R = new Rapport(t1.getTimeStamp(),DailyTransactions);
//		MoneyServiceIO.saveSerializedReport(R);
	}

	public static List<ExchangeRate> setTheRates() {
		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.currencyConfigFilename)));
		for(ExchangeRate er:test) {
			System.out.println(er.toString());
		}		
		return test;
	}

	public static MoneyBox fillTheMoneyBox(MoneyBox theBox, Map<String, Currency> currencyMap ) {
		Map<String, Integer> testMap = new HashMap<String,Integer>(MoneyServiceIO.parseProjectConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.projectConfigFilename)));

		Set<String> keySet = testMap.keySet();
		for(String k:keySet) {
			Currency tempCurrency = new Currency(testMap.get(k).intValue(), 0.0f, 0.0f);
			currencyMap.putIfAbsent(k, tempCurrency);
			System.out.println(k.toString());
		}
		return theBox;
	}

}
