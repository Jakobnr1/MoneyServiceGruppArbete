package MoneyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;



public class Order {

	public enum typeOfTransaction {SELL, BUY}

	//id= date&id++
	private typeOfTransaction TransactionType;
	private static long id = 1;
	private long orderId;
	LocalDate date;
	private int value;
	private String currencyCode;
	


	public Order(int value, String currencyCode,typeOfTransaction Transaction) {
		this.orderId = id++;
		this.value = value;
		this.currencyCode = currencyCode;
		this.TransactionType = Transaction;
		this.date = LocalDate.now(); //LocalDate.parse(MoneyServiceIO.refDate); changed this to right code

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

	/**
	 * 
	 */
	@Override
	public String toString() {
		return String.format("OrderData [id= %s, timeStamp=%s, currencyCode=%s, amount= %d, mode= %s ]",
				this.orderId, this.date, this.currencyCode, this.value, this.TransactionType);
	}
	
	/**
	 * TODO add function to return the cost of the amount bought
	 */
	public int calculatePrice(String currencyCode, int amount, List<ExchangeRate> currencyList) {
		
		int cost = 2;
		return cost;
	}

	/**
	 * TODO fix randomize currency and buy/sell
	 */

	public List<Order> generateDailyOrder(List<ExchangeRate> tempCurrencies ) {
		Random rd = new Random();
		List<Order> tempList = new ArrayList<Order>(25);
        List<Integer> tempValues = new ArrayList<Integer>(25);
		for(int i=0; i<25; i++){
			Stream<Integer> randomFilteredValues = 
					Stream.generate(rd::nextInt)
					.limit(250)
					.map(d -> d%2500)
					.filter(d -> d>50)
					.filter(d -> d%50==0);                        

			Optional<Integer> optionalHighValue = randomFilteredValues.findAny();
			optionalHighValue.ifPresent(v -> tempValues.add(v));
			if(tempValues.size()<25&&i==24)
				i--;

		}
		
		tempValues.forEach((d) -> tempList.add(new Order(d, "EUR", typeOfTransaction.BUY)));	//Need Random 
	

		return tempList;
	}


}