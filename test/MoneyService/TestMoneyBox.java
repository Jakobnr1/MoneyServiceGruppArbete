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

		MoneyBox testBox = new MoneyBox(0, currencyMap);		

		assertNotNull(testBox);
	}

	@Test
	public void creatingMoneyBox() {
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();
		MoneyBox testBox = new MoneyBox(0, currencyMap);

		String currencyName = "SEK";
		Currency c= new Currency(5000);

		currencyMap.putIfAbsent(currencyName, c);

		assertNotNull(testBox);
	}

	@Test
	public void getIdTest() {
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();
		MoneyBox testBox = new MoneyBox(999, currencyMap);

		assertEquals(testBox.getId(),999);
	}

	@Test
	public void toStringTest() {
		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();
		MoneyBox testBox = new MoneyBox(999, currencyMap);


		String currencyName = "SEK";
		Currency c= new Currency(5000);

		currencyMap.putIfAbsent(currencyName, c);	

		String test = testBox.toString();

		assertEquals(test, "MoneyBox [id=999, currencyMap={SEK=Currency [totalValue=5000]}]");
	}

}
