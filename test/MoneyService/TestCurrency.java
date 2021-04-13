package MoneyService;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCurrency {

	@Test
	public void creatingCurrencyBasic1() {
		Currency c = new Currency(0, 0);
		assertNotNull(c);
	}

	@Test
	public void creatingCurrencyDenomination() {
		Currency c = new Currency(10, 0);
			
		int denomination = c.getDenomination();
		assertEquals(denomination, 10);
	}
	
	@Test
	public void creatingCurrencyWrongNumberOfNotes() {
		Currency c = new Currency(0, -50);
			
		int denomination = c.getNumberOfNotes();
		assertEquals(denomination, 0);
	}
	
	@Test
	public void creatingCurrencyWrongDenomination() {
		Currency c = new Currency(-10, 0);
			
		int denomination = c.getDenomination();
		assertEquals(denomination, 0);
	}
	
	@Test
	public void creatingCurrencyNumberOfNotes() {
		Currency c = new Currency(0, 50);
			
		int denomination = c.getNumberOfNotes();
		assertEquals(denomination, 50);
	}
	
	@Test
	public void changeNumberOfNotes1() {
		Currency c = new Currency(100, 50);
		
		c.setNumberOfNotes(100);
		
		int denomination = c.getNumberOfNotes();
		assertEquals(denomination, 150);
	}
	
	@Test
	public void changeNumberOfNotes2() {
		Currency c = new Currency(100, 50);
		
		c.setNumberOfNotes(-40);
		
		int denomination = c.getNumberOfNotes();
		assertEquals(denomination, 10);
	}
	
	@Test
	public void changeNumberOfNotesBadInput() {
		Currency c = new Currency(100, 50);
		
		c.setNumberOfNotes(-60);
		
		int denomination = c.getNumberOfNotes();
		assertEquals(denomination, 50);
	}
	
	@Test
	public void getTotalValue() {
		Currency c = new Currency(100, 50);
		
		int totalValue = c.totalValue();
				
		assertEquals(totalValue, 5000);
	}
	
	@Test
	public void toStringTest() {
		Currency c = new Currency(100, 50);
		
		String test= c.toString();
				
		assertEquals(test, "Currency [denomination=100, numberOfNotes=50]");
	}
	
}
