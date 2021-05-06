package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.Currency;
import affix.java.project.moneyservice.ExchangeRate;
import affix.java.project.moneyservice.MoneyBox;

public class TestMoneyBox {
	
	Map<String, Currency> currencyMap = new HashMap<>();
	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
	
	MoneyBox testBox;
	@SuppressWarnings("static-access")
	@Test
	public void testGetCurrencyMap() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		
		assertEquals(currencyMap, testBox.getCurrencyMap());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSetMap() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		Map<String, Currency> currencyMapTemp = new HashMap<>();
		currencyMapTemp = testBox.getCurrencyMap();
		
		testBox.setCurrencyMap(currencyMapTemp);
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetSerial() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		long test = testBox.getSerialversionuid();
		
		assertEquals(test, testBox.getSerialversionuid());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testAddNewCurr() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		
		assertTrue(testBox.addNewCurrency(1000.00D, "FIN", 4.20f));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testAddNewCurrfalse() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		testBox.addNewCurrency(1000.00D, "EUR", 4.20f);
		assertFalse(testBox.addNewCurrency(1000.00D, "EUR", 4.20f));
	}
	@SuppressWarnings("static-access")
	@Test
	public void testDenominationControllWithoutCurrency() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		int testInt = 50;
		assertEquals(testInt, testBox.denominationControl("EUR", testInt));
	}
	@SuppressWarnings("static-access")
	@Test
	public void testDenominationControllWithCurrency() {
		Config.setRatesInCurrency(exchangeRateList, currencyMap);
		testBox = new MoneyBox(currencyMap);
		testBox = Config.fillTheMoneyBox(testBox, currencyMap);
		int testInt = 50;
		testBox.addNewCurrency(1000.00D, "EUR", 4.20f);
		assertEquals(testInt, testBox.denominationControl("EUR", testInt));
	}
	
}
