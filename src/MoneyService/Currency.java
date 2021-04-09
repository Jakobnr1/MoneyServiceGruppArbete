package MoneyService;

import java.util.EnumMap;
import java.util.List;

public class Currency {

	private EnumMap<CurrencyName, List<Banknote>> currencyMap;

	public enum CurrencyName {SEK,EUR,DOLLAR,PUND,NOK;}

	public Currency(EnumMap<CurrencyName, List<Banknote>> currencyMap) {
		this.currencyMap = currencyMap;
	}

	public EnumMap<CurrencyName, List<Banknote>> getCurrencyMap() {
		return currencyMap;
	}

	public void setCurrencyMap(EnumMap<CurrencyName, List<Banknote>> currencyMap) {
		this.currencyMap = currencyMap;
	}

}
