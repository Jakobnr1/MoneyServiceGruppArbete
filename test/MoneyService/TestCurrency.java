package MoneyService;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCurrency {
	
	Currency testCurr;
	
	@Test
	public void testConstructor() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
	}
	@Test
	public void testConstructorNoValue() {
		testCurr = new Currency(0, 5.45f, 6.45f);
	}
	
	@Test
	public void testGetBuyRate() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Float buyRate = 5.45f;
		assertEquals(buyRate, testCurr.getBuyRate());
		
	}
	@Test
	public void testSetBuyRate() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Float buyRate = 8.45f;
		testCurr.setBuyRate(buyRate);
		assertEquals(buyRate, testCurr.getBuyRate());
		
	}
	@Test
	public void testSetSellRate() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Float sellRate = 3.45f;
		testCurr.setSellRate(sellRate);
		assertEquals(sellRate, testCurr.getSellRate());
		
	}
	
	@Test
	public void testGetSellRate() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Float sellRate = 6.45f;
		assertEquals(sellRate, testCurr.getSellRate());
		
	}
	
	@Test
	public void testGetTotalValue() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Double value = 1000D;
		assertEquals(value, testCurr.getTotalValue());
	}
	
	@Test
	public void testSetTotalValue() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		Double value = 1000D;
		testCurr.setTotalValue(1000);
		assertEquals(value, testCurr.getTotalValue());
	}
	@Test
	public void testToString() {
		testCurr = new Currency(1000, 5.45f, 6.45f);
		assertEquals("Currency [totalValue=1000.0]",testCurr.toString());
	}
}