package MoneyService;

import java.util.ArrayList;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ExchangeSite implements MoneyService {
	String name;
	
//public ExchangeSite(String name, long id, Map<String, List<Currency>> currencyMap) {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	
	/*
//	private final long id;

//	Map<String,Float> exchangeRates;
	
	public ExchangeSite(long id, Map<String, Float> exchangeRates) {
//		this.id = id;
//		this.exchangeRates = exchangeRates;
	}

	
	public static boolean parseOrder(int value) {
		
		if(value>)
		
		
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
*/

//	public Map<String,Double> tempMap;
	

// Doublecheck this/test
	@Override
	public boolean buyMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();
		
		int totalCurrency = MoneyBox.getCurrencyMap().get(currency).getTotalValue();
		MoneyBox.getCurrencyMap().get(currency).setTotalValue(totalCurrency-value);
		return (value<totalCurrency)?true : false;
	}

// Doublecheck this/test
	@Override
	public boolean sellMoney(Order orderData) throws IllegalArgumentException {
		int value = orderData.getValue();
		String currency = orderData.getCurrencyCode();
		
		int totalCurrency = MoneyBox.getCurrencyMap().get(currency).getTotalValue();
		MoneyBox.getCurrencyMap().get(currency).setTotalValue(totalCurrency+value);
		return (value<totalCurrency)?true : false;
	}

	@Override
	public void printSiteReport(String destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutDownService(String destination) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map<String, Currency> getCurrencyMap() {
		Map<String,Currency> tempMap = new TreeMap<>();
		
		return tempMap; 
		}
	
	@Override
	public Optional<Double> getAvailableAmount(String currencyCode) 
	{
		tempMap = MoneyBox.getCurrencyList();
		if(true){
			// TODO Add code for optional handling
			return Optional.empty();
		}
		
	else {
		return Optional.empty(); 
		}
	}
	
	
//	@Override
//	public String toString() {
//		return String.format("ExchangeSite [id=%s, exchangeRates=%s]", id, exchangeRates);
//	}
}
