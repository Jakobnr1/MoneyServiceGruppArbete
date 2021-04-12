package MoneyService;

import java.time.LocalDate;

public class Order {
	
	private final long id;
	LocalDate date;
	
	public enum typeOfTransaction {SELL,BY,SPLIT}

	public Order(long id, LocalDate date) {
		super();
		this.id = id;
		this.date = date;
	}

	
	
	
	

}
