package MoneyService;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import MoneyService.Currency.CurrencyName;

public class TestApp {

	public static void main(String[] args){

		EnumMap<CurrencyName, List<Banknote>> currencyList = new EnumMap<CurrencyName, List<Banknote>>(CurrencyName.class);

		//------------- Input from configuration file --------------
		long boxId=22384;
		MoneyBox theBox = new MoneyBox(boxId, currencyList); //Creating the box
			
		CurrencyName[] currencies = CurrencyName.values();
		
		//Setting up the box with currencies from start. 
		for(CurrencyName c : currencies) {
			CurrencyName currancyType = c;
			List<Banknote> tempListOfNotes = new ArrayList<>();
			
			Banknote[] myNotes = new Banknote[5];
			
			myNotes[0] = new Banknote(20,0);
			myNotes[1] = new Banknote(50,0);
			myNotes[2] = new Banknote(100,0);
			myNotes[3] = new Banknote(500,0);
			myNotes[4] = new Banknote(1000,0);
			
			for(Banknote b: myNotes) {
				tempListOfNotes.add(b);
			}
			
			MoneyBox.addCurrencyToBoxAtStartOfDay(tempListOfNotes, currencyList, currancyType );
		}

		
		int test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("Number of 100 SEK notes in box at start of day: %d", test);

		System.out.println("\n\n----------- Now trying to add number of 100 SEK notes with 5000 -------------");

		MoneyBox.changeNumberOfNotes(currencyList, "SEK", 100, 50);

		test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("\nAfter adding: %d", test);

		System.out.println("\n\n----------- Now trying to remove number of 100 SEK notes with 438 -------------");
		
		if(!MoneyBox.changeNumberOfNotes(currencyList, "SEK", 100, -438)) {
			System.out.println("DEBUG, under 0 when trying to change.");
		}
		
		test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("\nAfter removing: %d", test);
		
	}

}
