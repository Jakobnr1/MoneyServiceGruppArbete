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
	//TODO;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			  |	  |	
	//			  |	  |
	//			  |   |
	//-----------------------------
	//	\						/
	//		\				/
	//			\		/
	//				\/
	public boolean setTotalValue(int numberOfNotes) {
//		int oldValue = this.totalValue;
//		int tempValue = oldValue + numberOfNotes;
//		if(tempValue < 0) {							TODO:!!!!!
//			return false;							Check this???
//TODO check if we can afford to buy/ sell and if false do not do the transaction
//		}
//		this.totalValue = oldValue + numberOfNotes;
		this.totalValue = numberOfNotes;
		if(totalValue < 0) return false;
		return true;
	}



	@Override
	public String toString() {
		return String.format("Currency [totalValue=%s]",totalValue);
	} 
	
	
	

}
