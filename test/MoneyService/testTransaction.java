package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;

import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.Transaction;
import affix.java.project.moneyservice.TransactionMode;

public class testTransaction {
	static Transaction testTransaction;
	static Order tempOrder;
	static LocalDateTime testTime;
	@BeforeClass
	public static void beforeTest() {
		tempOrder = new Order(500, "AUD", TransactionMode.BUY);
		testTransaction = new Transaction(tempOrder);
		 testTime = testTransaction.getTimeStamp();
	}
	@SuppressWarnings("static-access")
	@Test
	public void testgetUniqueId() {
		int tempId = testTransaction.getUniqueId();
		assertEquals(tempId, testTransaction.getUniqueId());
	}
	@SuppressWarnings("static-access")
	@Test
	public void testSetUniqueId() {
		int testId = 5;
		testTransaction.setUniqueId(testId);
		assertEquals(testId, testTransaction.getUniqueId());
	}
	@Test
	public void testGetId() {
		int tempId = testTransaction.getId();
		assertEquals(tempId, testTransaction.getId());
	}
	@Test
	public void testGetLocalDateTime() {
		assertEquals(testTime, testTransaction.getTimeStamp());
	}
	@Test
	public void testGetCurrencyCode() {
		String currency = "AUD";
		assertEquals(currency, testTransaction.getCurrencyCode());
	}
	@Test
	public void testGetAmount() {
		int amount = 500;
		assertEquals(amount, testTransaction.getAmount());
	}
	@Test
	public void testGetMode() {
		TransactionMode test = TransactionMode.BUY;
		assertEquals(test, testTransaction.getMode());
	}
	@Test
	public void testToString() {
		int id = testTransaction.getId();
		LocalDateTime testTime = testTransaction.getTimeStamp();
		String currency = "AUD";
		int amount = testTransaction.getAmount();
		TransactionMode test = testTransaction.getMode();
		String output = "Transaction [id=" + id+ ", timeStamp="+ testTime+ ", currencyCode=" + currency + ", amount=" + amount + ", mode=" + test + "]"; 
		assertEquals(output, testTransaction.toString());
	}

}
