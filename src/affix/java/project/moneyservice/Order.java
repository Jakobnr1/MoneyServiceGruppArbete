package affix.java.project.moneyservice;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.stream.Stream;



public class Order {

	private TransactionMode TransactionType;
	private int value;
	private String currencyCode;



	public Order(int value, String currencyCode,TransactionMode Transaction) {
		this.value = value;
		this.currencyCode = currencyCode;
		this.TransactionType = Transaction;

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
	public TransactionMode getTransactionType(){
		return TransactionType;
	}

	/**
	 * @return the Order currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}



	/**
	 * 
	 */
	@Override
	public String toString() {
		return String.format("OrderData [currencyCode=%s, amount= %d, mode= %s ]",
				this.currencyCode, this.value, this.TransactionType);
	}


	/**
	 * Generates randomized Orders depending on the parameters sent in
	 * @param tempCurrencies is the current Currencies available
	 * @param amount is the amount of Orders the method returns
	 * @return a List of Orders
	 */

	public static List<Order> generateDailyOrder(List<ExchangeRate> tempCurrencies, int amount) {
		Random rand = new Random();
		List<Order> tempList = new ArrayList<Order>(amount);
		List<Integer> tempValues = new ArrayList<Integer>(amount); 
		List<TransactionMode> tempTransactionTypes = new ArrayList<TransactionMode>(2);
		tempTransactionTypes.add(TransactionMode.BUY);
		tempTransactionTypes.add(TransactionMode.SELL);

		do { Stream<Integer> randomFilteredValues = Stream.generate(rand::nextInt).limit(250)
				.map(d -> d%2500)      
				.filter(d -> d>50)   
				.filter(d -> d%50==0); 
		Optional<Integer> optionalHighValue = randomFilteredValues.findAny(); 
		optionalHighValue.ifPresent(v -> tempValues.add(v));   
		}while(tempValues.size()<amount);

		tempValues.forEach((d) -> tempList.add(new Order(d,
				tempCurrencies.get(rand.nextInt(tempCurrencies.size())).getName(),
				tempTransactionTypes.get(rand.nextInt(tempTransactionTypes.size())))));	//Need Random 


		return tempList;
	}

}