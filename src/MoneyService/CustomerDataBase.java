package MoneyService;

import java.util.List;
import java.util.Map;

public class CustomerDataBase {

	private final long id;
	List<Transaction> dailyTransactions;
	Map<String,List<Transaction>> customerInfoHistory;
	Map<String,List<Order>> customerOrderHistory;
	public CustomerDataBase(long id, List<Transaction> dailyTransactions,

			Map<String, List<Transaction>> customerInfoHistory, Map<String, List<Order>> customerOrderHistory) {
		super();
		this.id = id;
		this.dailyTransactions = dailyTransactions;
		this.customerInfoHistory = customerInfoHistory;
		this.customerOrderHistory = customerOrderHistory;
	}

	public void Export() {
		
	}

	public void Import() {
		
	}


	public List<Transaction> getDailyTransactions() {
		return dailyTransactions;
	}

	public void setDailyTransactions(List<Transaction> dailyTransactions) {
		this.dailyTransactions = dailyTransactions;
	}

	public Map<String, List<Transaction>> getCustomerInfoHistory() {
		return customerInfoHistory;
	}

	public void setCustomerInfoHistory(Map<String, List<Transaction>> customerInfoHistory) {
		this.customerInfoHistory = customerInfoHistory;
	}

	public Map<String, List<Order>> getCustomerOrderHistory() {
		return customerOrderHistory;
	}

	public void setCustomerOrderHistory(Map<String, List<Order>> customerOrderHistory) {
		this.customerOrderHistory = customerOrderHistory;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format(
				"CustomerDataBase [id=%s, dailyTransactions=%s, customerInfoHistory=%s, customerOrderHistory=%s]", id,
				dailyTransactions, customerInfoHistory, customerOrderHistory);
	}





}
