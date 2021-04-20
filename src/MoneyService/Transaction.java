package MoneyService;

import java.io.Serializable;
import java.time.LocalDateTime;

import MoneyService.Order.TransactionMode;

public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Attributes
	private final int id;
	private final LocalDateTime timeStamp;
	private final String currencyCode;
	private final int amount;
	private final TransactionMode mode;
	private static int uniqueId = 0;
	
	//Constructor
	
	public Transaction(Order order) {
		this(order, LocalDateTime.now());
	}
	
	public Transaction(Order order, LocalDateTime ref) {
		++uniqueId;
		this.id = uniqueId;
		this.currencyCode = order.getCurrencyCode();
		this.amount = order.getValue();
		this.mode = order.getTransactionType();
		this.timeStamp = ref;
	}
	
	//Methods
	public static int getUniqueId() {
		return uniqueId;
	}

	public static void setUniqueId(int uniqueId) {
		Transaction.uniqueId = uniqueId;
	}

	public int getId() {
		return this.id;
	}

	public LocalDateTime getTimeStamp() {
		return this.timeStamp;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public int getAmount() {
		return this.amount;
	}

	public TransactionMode getMode() {
		return this.mode;
	}

	@Override
	public String toString() {
		return String.format("Transaction [id=%s, timeStamp=%s, currencyCode=%s, amount=%s, mode=%s]", this.id, this.timeStamp,
				this.currencyCode, this.amount, this.mode);
	}
}
