package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.ExchangeSite;
import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.TransactionMode;


public class testOrder {

	static ExchangeSite theSite = new ExchangeSite("North");
	static Order testOrder;
	@BeforeClass
	public static void beforeTest() {
		Config.readConfigFile("configFileNorth.txt");
		
		testOrder = new Order(150, "AUD", TransactionMode.BUY);
		theSite.startTheDay();

	}
	@Test
	public void testGetValue() {
		int value = 150;
		assertEquals(value , testOrder.getValue());
		
	}
	@Test
	public void testGetTransactionMode() {
		TransactionMode transaction = TransactionMode.BUY;
		assertEquals(transaction , testOrder.getTransactionType());
	}
	@Test
	public void testGetCurrencyCode() {
		String currency = "AUD";
		assertEquals(currency, testOrder.getCurrencyCode());
		
	}
	@Test
	public void testToString() {
		String test = new Order(150, "AUD", TransactionMode.BUY).toString();
		assertEquals(test, testOrder.toString());
	}
	@Test
	public void testGenerateorder() {
		List<Order> testOrder = new ArrayList<>();
		testOrder = Order.generateDailyOrder(theSite.getRates(), 10);
		assertEquals(10, testOrder.size());
	}


}
