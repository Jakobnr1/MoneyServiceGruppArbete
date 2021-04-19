package MoneyService;

import java.util.List;
import java.util.Map;

public class Currency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int totalValue;
	private Float buyRate;
	private Float sellRate;


	public Currency(int totalValue, Float buyRate, Float sellRate) {
		if(totalValue < 0) totalValue =0;
		this.totalValue = totalValue;
		this.buyRate = buyRate;
		this.sellRate = sellRate;	
	}

	public Float getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(Float buyRate) {
		this.buyRate = buyRate;
	}
	public Float getSellRate() {
		return sellRate;
	}
	public void setSellRate(Float sellRate) {
		this.sellRate = sellRate;
	}
	public int getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(int valueToChange) {
		this.totalValue = valueToChange;   
	}
	
/**
 * The method sets buy and sell rate in each currency.
 * Needs to be run early in program start! 
 * @param currencyList
 * @param currencyMap
 */
	public static void setRates(List<ExchangeRate> currencyList, Map<String, Currency> currencyMap) {
		for(ExchangeRate s : currencyList) {
			String key = s.getName();
			Float buyRate = s.getExchangeRate().floatValue();
			currencyMap.get(key).setBuyRate(buyRate);
			Float sellRate = buyRate *1.005f;
			currencyMap.get(key).setSellRate(sellRate);;
		}
	}


	/**
	 * Skip the one in Order and use this one instead to get price.
	 * @param currencyCode
	 * @param amount
	 * @return int price
	 */

	public static int calculatePrice(String currencyCode, int amount) {

		Map<String, Currency> currencyMap= MoneyBox.getCurrencyMap();
		float calcPrice = currencyMap.get(currencyCode).sellRate.floatValue();

		float price = amount*calcPrice;
		
		return (int)price;
	}
	
	@Override
	public String toString() {
		return String.format("Currency [totalValue=%s]",totalValue);
	} 

	


}
