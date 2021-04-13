package MoneyService;

import java.time.LocalDate;

public class Order {
	
	public enum typeOfTransaction {SELL, BUY}
	
	private typeOfTransaction TransactionType;
	private static long id = 1;
	private long orderId;
	LocalDate date;
	private int value;
	private String currencyCode;
	
	public Order(int value, String currencyCode,String Transaction) {
		this.orderId = id++;
		this.value = value;
		this.currencyCode = currencyCode;
		this.TransactionType = typeOfTransaction.BUY; //Needs 
		this.date = LocalDate.now();
	}
	
	/**
	 * @return the Order Id
	 */
	public long getOrderId() {
		return orderId;
	}
	
	/**
	 * @return the Order Value
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * @return the Order TransactionType
	 */
	public typeOfTransaction getTransactionType(){
		return TransactionType;
	}
	
	/**
	 * @return the Order currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * @return the Order Date
	 */
	public LocalDate getOrderDate() {
		return date;
	}
	
	
	
	
	
	//automatiserad suppliers
	
	
	
}