package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.ExchangeSite;
import affix.java.project.moneyservice.MoneyServiceIO;
import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.Report;
import affix.java.project.moneyservice.Transaction;
import affix.java.project.moneyservice.TransactionMode;

public class testReport {

	static ExchangeSite theSite = new ExchangeSite("NORTH");
	static Report testReport;
	@SuppressWarnings("static-access")
	@BeforeClass
	public static void beforeTest() {
		
		Config.readConfigFile("configFileNorthTest.txt");
		

		theSite.startTheDay();
		List<Order> listOfOrders;
		int i=0;
		boolean stop = false;
		do {
			System.out.println(theSite.getRates());
			listOfOrders = Order.generateDailyOrder(theSite.getRates(), 35);
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
		testReport = new Report(LocalDateTime.of(2021, 04, 10, 06, 00), theSite.getTransactionList());
	}
	@Test
	public void test() {
		
	}
	@Test
	public void testGetDay() {
		LocalDateTime now = LocalDateTime.of(2021, 04, 10, 06, 00);
		assertEquals(now, testReport.getDay());
		
	}
	
	@Test
	public void testSetDay() {
		LocalDateTime testDate = LocalDateTime.of(2021, 04, 10, 06, 00);
		testReport.setDay(LocalDateTime.of(2021, 04, 10, 06, 00));
		assertEquals(testDate, testReport.getDay());
	}

	@Test
	public void testGetDalyTransaction() {
		List<Transaction> testList = theSite.getTransactionList();
		assertEquals(testList.toString(), testReport.getDailyTransaction().toString());
	}
	@SuppressWarnings("static-access")
	@Test
	public void testSetDalyTransaction() {
		List<Transaction> testList = theSite.getTransactionList();
		testReport.setDailyTransaction(testList);
		assertEquals(testList.toString(), testReport.getDailyTransaction().toString());
	}
	
	@Test
	public void testGetUniqueId() {
	long testId = testReport.getId();
	assertEquals(testId, testReport.getId());
	}
	
	@Test
	public void testTostring() {
		long testId = testReport.getId();
		LocalDateTime testDay= testReport.getDay();
		List<Transaction> testList = testReport.getDailyTransaction();
		assertEquals("Rapport [id="+testId +", " + "day="+ testDay + ", " + "dailyTransactions="+ testList+"]", testReport.toString());
	}
	@Test
	public void testGetUniqueFilename() {
		String fileName = "Report_" + ExchangeSite.name.toString().trim()+"_" + LocalDate.of(2021, 04, 10) +".ser" ;
		assertEquals(fileName, testReport.getUniqueFileName());
	}
}
