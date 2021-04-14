package MoneyService;

import java.util.List;
import java.util.Map;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private final long id;
	//NB changed to static to fix some linking issues
	// Change List<Currency> to Map<String,Double>
	private static Map<String,Double> currencyMap;


	public MoneyBox(long id, Map<String,Double> currencyMap) {
		super();
		this.id = id;
		this.currencyMap = currencyMap;
	}

	void orderPickup(){
		
		
	}
	//NB changed to static to fix some linking issues
	public static Map<String, Double> getCurrencyList() {
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
