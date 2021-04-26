package affix.java.project.moneyservice;

import java.time.LocalDate;

public class ExchangeRate {

	//Attributes
	private final LocalDate day;
	private String currencyName;
	private Float exchangeRate;
	
	//Constructor
	/**
	 * @throws Illegalarg
	 * @param day
	 * @param scale
	 * @param currency
	 * @param price
	 */
	public ExchangeRate(LocalDate day, int scale, String currency, Float price) {
		if(scale < 1 || price <0) {
			throw new IllegalArgumentException(String.format("EchangeRate scale below 1"));
		}
		this.day = day;
		this.currencyName = currency;
		this.exchangeRate = (float)(price/scale);
		
	}
	
	//Methods
	public LocalDate getLocalDate() {
		return this.day;
	}
	
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
	
	@Override
	public String toString() {
		return String.format("Date: %s, Currency: %s, Exchangerate: %3.4f", this.day.toString(),this.currencyName, this.exchangeRate);
	}
}
