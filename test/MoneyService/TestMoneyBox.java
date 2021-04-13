package MoneyService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class TestMoneyBox {

	@Test
	public void creatingEmptyMoneyBox() {
		Map<String, List<Currency>> currencyMap = new TreeMap<String, List<Currency>>();
		
		MoneyBox testBox = new MoneyBox(0, currencyMap);		
		
		assertNotNull(testBox);
	}

	@Test
	public void creatingMoneyBox() {
		Map<String, List<Currency>> currencyMap = new TreeMap<String, List<Currency>>();
		MoneyBox testBox = new MoneyBox(0, currencyMap);
		
		String currencyName = "SEK";
		
		List<Currency> testList = new ArrayList<Currency>();
		Currency[] myCurrencyList =new Currency[2];
		myCurrencyList[0] = new Currency(50,100);
		myCurrencyList[1] = new Currency(100,200);
				
		for(Currency c: myCurrencyList) {
			testList.add(c);
		}
		currencyMap.putIfAbsent(currencyName, testList);
				
		assertNotNull(testBox);
	}
	
	@Test
	public void getIdTest() {
		Map<String, List<Currency>> currencyMap = new TreeMap<String, List<Currency>>();
		MoneyBox testBox = new MoneyBox(999, currencyMap);
					
		assertEquals(testBox.getId(),999);
	}
	
	@Test
	public void toStringTest() {
		Map<String, List<Currency>> currencyMap = new TreeMap<String, List<Currency>>();
		MoneyBox testBox = new MoneyBox(999, currencyMap);
		
String currencyName = "SEK";
		
		List<Currency> testList = new ArrayList<Currency>();
		Currency[] myCurrencyList =new Currency[1];
		myCurrencyList[0] = new Currency(50,100);
					
		for(Currency c: myCurrencyList) {
			testList.add(c);
		}
		currencyMap.putIfAbsent(currencyName, testList);	

		String test = testBox.toString();
				
		assertEquals(test, "MoneyBox [id=999, currencyMap={SEK=[Currency [denomination=50, numberOfNotes=100]]}]");
	}
	
}
