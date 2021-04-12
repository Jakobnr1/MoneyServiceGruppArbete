package MoneyService;

public class Banknote implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private final int denomination; 
	private int numberOfNotes; 
	
	public Banknote(int denomination, int numberOfNotes) {
		this.denomination = denomination;
		this.numberOfNotes = numberOfNotes;
	}

	boolean setNumberOfNotes(int numberOfNotes) {
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

	public int getNumberOfNotes() {
		return numberOfNotes;
	}
	
	@Override
	public String toString() {
		return String.format("Banknote [denomination=%s, numberOfNotes=%s]", denomination, numberOfNotes);
	}
}
