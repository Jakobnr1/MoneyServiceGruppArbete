package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.Currency;
import affix.java.project.moneyservice.ExchangeRate;
import affix.java.project.moneyservice.ExchangeSite;
import affix.java.project.moneyservice.MoneyBox;
import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.Transaction;
import affix.java.project.moneyservice.TransactionMode;



public class TestExchangeSite {
	ExchangeSite tempExchangeSite = new ExchangeSite("North");
	Order tempOrder;
	Order tempOrderAnotherOne;
	List<Order> orderList = new ArrayList<>();
	@BeforeClass
	public static void preCode() {
		Config.readConfigFile("configFileNorth.txt");

	}
	@Test
	public void testBuyMoneyTrue() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500, "EUR", TransactionMode.BUY);
		
		assertTrue(tempExchangeSite.buyMoney(tempOrder)); 	
	}
	
	@Test
	public void testBuyMoneyFalse() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(6000, "EUR", TransactionMode.BUY);

		assertFalse(tempExchangeSite.buyMoney(tempOrder));
	}
	
	@Test
	public void testBuyMoneyCustomerCurEmpty() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500,"Jesus",TransactionMode.BUY);
		
		
		assertFalse(tempExchangeSite.buyMoney(tempOrder));
	}
	
	
	@Test
	public void testSellMoneyTrue() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(1000, "EUR", TransactionMode.SELL);
		
		assertTrue(tempExchangeSite.sellMoney(tempOrder));
	}
	
	@Test
	public void testSellMoneyFalse() {
		tempExchangeSite.startTheDay();

		tempOrderAnotherOne = new Order(5005, "AUD", TransactionMode.SELL);
		
		
		assertFalse(tempExchangeSite.sellMoney(tempOrderAnotherOne));
	}
	
	@Test
	public void testSellMoneyWrongCur() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500,"Jesus",TransactionMode.SELL);
		
		
		assertFalse(tempExchangeSite.sellMoney(tempOrder));
	}
	
	@Test
	public void testPrintSiteReportTxt() {
		
		tempExchangeSite.printSiteReport(".txt");
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPrintSiteReportSyso() {
		tempExchangeSite.startTheDay();
		
		tempOrder = new Order(100, "AUD", TransactionMode.BUY);
		tempOrderAnotherOne = new Order(100, "AUD", TransactionMode.BUY);
		
			tempExchangeSite.transactionList.add(new Transaction(tempOrder));
			tempExchangeSite.transactionList.add(new Transaction(tempOrderAnotherOne));
			
		tempExchangeSite.printSiteReport("syso");
	}
	
	@Test
	public void testShutDownServiceTxt() {
		tempExchangeSite.shutDownService(".txt");
	
	}
	
	@Test
	public void testShutDownServiceDb() {
		tempExchangeSite.shutDownService(".db");
	
	}
	
	@Test
	public void testgetCurrencyMap() {
		
		Map<String,Currency> tempMap = MoneyBox.getCurrencyMap();
		
		assertEquals(tempExchangeSite.getCurrencyMap(),tempMap);
	}
	
	//Test Optinal Double NB! allready Covered in other tests!
	
	@Test
	public void testCompleteOrderBuy() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500, "JPY", TransactionMode.BUY);
		tempExchangeSite.completeOrder(tempOrder);
	
	}
	
	@Test
	public void testCompleteOrderSell() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500, "JPY", TransactionMode.SELL);
		tempExchangeSite.completeOrder(tempOrder);
	
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetTransactionList() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500, "EUR", TransactionMode.BUY);
		
		tempExchangeSite.completeOrder(tempOrder);
		
	
		assertEquals(tempExchangeSite.getTransactionList(),tempExchangeSite.transactionList);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetRates() {
		tempExchangeSite.startTheDay();
		List<ExchangeRate> temptest = new ArrayList <ExchangeRate>();
		temptest = tempExchangeSite.getRates();
		
		
		assertEquals(temptest,tempExchangeSite.getRates());
	}
	
	@Test
	public void testSetName() {
		
		ExchangeSite tempExchangeSiteTest = new ExchangeSite("Jesus");
		tempExchangeSiteTest.setName("Jesus");
		
	}
	@SuppressWarnings("static-access")
	@Test
	public void testAddOrderToQueue() {
		tempExchangeSite.startTheDay();
		Order myOrder = new Order(500, "USD", TransactionMode.BUY);
		orderList = tempExchangeSite.addOrderToQueue(myOrder);
		int orders = 1;
		assertEquals(orders, orderList.size());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testProcessOrderQueueBuy() {
		tempExchangeSite.startTheDay();
		Order myOrder = new Order(500, "USD", TransactionMode.BUY);
		orderList = tempExchangeSite.addOrderToQueue(myOrder);
		tempExchangeSite.processOrderQueue(orderList);
		assertTrue(orderList.size()== 0);
		
	}
	@SuppressWarnings("static-access")
	@Test
	public void testProcessOrderQueueSell() {
		tempExchangeSite.startTheDay();
		tempOrder = new Order(500, "EUR", TransactionMode.SELL);
		orderList = tempExchangeSite.addOrderToQueue(tempOrder);
		tempExchangeSite.processOrderQueue(orderList);
		assertTrue(orderList.size()== 0);
		
	}
	@SuppressWarnings("static-access")
	@Test
	public void testProcessOrderQueueFailBuy() {

		tempExchangeSite.startTheDay();
		Order myOrder = new Order(50000, "USD", TransactionMode.BUY);
		orderList = tempExchangeSite.addOrderToQueue(myOrder);
		tempExchangeSite.processOrderQueue(orderList);
		assertTrue(orderList.size()==1);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testProcessOrderQueueFailSell() {
		tempExchangeSite.startTheDay();
		Order myOrder = new Order(50000, "USD", TransactionMode.SELL);
		orderList = tempExchangeSite.addOrderToQueue(myOrder);
		tempExchangeSite.processOrderQueue(orderList);
		assertTrue(orderList.size()==1);

	}
	
	

}
