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
		
		List<Banknote> listOfNotes = new ArrayList<>(); 
		
		List<Banknote> listOfNotesSek = new ArrayList<>(); 
		
				CurrencyName currancyType = CurrencyName.SEK;
		Banknote[] myNotes = new Banknote[5];
		
		myNotes[0] = new Banknote(20,30);
		myNotes[1] = new Banknote(50,45);
		myNotes[2] = new Banknote(100,68);
		myNotes[3] = new Banknote(500,114);
		myNotes[4] = new Banknote(1000,20);
		
		for(Banknote b: myNotes) {
			listOfNotesSek.add(b);
		}
		listOfNotes=listOfNotesSek;
		
		MoneyBox.addCurrencyToBoxAtStartOfDay(listOfNotes, currencyList, currancyType );
		
		
		List<Banknote> listOfNotesEur = new ArrayList<>(); 
		currancyType = CurrencyName.EUR;
		myNotes = new Banknote[5];
		
		myNotes[0] = new Banknote(20,20);
		myNotes[1] = new Banknote(50,25);
		myNotes[2] = new Banknote(100,58);
		myNotes[3] = new Banknote(500,189);
		myNotes[4] = new Banknote(1000,22);
		
		for(Banknote b: myNotes) {
			listOfNotesEur.add(b);
		}
		listOfNotes=listOfNotesEur;
		MoneyBox.addCurrencyToBoxAtStartOfDay(listOfNotes, currencyList, currancyType );
		//------------------------------------------
		
		int test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("Number of 100 SEK notes in box at start of day: %d", test);

		System.out.println("\n\n----------- Now trying to add number of 100 SEK notes with 5000 -------------");

		MoneyBox.changeNumberOfNotes(currencyList, "SEK", 100, 5000);

		test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("\nAfter adding: %d", test);

		System.out.println("\n\n----------- Now trying to remove number of 100 SEK notes with 438 -------------");
		
		MoneyBox.changeNumberOfNotes(currencyList, "SEK", 100, -438);
		
		test = MoneyBox.getNumberOfNotes(currencyList, "SEK" , 100);
		System.out.format("\nAfter removing: %d", test);
		
	}

}
