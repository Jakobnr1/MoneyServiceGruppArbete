package MoneyService;

public class ExchangeRate {

	//Attributes
	String currencyName;
	Float exchangeRate;
	//Constructor
	
	public ExchangeRate(String currency, Float exchangeRate) {
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
	public Float getExchangeRate() {
		return this.exchangeRate;
	}
	public boolean setExchangeRate(Float rate) {
		this.exchangeRate = rate;
		return true;
	}
	
}
