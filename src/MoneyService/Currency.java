package MoneyService;


public class Currency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private double totalValue;
	Float buyRate;
	Float sellRate;


	public Currency(int totalValue, Float buyRate, Float sellRate) {
		if(totalValue < 0) {
			totalValue =0;
		}
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
	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(int valueToChange) {
		this.totalValue = valueToChange;   
	}


	@Override
	public String toString() {
		return String.format("Currency [totalValue=%s]",totalValue);
	} 




}
