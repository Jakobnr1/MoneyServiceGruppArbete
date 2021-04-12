package MoneyService;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
