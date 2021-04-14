package MoneyService;

import java.util.Map;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private final long id;
	Map<String, Currency> currencyMap;


	public MoneyBox(long id, Map<String, Currency> currencyMap) {
		super();
		this.id = id;
		this.currencyMap = currencyMap;
	}



	public Map<String, Currency> getCurrencyMap() {
		return currencyMap;
	}



	public void setCurrencyMap(Map<String, Currency> currencyMap) {
		this.currencyMap = currencyMap;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public long getId() {
		return id;
	}



	@Override
	public String toString() {
		return String.format("MoneyBox [id=%s, currencyMap=%s]", id, currencyMap);
	}


}
