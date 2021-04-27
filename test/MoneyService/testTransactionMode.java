package MoneyService;

import static org.junit.Assert.*;

import org.junit.Test;

import affix.java.project.moneyservice.TransactionMode;

public class testTransactionMode {

	@Test
	public void testBuy() {
		TransactionMode testBuy = TransactionMode.BUY;
		assertEquals(testBuy, TransactionMode.BUY);
	}

	@Test
	public void testSell() {
		TransactionMode testSell = TransactionMode.SELL;
		assertEquals(testSell, TransactionMode.SELL);
	}

}
