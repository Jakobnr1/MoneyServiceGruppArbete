package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
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
		Config.readConfigFile("configFileNorthTest.txt");
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
		List <ExchangeRate> testList = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.getPathName("DalyRates")+ "CurrencyConfig_2021-04-01.txt")));
		
		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.getPathName("DalyRates")+ "CurrencyConfig_2021-04-01.txt")));
		
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
		assertTrue(MoneyServiceIO.saveTxtMoneyBox(theSite.getCurrencyMap(), fileName));
		
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void testSaveSerializedReport() {

		Report report = new Report(LocalDateTime.now(), theSite.getTransactionList());
		assertTrue(MoneyServiceIO.saveSerializedReport(report));
	}
	
	@Test
	public void testPrintPathList() {
		MoneyServiceIO.printPathList();
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
	
	@Test
	public void testSetRefDate() {
		LocalDate now = MoneyServiceIO.refDate;
		MoneyServiceIO.setRefDate(LocalDate.of(2021, 01, 01));
		assertFalse(now==MoneyServiceIO.refDate);
	}
	
	
	@Test
	public void testReadSerialzedFail() {
		String test ="";
		MoneyServiceIO temp = new MoneyServiceIO();
		temp.readSerializedDailyTransactionList(test);
	
	}
	@Test
	public void testStoreSerialzedFail() {
		List<Transaction> testList = new ArrayList<>();
		String test = "";
		MoneyServiceIO.saveDailyTransactionListAsText(testList, test);
		
	
	}
	
	@Test
	public void testGetTransactionFolderPath() {
		String expected = "Transactions/NORTH/";
		String recived = MoneyServiceIO.getPathName("Transactions");
		assertEquals(expected, recived);
	}
	
}
