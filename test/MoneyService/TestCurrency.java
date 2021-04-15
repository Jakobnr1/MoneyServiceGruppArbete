package MoneyService;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCurrency {

	@Test
	public void creatingCurrencyBasic1() {
		Currency c = new Currency(10);
		assertNotNull(c);
	}

		
	@Test
	public void creatingCurrencyWrongValue() {
		Currency c = new Currency(-50);
		
		assertEquals(c.getTotalValue(), 0);
	}
		
	@Test
	public void changeNumberOfNotes1() {
		Currency c = new Currency(50);
		
		c.setTotalValue(100);
		
		assertEquals(c.getTotalValue(), 150);
	}
	
	@Test
	public void changeNumberOfNotes2() {
		Currency c = new Currency(50);
		
		c.setTotalValue(-40);
		
		assertEquals(c.getTotalValue(), 10);
	}
	
	@Test
	public void changeNumberOfNotesBadInput() {
		Currency c = new Currency(50);
		
		c.setTotalValue(-60);
		
		assertEquals(c.getTotalValue(), 50);
	}
	
	
	@Test
	public void toStringTest() {
		Currency c = new Currency(50);
		
		String test= c.toString();
				
		assertEquals(test, "Currency [totalValue=50]");
	}
	
}
