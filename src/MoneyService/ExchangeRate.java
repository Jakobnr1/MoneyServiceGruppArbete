package MoneyService;

public class ExchangeRate {

	//Attributes
	String currencyName;
	float exchangeRate;
	//Constructor
	
	public ExchangeRate(String currency, float exchangeRate) {
		this.currencyName= currency;
		this.exchangeRate = exchangeRate;
	}
	//Methods
	public String getName() {
		return this.currencyName.trim();
	}
	
	public boolean setName(String currencyName) {
		this.currencyName = currencyName.trim();
		return true;
	}
	public float getExchangeRate() {
		return this.exchangeRate;
	}
	public boolean setExchangeRate(float rate) {
		this.exchangeRate = rate;
		return true;
	}
	
}
