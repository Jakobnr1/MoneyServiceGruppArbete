package MoneyService;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MoneyService.Currency.CurrencyName;

public class ExchangeSite {

	private final long id;
	Map<String,Float> exchangeRates;
	
	public ExchangeSite(long id, Map<String, Float> exchangeRates) {
		super();
		this.id = id;
		this.exchangeRates = exchangeRates;
	}

	
	public static boolean parseOrder() {
		
		return false;
		
		
	}
	
	public static boolean sellCurrency(EnumMap<CurrencyName, List<Banknote>> currencyList, String currencyToChange, int denomination, int newNumberOfNotes) {

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
	
	public static boolean byCurrency(EnumMap<CurrencyName, List<Banknote>> currencyList, String currencyToChange, int denomination, int newNumberOfNotes) {

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
	
	public Map<String, Float> getExchangeRates() {
		return exchangeRates;
	}

	public void setExchangeRates(Map<String, Float> exchangeRates) {
		this.exchangeRates = exchangeRates;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("ExchangeSite [id=%s, exchangeRates=%s]", id, exchangeRates);
	}
	
	
	
}
