package MoneyService;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import MoneyService.Currency.CurrencyName;



public class MoneyBox {

	private final long id;
	EnumMap<CurrencyName, List<Banknote>> currencyList;

	public MoneyBox(long id, EnumMap<CurrencyName, List<Banknote>> currencyList) {
		this.id = id;
		this.currencyList = currencyList;
	}

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


	public EnumMap<CurrencyName, List<Banknote>> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(EnumMap<CurrencyName, List<Banknote>> currencyList) {
		this.currencyList = currencyList;
	}

	public long getId() {
		return id;
	}


}
