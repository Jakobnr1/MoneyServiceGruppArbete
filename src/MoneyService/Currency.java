package MoneyService;

public class Currency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int totalValue;

	public Currency(int totalValue) {
		if(totalValue < 0) totalValue =0;
			this.totalValue = totalValue;
	}
	public int getTotalValue() {
		return totalValue;
	}
  

	public boolean setTotalValue(int valueToChange) {
		if(this.totalValue - valueToChange < 0) {
      return false; }
       
    else{
    this.totalValue = valueToChange;   
		return true;
       }
	}


	@Override
	public String toString() {
		return String.format("Currency [totalValue=%s]",totalValue);
	} 
	
	
	

}
