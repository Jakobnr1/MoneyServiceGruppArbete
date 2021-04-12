package MoneyService;

import java.util.ArrayList;
import java.util.List;

public class Config {
	//Attributes
	private static int MIN_AMMOUNT = 1;
	private static int MAX_AMMOUNT = 2000;
	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
	
	//Methods
	public List<ExchangeRate> getExchangeRateList(){
		return this.exchangeRateList;
	}
	
	public boolean setMIN_AMMOUNT(int MIN_AMMOUNT) {
		this.MIN_AMMOUNT = MIN_AMMOUNT;
		return true;
	}
	
	public boolean setMAX_AMMOUNT(int MAX_AMMOUNT) {
		this.MAX_AMMOUNT = MAX_AMMOUNT;
		return true;
	}
	
}
