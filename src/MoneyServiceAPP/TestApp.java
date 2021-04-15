package MoneyServiceAPP;

import java.util.Map;

import java.util.TreeMap;

import MoneyService.Currency;
import MoneyService.MoneyBox;

public class TestApp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args){

		Map<String, Currency> currencyMap = new TreeMap<String, Currency>();

		long boxId=99999;
		MoneyBox theBox = new MoneyBox(boxId, currencyMap);
		
		Currency c1 = new Currency(10500);
		Currency c2 = new Currency(50500);
		Currency c3 = new Currency(90500);
		Currency c4 = new Currency(30500);
		
		currencyMap.putIfAbsent("SEK", c1);
		currencyMap.putIfAbsent("EUR", c2);
		currencyMap.putIfAbsent("GPB", c3);
		currencyMap.putIfAbsent("USD", c4);

		System.out.format("\nAt start: %d", currencyMap.get("SEK").getTotalValue());

		System.out.println("\n\n----------- Now trying to add 5000 -------------");

		currencyMap.get("SEK").setTotalValue(5000);
	
		System.out.format("\nAfter add: %d", currencyMap.get("SEK").getTotalValue());
	
		System.out.println("\n\n----------- Now trying to remove 438 SEK -------------");
		
		if(!currencyMap.get("SEK").setTotalValue(-438)) {
			System.out.println("DEBUG, under 0 when trying to change.");
		}
		
		System.out.format("\nAfter removing: %d", currencyMap.get("SEK").getTotalValue());
		
		System.out.format("\nTheBox: %s", theBox.toString());
		
	}

}

