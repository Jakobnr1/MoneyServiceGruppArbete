package MoneyServiceAPP;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import MoneyService.Currency;
import MoneyService.MoneyBox;

public class TestApp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){

		Map<String, List<Currency>> currencyMap = new TreeMap<String, List<Currency>>();

		//Creating the box
		long boxId=22384;
		MoneyBox theBox = new MoneyBox(boxId, currencyMap);

		String[] currencyToUse = {"SEK","EUR","GPB","USD"};

		for(String c : currencyToUse) {
			List<Currency> testList = new ArrayList<Currency>();
			Currency[] myCurrencyList =new Currency[4];
			myCurrencyList[0] = new Currency(50,100);
			myCurrencyList[1] = new Currency(100,200);
			myCurrencyList[2] = new Currency(500,50);
			myCurrencyList[3] = new Currency(1000,10);

			for(Currency curt: myCurrencyList) {
				testList.add(curt);
			}
			currencyMap.putIfAbsent(c, testList);
		}
		
		for(List<Currency> l : currencyMap.values() ) {
			for(Currency c : l) {
				System.out.format("DEBUG : %d",c.totalValue());				
			}
		}
		
		int test = getNumberOfNotes(currencyMap, "SEK" , 100);
		System.out.format("Number of 100 SEK notes in box at start of day: %d", test);

		System.out.println("\n\n----------- Now trying to add number of 100 SEK notes with 5000 -------------");

		changeNumberOfNotes(currencyMap, "SEK", 100, 5000);

		test = getNumberOfNotes(currencyMap, "SEK" , 100);
		System.out.format("\nAfter adding: %d", test);

		System.out.println("\n\n----------- Now trying to remove number of 100 SEK notes with 438 -------------");

		if(!changeNumberOfNotes(currencyMap, "SEK", 100, -438)) {
			System.out.println("DEBUG, under 0 when trying to change.");
		}

		test = getNumberOfNotes(currencyMap, "SEK" , 100);
		System.out.format("\nAfter removing: %d", test);

		storeItems("Test.ser", theBox);

		storeItemsTxt("Test.txt", theBox);

	}

	//	}


	public static void storeItems(String filename, MoneyBox theBox) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(theBox);
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}
	}

	public static MoneyBox readItems(String filename, MoneyBox theBox ) {
					
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
			theBox=(MoneyBox)ois.readObject();
		}
		catch(IOException | ClassNotFoundException ioe){
			System.out.println("Exception occurred: " + ioe);
		}
		return theBox;
	}

	public static void storeItemsTxt(String filename, MoneyBox theBox) {

		try(PrintWriter pw = new PrintWriter(new FileWriter(filename))){ 
			pw.println("TheBox: "+theBox.getCurrencyList().entrySet().toString());
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}

	}





	public static int getNumberOfNotes(Map<String, List<Currency>> currencyList, String currencyToChange, int denomination) {
		int numberOfNotes=0;

		Set<String> currencysKeys = currencyList.keySet();
		Iterator<String> keyIter = currencysKeys.iterator();

		while(keyIter.hasNext()) {
			String key= keyIter.next();
			String temp=key.toString();

			if(temp.equalsIgnoreCase(currencyToChange)) {

				for(int i=0; i<currencyList.get(key).size(); i++) {
					if(currencyList.get(key).get(i).getDenomination() == denomination) { //Searching for correct denomination to change.
						return numberOfNotes = currencyList.get(key).get(i).getNumberOfNotes(); //get the numberOfNotes 
					}	
				}
			}
		}
		return numberOfNotes;
	}

	/**
	 * Input: EnumMap<CurrencyName, List<Banknote>> currencyList, String currencysKeys, int denomination, int newNumberOfNotes.
	 * Changes the numberOfNotes with newNumberOfNotes.<p>
	 * Iterating true currencyList and search for the currencyToChange, than searching for correct denomination to change.
	 * returns okChange to true if denomination changed.
	 * @return boolean okChange.
	 * @Param Set<Currencys> currencysKeys, store all the keys in the EnumMap.
	 * @Param Iterator<Currencys> keyIter, iterates thru the keys in currencysKeys.
	 * @Param String temp, used to comparing with String currencysKeys.
	 */
	public static boolean changeNumberOfNotes(Map<String, List<Currency>> currencyList, String currencyToChange, int denomination, int newNumberOfNotes) {

		boolean okChange = false;
		Set<String> currencysKeys = currencyList.keySet();
		Iterator<String> keyIter = currencysKeys.iterator();

		while(keyIter.hasNext()) {
			String key= keyIter.next();
			String temp=key.toString();

			if(temp.equalsIgnoreCase(currencyToChange)) {

				for(int i=0; i<currencyList.get(key).size(); i++) {
					if(currencyList.get(key).get(i).getDenomination() == denomination) { //Searching for correct denomination to change.
						currencyList.get(key).get(i).setNumberOfNotes(newNumberOfNotes);//Updates the numberOfNotes 
						return okChange = true;
					}
				}
			}
		}
		return okChange;
	}
}

