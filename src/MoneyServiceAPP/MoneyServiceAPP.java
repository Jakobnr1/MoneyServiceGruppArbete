package MoneyServiceAPP;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MoneyService.ExchangeRate;
import MoneyService.MoneyServiceIO;
import MoneyService.Transaction;
import MoneyService.Order.typeOfTransaction;

public class MoneyServiceAPP {
	
	public static void main(String[] args) {
		
		//MoneyServiceIO.readCurrencyConfigTextFiles("sdfkj");
		
		Map<String, Integer> testMap = new HashMap<String,Integer>(MoneyServiceIO.parseProjectConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.projectConfigFilename)));
		
		Set<String> keySet = testMap.keySet();
		for(String k:keySet) {
			System.out.println(k.toString());
		}
		
		
		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.currencyConfigFilename)));
		for(ExchangeRate er:test) {
			System.out.println(er.toString());
		}
		
		
		List<Transaction> DailyTransactions = new ArrayList<>();
		
		Transaction t1 = new Transaction("Eur", 3400, typeOfTransaction.BUY);
		Transaction t2 = new Transaction("Eur", 5000, typeOfTransaction.SELL);
		Transaction t3 = new Transaction("Eur", 7500, typeOfTransaction.SELL);
		DailyTransactions.add(t1);
		DailyTransactions.add(t2);
		DailyTransactions.add(t3);
		
		int i = 0;
		System.out.println("These are transactions from the list:");
		for(Transaction t: DailyTransactions) {
			System.out.println(t.toString());
			System.out.println(i++);
		}
		
		
		System.out.println(t1.toString());
		System.out.println(t2.toString());
		System.out.println(t3.toString());
		
		System.out.println(t1.getId());
		System.out.println(t2.getId());
		System.out.println(t3.getId());
		
		System.out.println(t1.getTimeStamp().toLocalDate());
		System.out.println(t2.getTimeStamp().toLocalDate());
		System.out.println(t3.getTimeStamp().toLocalDate());
		
		
		System.out.println(t1.getAmount());
		System.out.println(t2.getAmount());
		System.out.println(t3.getAmount());
		 
		System.out.println(t1.getCurrencyCode());
		System.out.println(t2.getCurrencyCode());
		System.out.println(t3.getCurrencyCode());
		

	}
	

}
