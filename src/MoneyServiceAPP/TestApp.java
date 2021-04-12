package MoneyServiceAPP;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import MoneyService.Banknote;
import MoneyService.Currency;
import MoneyService.Currency.CurrencyName;
import MoneyService.MoneyBox;

public class TestApp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){

		EnumMap<CurrencyName, List<Banknote>> currenieList = new EnumMap<CurrencyName, List<Banknote>>(CurrencyName.class);


		//+++++++++++++++++EXTRA JUST FOR TEST! ++++++++++++++++++++++
		//------------- Input from configuration file --------------
		long boxId=22384;
		MoneyBox theBox = new MoneyBox(boxId, currenieList); //Creating the box

		CurrencyName[] currencies = CurrencyName.values();

		theBox=readItems("Test.ser");

		//Setting up the box with currencies from start. 
//		for(CurrencyName c : currencies) {
//			CurrencyName currancyType = c;
//			List<Banknote> tempListOfNotes = new ArrayList<>();
//
//			Banknote[] myNotes = new Banknote[5];
//
//			myNotes[0] = new Banknote(20,10);
//			myNotes[1] = new Banknote(50,23);
//			myNotes[2] = new Banknote(100,500);
//			myNotes[3] = new Banknote(500,100);
//			myNotes[4] = new Banknote(1000,50);
//
//			for(Banknote b: myNotes) {
//				tempListOfNotes.add(b);
//			}
//
//			addCurrencyToBoxAtStartOfDay(tempListOfNotes, currenieList, currancyType );
//		}

		//Trying to change the rates
		setRates("EUR", 11.0193, 9.5371, currenieList);
		setRates("USD", 10.0193, 8.5371, currenieList);
		setRates("GBP", 15.0193, 22.5371, currenieList);

		int test = getNumberOfNotes(currenieList, "SEK" , 100);
		System.out.format("Number of 100 SEK notes in box at start of day: %d", test);

		System.out.println("\n\n----------- Now trying to add number of 100 SEK notes with 5000 -------------");

		changeNumberOfNotes(currenieList, "SEK", 100, 50);

		test = getNumberOfNotes(currenieList, "SEK" , 100);
		System.out.format("\nAfter adding: %d", test);

		System.out.println("\n\n----------- Now trying to remove number of 100 SEK notes with 438 -------------");

		if(!changeNumberOfNotes(currenieList, "SEK", 100, -438)) {
			System.out.println("DEBUG, under 0 when trying to change.");
		}

		test = getNumberOfNotes(currenieList, "SEK" , 100);
		System.out.format("\nAfter removing: %d", test);

		storeItems("Test.ser", theBox);

		storeItemsTxt("Test.txt", theBox);
		
		storeRatesTxt("Rates.txt", theBox);

			for(CurrencyName c : currencies) {
			System.out.format("\nDEBUG: Currency: %s By rate: %f Sell rate: %f",c.name(), c.getByRate(), c.getSellRate());
		}


	}

	public static void setRates(String currencieToChange, double byRate, double sellRate, EnumMap<CurrencyName, List<Banknote>> currenieList) {

		Set<CurrencyName> currencysKeys = currenieList.keySet();

		Iterator<CurrencyName> keyIter = currencysKeys.iterator();

		while(keyIter.hasNext()) {
			CurrencyName key= keyIter.next();
			String temp=key.toString();

			if(temp.equalsIgnoreCase(currencieToChange)) {
				System.out.println("test");
				key.setByRate(byRate);
				key.setSellRate(sellRate);
			}
		}		

	}

	public static void storeItems(String filename, MoneyBox theBox) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(theBox);
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}
	}

	public static MoneyBox readItems(String filename) {
		long boxId=0;
		EnumMap<CurrencyName, List<Banknote>> currencieList = new EnumMap<CurrencyName, List<Banknote>>(CurrencyName.class);
		MoneyBox theBox = new MoneyBox(boxId, currencieList);

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
			pw.println("TheBox: "+theBox.toString());
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}

	}
	
	
	public static void storeRatesTxt(String filename, MoneyBox theBox) {
		
		try(PrintWriter pw = new PrintWriter(new FileWriter(filename))){ 
			pw.println("Rates: "+theBox.getCurrencyList().toString());
			pw.println("TheBox: "+theBox.toString());
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}

	}
	
	//TESTAR med lite metoder:
	
		public static int getNumberOfNotes(EnumMap<CurrencyName, List<Banknote>> currencyList, String currencyToChange, int denomination) {
			int numberOfNotes=0;

			Set<CurrencyName> currencysKeys = currencyList.keySet();

			Iterator<CurrencyName> keyIter = currencysKeys.iterator();

			while(keyIter.hasNext()) {
				CurrencyName key= keyIter.next();
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
		public static boolean changeNumberOfNotes(EnumMap<CurrencyName, List<Banknote>> currencyList, String currencyToChange, int denomination, int newNumberOfNotes) {

			boolean okChange = false;
			Set<CurrencyName> currencysKeys = currencyList.keySet();

			Iterator<CurrencyName> keyIter = currencysKeys.iterator();

			while(keyIter.hasNext()) {
				CurrencyName key= keyIter.next();
				String temp=key.toString();

				if(temp.equalsIgnoreCase(currencyToChange)) {
					
					for(int i=0; i<currencyList.get(key).size(); i++) {
						if(currencyList.get(key).get(i).getDenomination() == denomination) { //Searching for correct denomination to change.
							if(currencyList.get(key).get(i).setNumberOfNotes(newNumberOfNotes)) {//Updates the numberOfNotes 
								return okChange = true;
								
							}
							
						}	
					}
				}
			}
			return okChange;
		}


		   static void addCurrencyToBoxAtStartOfDay(List<Banknote> notes, EnumMap<CurrencyName, List<Banknote>> currencyTest, CurrencyName currancyType) {
			Currency testingCurrency = new Currency(currencyTest);
			testingCurrency.getCurrencyMap().putIfAbsent(currancyType,notes);
		}



}
