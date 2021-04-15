package MoneyServiceAPP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MoneyService.ExchangeRate;
import MoneyService.MoneyServiceIO;

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
		
		
		
		
	}
	

}
