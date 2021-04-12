package MoneyService;

import java.util.ArrayList;
import java.util.List;

public class Config {
	//Attributes
	private static int MIN_AMMOUNT = 1;
	private static int MAX_AMMOUNT = 2000;
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
	
}
