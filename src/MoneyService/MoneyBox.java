package MoneyService;

import java.util.List;
import java.util.Map;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private final long id;
	Map<String, List<Currency>> currencyList;


	public MoneyBox(long id, Map<String, List<Currency>> currencyList) {
		super();
		this.id = id;
		this.currencyList = currencyList;
	}

	void orderPickup(){
		
		
	}
	
	public Map<String, List<Currency>> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(Map<String, List<Currency>> currencyList) {
		this.currencyList = currencyList;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("MoneyBox [id=%s, currencyList=%s]", id, currencyList);
	}


}
