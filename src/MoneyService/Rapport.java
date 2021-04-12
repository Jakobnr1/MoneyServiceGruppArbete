package MoneyService;

import java.time.LocalDate;
import java.util.List;

public class Rapport {

	private final long id;
	LocalDate day;
	List<Transaction> dailyTransaction;
	
	public Rapport(long id, LocalDate day, List<Transaction> dailyTransaction) {
		super();
		this.id = id;
		this.day = day;
		this.dailyTransaction = dailyTransaction;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public List<Transaction> getDailyTransaction() {
		return dailyTransaction;
	}

	public void setDailyTransaction(List<Transaction> dailyTransaction) {
		this.dailyTransaction = dailyTransaction;
	}

	public long getId() {
		return id;
	}
	
	
	

}
