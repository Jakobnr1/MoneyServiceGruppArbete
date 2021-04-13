package MoneyService;

import java.util.List;
import java.util.Map;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private final long id;
	Map<String, List<Currency>> currencyMap;


	public MoneyBox(long id, Map<String, List<Currency>> currencyMap) {
		super();
		this.id = id;
		this.currencyMap = currencyMap;
	}

	void orderPickup(){
		
		
	}
	
	public Map<String, List<Currency>> getCurrencyList() {
		return currencyMap;
	}

	
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("MoneyBox [id=%s, currencyMap=%s]", id, currencyMap);
	}


}
