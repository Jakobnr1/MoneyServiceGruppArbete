package MoneyService;

public class Currency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int totalValue;

	public Currency(int totalValue) {
		super();
		if(totalValue < 0) totalValue =0;
			this.totalValue = totalValue;
	}

	public int getTotalValue() {
		return totalValue;
	}
	
	
	public boolean setTotalValue(int numberOfNotes) {
		int oldValue = this.totalValue;
		int tempValue = oldValue + numberOfNotes;
		if(tempValue < 0) {
			return false;
		}
		this.totalValue = oldValue + numberOfNotes;
		return true;
	}



	@Override
	public String toString() {
		return String.format("Currency [totalValue=%s]",totalValue);
	} 
	
	
	

}
