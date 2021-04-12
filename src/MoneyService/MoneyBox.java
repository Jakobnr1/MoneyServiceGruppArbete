package MoneyService;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import MoneyService.Currency.CurrencyName;



public class MoneyBox implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final long id;
	EnumMap<CurrencyName, List<Banknote>> currenieList;

	public MoneyBox(long id, EnumMap<CurrencyName, List<Banknote>> currenieList) {
		this.id = id;
		this.currenieList = currenieList;
	}

	
	void orderPickup(){
		
		
	}
	
	

	public EnumMap<CurrencyName, List<Banknote>> getCurrencyList() {
		return currenieList;
	}

	public void setCurrencyList(EnumMap<CurrencyName, List<Banknote>> currencyList) {
		this.currenieList = currencyList;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("MoneyBox [id=%s, currencyList=%s]", id, currenieList);
	}


}
