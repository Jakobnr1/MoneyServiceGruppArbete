package MoneyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import affix.java.effective.streams.StreamPipe.typeOfTransaction;



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
		this.date = MoneyServiceIO.refDate;

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
	 * Needs polish!
	 */
	public int calculatePrice(String currencyCode, int amount, List<ExchangeRate> currencyList) {
		Iterator<E> listIterator = currencyList.iterator();
		int cost = 0;
		while(listIterator.hasNext()) {
			if(listIterator.next().getName().equals(currencyCode)) {
				cost = (float)((float)amount*listIterator.getExchangeRate())*1.005;
			}
		}
		return cost;
	}


	public List<Order> generateDailyOrder(List<ExchangeRate> tempCurrencies, int amount) {
		Random rand = new Random();
		List<Order> tempList = new ArrayList<Order>(amount);
        List<Integer> tempValues = new ArrayList<Integer>(amount); 
		List<typeOfTransaction> tempTransactionTypes = new ArrayList<typeOfTransaction>(2);
		tempTransactionTypes.add(typeOfTransaction.BUY);
		tempTransactionTypes.add(typeOfTransaction.SELL);
        
		for(int i=0; i<amount; i++){
			Stream<Integer> randomFilteredValues = 
					Stream.generate(rd::nextInt)
					.limit(250)
					.map(d -> d%2500)
					.filter(d -> d>50)
					.filter(d -> d%50==0);                        

			Optional<Integer> optionalHighValue = randomFilteredValues.findAny();
			optionalHighValue.ifPresent(v -> tempValues.add(v));
			if(tempValues.size()<amount&&i==(amount--))
				i--;
		}
		
		
		tempValues.forEach((d) -> tempList.add(new Order(d,
				tempCurrencies.get(rand.nextInt(tempCurrencies.size())),
				tempTransactionTypes.get(rand.nextInt(tempTransactionTypes.size())))));	//Need Random 
	

		return tempList;
	}

}