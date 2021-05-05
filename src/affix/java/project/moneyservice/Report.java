package affix.java.project.moneyservice;

import java.time.LocalDateTime;
import java.util.List;

public class Report {

	private final long id;
	LocalDateTime day; //TODO
	List<Transaction> dailyTransactions;
	public static int uniqueId = 0;
	public String uniqueFilename;
	
	public Report(LocalDateTime day, List<Transaction> dailyTransactions) {
		++uniqueId;
		this.id = uniqueId;
		this.day = day;
		this.dailyTransactions = dailyTransactions;
		this.uniqueFilename = String.format("Report_%s_%s.ser",ExchangeSite.name.toString().trim(), day.toLocalDate());
	}

	public LocalDateTime getDay() {
		return day;
	}

	public void setDay(LocalDateTime day) {
		this.day = day;
	}

	public List<Transaction> getDailyTransaction() {
		return dailyTransactions;
	}

	public void setDailyTransaction(List<Transaction> dailyTransaction) {
		this.dailyTransactions = dailyTransaction;
	}

	public long getId() {
		return id;
	}

	public String getUniqueFileName() {
		return this.uniqueFilename;
	}
	
	@Override
	public String toString() {
		return String.format("Rapport [id=%s, day=%s, dailyTransactions=%s]", id, day, dailyTransactions);
	}

}
