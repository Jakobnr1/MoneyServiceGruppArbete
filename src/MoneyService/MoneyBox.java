package MoneyService;

import java.util.Map;
import java.util.logging.Logger;

public class MoneyBox implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger;

	static {
		logger = Logger.getLogger("MoneyService");
	}

	private static Map<String, Currency> currencyMap;


	public MoneyBox(Map<String, Currency> currencyMap) {
		MoneyBox.currencyMap = currencyMap;
	}


	public static Map<String, Currency> getCurrencyMap() {
		return currencyMap;
	}


	public void setCurrencyMap(Map<String, Currency> currencyMap) {
		MoneyBox.currencyMap = currencyMap;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static boolean addNewCurrency(double amount, String currencyName, Float rate) {
		Currency c = new Currency(0, rate, rate);
		if(currencyMap.containsKey(currencyName)) {
			return false;
		}
		currencyMap.putIfAbsent(currencyName, c);
		currencyMap.get(currencyName).setBuyRate(rate * 0.995F);
		currencyMap.get(currencyName).setSellRate(rate * 1.005F);

		if(currencyMap.containsKey(currencyName)) {
			logger.fine("New currency added: "+currencyName+ " rate from list:"+rate+" buyRate:"+c.getBuyRate()+" sellRate:"+c.getSellRate());
		}
		return true;
	}



}
