package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import affix.java.project.moneyservice.Currency;
import affix.java.project.moneyservice.ExchangeRate;
import affix.java.project.moneyservice.ExchangeSite;
import affix.java.project.moneyservice.MoneyServiceIO;
import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.Report;
import affix.java.project.moneyservice.Transaction;
import affix.java.project.moneyservice.TransactionMode;

public class testMoneyServiceIO {

	List <String> filenameList = new ArrayList<>();
	Map<String, Double> testMap = new HashMap<String,Double>
	(MoneyServiceIO.parseProjectConfig
			(MoneyServiceIO.readTextFiles
			(MoneyServiceIO.projectConfigFilename)));
	static ExchangeSite theSite = new ExchangeSite("North");
	@BeforeClass
	public static void preCode() {
		theSite.startTheDay();
		List<Order> listOfOrders;
		int i=0;
		boolean stop = false;
		do {
			listOfOrders = Order.generateDailyOrder(ExchangeSite.getRates(), 35);
			for(Order d: listOfOrders) {
				if(i>24) {
					stop = true;
				}else {
					if(d.getTransactionType() == TransactionMode.BUY) {
						System.out.println("Tried: "+d.toString());
						if(theSite.buyMoney(d)) {
							theSite.completeOrder(d);
							i++;
							System.out.println(i + ": Worked");
						}
					}else {
						System.out.println("Tried: "+d.toString());
						if(theSite.sellMoney(d)) {
							theSite.completeOrder(d);
							i++;
							System.out.println(i + ": Worked");
						}
					}
				}
			}

		}while(!stop);

		for(Transaction t : ExchangeSite.transactionList) {
			System.out.println(""+t.toString());
		}

	}
	@Test
	public void testParseProjectConfig() {
		filenameList = MoneyServiceIO.readTextFiles(MoneyServiceIO.projectConfigFilename);
		Map<String, Double> tempcurrencyMap = new HashMap<>();
		tempcurrencyMap = MoneyServiceIO.parseProjectConfig(filenameList);
		assertEquals(tempcurrencyMap, testMap);
	}
	
	@Test
	public void testGetRefCurrency() {
		String tempRefCurrency = "SEK";
		
		
		assertEquals(tempRefCurrency, MoneyServiceIO.getReferenceCurrency());
	}

	@Test
	public void tesParceCurrencyConfig() {
		List <ExchangeRate> testList = new ArrayList<ExchangeRate>();
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 1, "AUD", 6.5024f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 100, "CHF", 917.305f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 100, "DKK", 136.1902f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 1, "EUR", 10.1273f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 1, "GBP", 11.6702f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 100, "JPY", 7.7879f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 100, "NOK", 100.9289f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 100, "RUB", 11.1971f));
		testList.add(new ExchangeRate(LocalDate.of(2021,04,27), 1, "USD", 8.4066f));

		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.currencyConfigFilename)));
		
		assertEquals(testList.toString(), test.toString());
	}
	@Test
	public void testParseConfigFail() {
		List <String> testList = new ArrayList<String>();
		testList.add("2021-04-19 10,75t 123 hello");
		
		MoneyServiceIO.parseCurrencyConfig(testList);
	}
	@Test
	public void testReadFilesFail() {
		String test = "";
		MoneyServiceIO.readTextFiles(test);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSaveSerializedDalyTranactions() {
				
		String filename = "TestFile.ser";
		assertTrue(MoneyServiceIO.saveSerializedDailyTransactions(theSite.getTransactionList(), filename));
	}
	@Test
	public void testSaveSerializedDalTransFali() {
		String fileName= "";
		List <Transaction> emptyList = new ArrayList<Transaction>();
		assertFalse(MoneyServiceIO.saveSerializedDailyTransactions(emptyList, fileName));
	}
	
	@Test
	public void testSaveSerializedCurrencyMap() {

		String fileName = "test2.txt";
		assertTrue(MoneyServiceIO.saveSerializedCurrencyMap(theSite.getCurrencyMap(), fileName));
		
	}
	@Test
	public void testSaveSerializedCurrencyMapFail() {

		String fileName = "";
		Map <String, Currency> emptyMap = new HashMap<String, Currency>();
		assertFalse(MoneyServiceIO.saveSerializedCurrencyMap(emptyMap, fileName));
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSaveSerializedReport() {

		Report report = new Report(LocalDateTime.now(), theSite.getTransactionList());
		assertTrue(MoneyServiceIO.saveSerializedReport(report));
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void testReadSerializedDalyTransactionList() {

		MoneyServiceIO.saveSerializedDailyTransactions(theSite.getTransactionList(), "TestList3.txt");
		List <Transaction> testList = theSite.getTransactionList();
		assertEquals(testList.toString(), MoneyServiceIO.readSerializedDailyTransactionList("TestList3.txt").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSaveDalyTransactionListAsTesxt() {
		String fileName ="testTesxt4.txt";
		assertTrue(MoneyServiceIO.saveDailyTransactionListAsText(theSite.getTransactionList(), fileName));
	}
	

}
