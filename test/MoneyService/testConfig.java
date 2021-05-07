package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.ExchangeRate;
import affix.java.project.moneyservice.ExchangeSite;



@SuppressWarnings("unused")
public class testConfig {
	private static Logger logger;
	private static FileHandler fh ;
	
	ExchangeSite tempExchangeSite = new ExchangeSite("North");
	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
		
	@Test
	public void testGetExchangeRateList(){
		Config.readConfigFile("configFileNorth.txt");

		exchangeRateList = Config.getExchangeRateList();
		tempExchangeSite.startTheDay();
		assertNotNull(exchangeRateList);
		
	}
	
	@Test
	public void testSetMIN_AMOUNt() {
		assertTrue(Config.setMIN_AMMOUNT(100));
	}
	@Test
	public void testSetMAX_AMOUNt() {
		assertTrue(Config.setMAX_AMMOUNT(100000));
	}
	
	@Test
	public void testGetMIN_AMOUNT() {
		int min = 100;
		Config.setMIN_AMMOUNT(min);
		assertEquals(min, Config.getMIN_AMMOUNT());
	}
	@Test
	public void testGetMAX_AMOUNT() {
		int max = 5000;
		Config.setMAX_AMMOUNT(max);
		assertEquals(max, Config.getMAX_AMMOUNT());
	}
	
	@Test
	public void testControllPwd() {
		Config.readConfigFile("configFileNorth.txt");

		exchangeRateList = Config.getExchangeRateList();
		tempExchangeSite.startTheDay();
		assertTrue(Config.controlPwd("asdf1234"));
	}
}
