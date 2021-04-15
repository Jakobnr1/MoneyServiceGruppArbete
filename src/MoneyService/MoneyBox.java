package MoneyService;

import java.util.Map;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private static Map<String, Currency> currencyMap;


	public MoneyBox(Map<String, Currency> currencyMap) {
		super();
		this.currencyMap = currencyMap;
	}


	public static Map<String, Currency> getCurrencyMap() {
		return currencyMap;
	}


	public void setCurrencyMap(Map<String, Currency> currencyMap) {
		this.currencyMap = currencyMap;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
