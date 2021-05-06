package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.ExchangeRate;
import affix.java.project.moneyservice.ExchangeSite;



public class testConfig {
	private static Logger logger;
	private static FileHandler fh ;
	Config testInitialize = new Config();
	ExchangeSite tempExchangeSite = new ExchangeSite("North");
	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
		
	@Test
	public void testGetExchangeRateList(){
		tempExchangeSite.startTheDay();
		exchangeRateList = Config.getExchangeRateList();
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
	public void testSetUpLogger0() {
		ExchangeSite theSite = new ExchangeSite("Test");
		Config.readConfigFile("configFileNorthTest.txt");

		theSite.startTheDay();
		logger = Config.setUpLogger(logger, fh);
	}
	@Test
	public void testSetUpLogger1() {
		ExchangeSite theSite = new ExchangeSite("Test");
		Config.readConfigFile("configFileNorthTest.txt");

		theSite.startTheDay();
		Config.setLogLevel(Level.ALL);
		logger = Config.setUpLogger(logger, fh);
	}
	@Test
	public void testControllPwd() {
		assertTrue(Config.controlPwd("asdf1234"));
	}
}
