package MoneyService;

public class Currency implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private final int denomination;
	private int numberOfNotes;

	public Currency(int denomination, int numberOfNotes) {
		super();
		this.denomination = denomination;
		this.numberOfNotes = numberOfNotes;
	}

	public int getNumberOfNotes() {
		return numberOfNotes;
	}
	
	public int totalValue() {
		return (this.denomination*this.numberOfNotes);
	}

	public boolean setNumberOfNotes(int numberOfNotes) {
		int oldValue = this.numberOfNotes;
		int tempValue = oldValue + numberOfNotes;
		if(tempValue < 0) {
			return false;
		}
		this.numberOfNotes = oldValue + numberOfNotes;
		return true;
	}

	public int getDenomination() {
		return denomination;
	} 
	
	@Override
	public String toString() {
		return String.format("Currency [denomination=%s, numberOfNotes=%s]", denomination, numberOfNotes);
	}
	
	

}
