package MoneyService;

import static org.junit.Assert.*;

import org.junit.Test;

public class testExchangeRate {

	float rate = (float) 4.50;
	ExchangeRate x = new ExchangeRate("GBP/JPY", rate);
	
	@Test
	public void testConstructor() {
		ExchangeRate y = new ExchangeRate("SEK/EUR",4.02F);
		assertNotNull(y);
	}
	
	@Test
	public void testGetName() {
		assertEquals(x.currencyName, "GBP/JPY");
	}
	
	@Test
	public void testSetName() {
		x.setName("Test");
		assertEquals(x.getName(),"Test");
	}
	
	@Test
	public void testGetExchangeRate() {
		assertEquals(x.getExchangeRate(), rate);
	}
	
	@Test
	public void testSetExchangeRate() {
		x.setExchangeRate(4.0F);
		assertNotNull(x.exchangeRate);
	}
	
	

}
