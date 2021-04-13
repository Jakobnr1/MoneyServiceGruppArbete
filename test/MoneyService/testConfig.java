package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class testConfig {

	int min = 2;
	int max = 4;
	ExchangeRate x = new ExchangeRate("GBP/JPY", 3.0F);
	ExchangeRate y = new ExchangeRate("SEK/JPY", 4.0F);
	ExchangeRate z = new ExchangeRate("EUR/SEK", 5.0F);
	
	@Ignore
	@Test
	public void testConstructor() {
		ExchangeRate y = new ExchangeRate("SEK/EUR",4.02F);
		assertNotNull(y);
	}
	
	@Test
	public void testList() {
		List<ExchangeRate> testList = new ArrayList<>();
		testList.add(x);
		testList.add(y);
		testList.add(z);
		Config.exchangeRateList = testList;
		assertFalse(testList.isEmpty());
		assertFalse(Config.exchangeRateList.isEmpty());
		
	}
	
	@Test
	public void testGetList() {
		List<ExchangeRate> testList = new ArrayList<>();
		Config.exchangeRateList = testList;
		testList.add(x);
		testList.add(y);
		testList.add(z);
		List<ExchangeRate> fillThisList;
		fillThisList = Config.getExchangeRateList();
		assertFalse(Config.exchangeRateList.isEmpty());
		assertFalse(fillThisList.isEmpty());
	}
	
	@Test
	public void testSetMIN_AMMOUNT() {
		Config.setMIN_AMMOUNT(10);
		assertNotNull(Config.getMIN_AMMOUNT());
		assertEquals(10, Config.getMIN_AMMOUNT());
		
	}
	
	@Test
	public void testSetMAX_AMMOUNT() {
		Config.setMAX_AMMOUNT(50);
		assertNotNull(Config.getMAX_AMMOUNT());
		assertEquals(50,Config.getMAX_AMMOUNT());
		
	}
	
	@Test
	public void testGetMIN_AMMOUNT() {
		Config.setMIN_AMMOUNT(10);
		assertEquals(10, Config.getMIN_AMMOUNT());
	}
	
	@Test
	public void testGetMAX_AMMOUNT() {
		Config.setMAX_AMMOUNT(50);
		assertEquals(50, Config.getMAX_AMMOUNT());
	}


}
