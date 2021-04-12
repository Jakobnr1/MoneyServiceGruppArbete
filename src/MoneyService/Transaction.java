package MoneyService;

import java.time.LocalDate;

public class Transaction {
	
	private final long id;
	LocalDate date;
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	String customer;
	
	public Transaction(long id, LocalDate date, String customer) {
		super();
		this.id = id;
		this.date = date;
		this.customer = customer;
	}
	
	
	

}
