package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import MoneyService.Order.TransactionMode;



public class TestExchangeSite {
	ExchangeSite tempExchangeSite = new ExchangeSite("North");
	Order tempOrder;
	Order tempOrderAnotherOne;
	
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
		tempOrder = new Order(4500, "AUD", TransactionMode.SELL);
		tempOrderAnotherOne = new Order(4500, "AUD", TransactionMode.SELL);
		tempExchangeSite.sellMoney(tempOrder);
		
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
	
	
	
	
	

}
