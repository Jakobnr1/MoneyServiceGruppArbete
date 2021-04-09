package MoneyService;

public class Banknote {

	private final int denomination; 
	private int numberOfNotes; 
	
	public Banknote(int denomination, int numberOfNotes) {
		this.denomination = denomination;
		this.numberOfNotes = numberOfNotes;
	}

	public void setNumberOfNotes(int numberOfNotes) {
		int oldValue = this.numberOfNotes;
		this.numberOfNotes = oldValue + numberOfNotes;
	}

	public int getDenomination() {
		return denomination;
	}

	public int getNumberOfNotes() {
		return numberOfNotes;
	}
	
}
