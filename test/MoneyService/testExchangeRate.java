package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;



//@Test
//public void test() {
//	
//	
//}
public class testExchangeRate {
//	ExchangeSite tempExchangeSite = new ExchangeSite("North");
	ExchangeRate testExchangeRate;
	@Test
	public void testConstructor() {
		testExchangeRate = new ExchangeRate(LocalDate.now(), 10, "EUR", 10.54f);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorfail() {
		testExchangeRate = new ExchangeRate(LocalDate.now(), 0, "EUR", 10.54f);
		
	}
	
	@Test
	public void testGetLocalDate() {
		LocalDate date = LocalDate.now();
		testExchangeRate = new ExchangeRate(LocalDate.now(), 10, "EUR", 10.54f);
		
		assertEquals(date, testExchangeRate.getLocalDate());
	}
	@Test
	public void testGetname() {
		String name = "EUR";
		testExchangeRate = new ExchangeRate(LocalDate.now(), 10, "EUR", 10.54f);
		
		assertEquals(name, testExchangeRate.getName());
	}
	
	@Test
	public void testSetName() {
		
		testExchangeRate = new ExchangeRate(LocalDate.now(), 10, "EUR", 10.54f);
		
		
		assertTrue(testExchangeRate.setName("SEK"));
	}
	
	@Test
	public void testGetExRate() {
		Float rate = 10.54f;
		testExchangeRate = new ExchangeRate(LocalDate.now(), 1, "EUR", 10.54f);
		
		assertEquals(rate, testExchangeRate.getExchangeRate());
	}
	@Test
	public void testSetExRate() {
		
		testExchangeRate = new ExchangeRate(LocalDate.now(), 10, "EUR", 10.54f);
		
		assertTrue(testExchangeRate.setExchangeRate(9.45f));
	}
	@Test
	public void testToString() {
		
		testExchangeRate = new ExchangeRate(LocalDate.of(2021, 04, 23), 10, "EUR", 10.54f);
		
		assertEquals("Date: 2021-04-23, Currency: EUR, Exchangerate: 1.0540", testExchangeRate.toString());
	}

}
