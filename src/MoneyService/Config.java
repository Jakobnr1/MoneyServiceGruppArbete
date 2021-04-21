package MoneyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config {
	//Attributes
	private static int MIN_AMMOUNT = 50;
	private static int MAX_AMMOUNT = 10000;

	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
	
	//Methods
	public static List<ExchangeRate> getExchangeRateList(){
		return exchangeRateList;
	}
	
	public static boolean setMIN_AMMOUNT(int MIN_AMMOUNT) {
		MIN_AMMOUNT = MIN_AMMOUNT;
		return true;
	}
	
	public static boolean setMAX_AMMOUNT(int MAX_AMMOUNT) {
		MAX_AMMOUNT = MAX_AMMOUNT;
		return true;
	}

	
	public static int getMIN_AMMOUNT() {
		return MIN_AMMOUNT;
	}

	public static int getMAX_AMMOUNT() {
		return MAX_AMMOUNT;
	}

	
	/**
	 * The method sets buy and sell rate in each currency.
	 * Needs to be run early in program start! 
	 * @param currencyList
	 * @param currencyMap
	 */
		public static void setRatesInCurrency(List<ExchangeRate> currencyList, Map<String, Currency> currencyMap) {
			for(ExchangeRate s : currencyList) {
				String key = s.getName();
				Float buyRate = s.getExchangeRate() * 0.995F;
				currencyMap.get(key).setBuyRate(buyRate);
				Float sellRate = s.getExchangeRate() * 1.005f;
				currencyMap.get(key).setSellRate(sellRate);;
			}
		}
	
	
	public static List<ExchangeRate> setTheRates() {
		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.currencyConfigFilename)));
//		for(ExchangeRate er:test) {
//			System.out.println(er.toString());
//		}
		
		return test;
	}

	
	public static MoneyBox fillTheMoneyBox(MoneyBox theBox, Map<String, Currency> currencyMap ) {
		Map<String, Double> testMap = new HashMap<String,Double>(MoneyServiceIO.parseProjectConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.projectConfigFilename)));

		Set<String> keySet = testMap.keySet();
		for(String k:keySet) {
			Currency tempCurrency = new Currency(testMap.get(k).intValue(), 0.0f, 0.0f);
			currencyMap.putIfAbsent(k, tempCurrency);
//			System.out.println(k.toString());
		}
		return theBox;
	}

}
