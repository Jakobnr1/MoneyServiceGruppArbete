package MoneyService;

import java.util.EnumMap;
import java.util.List;

public class Currency {

	private EnumMap<CurrencyName, List<Banknote>> currencieMap;

	public enum CurrencyName {
		USD(0,0),
		EUR(0,0),
		SEK(1,1),
		GBP(0,0);

		private double sellRate;
		private double byRate;

		public void setSellRate(double sellRate) {
			this.sellRate = sellRate;
		}

		public void setByRate(double byRate) {
			this.byRate = byRate;
		}

		public double getSellRate() {
			return sellRate;
		}

		public double getByRate() {
			return byRate;
		}

		private CurrencyName(double sellRate, double byRate) {
			this.sellRate = sellRate;
			this.byRate = byRate;
		}

	}

	public Currency(EnumMap<CurrencyName, List<Banknote>> currencieMap) {
		this.currencieMap = currencieMap;
	}

	public EnumMap<CurrencyName, List<Banknote>> getCurrencyMap() {
		return currencieMap;
	}

	public void setCurrencyMap(EnumMap<CurrencyName, List<Banknote>> currencyMap) {
		this.currencieMap = currencyMap;
	}

	@Override
	public String toString() {
		return String.format("Currency [currencyMap=%s]", currencieMap);
	}




}
