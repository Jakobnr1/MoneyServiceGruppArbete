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
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();

		MoneyBox testBox = new MoneyBox(currencyMap);		

		assertNotNull(testBox);
	}

	@Test
	public void creatingMoneyBox() {
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();
		MoneyBox testBox = new MoneyBox(currencyMap);

		String currencyName = "SEK";
		Currency c= new Currency(5000);

		currencyMap.putIfAbsent(currencyName, c);

		assertNotNull(testBox);
	}

	
}
