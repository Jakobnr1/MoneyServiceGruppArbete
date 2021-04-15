package MoneyService;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import MoneyService.Order.typeOfTransaction;

public class TestExchangeSite {

	
	
	@Test
	public void testBuyMoney() {
		
		
		Order orderData = new Order(500,"EUR", typeOfTransaction.BUY );
		Map <String, Currency> tempMap = new TreeMap<>();
		tempMap.putIfAbsent("EUR", new Currency(2500));
		MoneyBox temp = new MoneyBox(tempMap);
		
		ExchangeSite tempSite = new ExchangeSite();
		Boolean test =tempSite.buyMoney(orderData);
		System.out.println(test);
		
		assertEquals(2000, temp.getCurrencyMap().get("EUR").getTotalValue());
	}
	@Test
	public void testSellMoney() {
		Order orderData = new Order(500,"EUR", typeOfTransaction.BUY );
		Map <String, Currency> tempMap = new TreeMap<>();
		tempMap.putIfAbsent("EUR", new Currency(2500));
		MoneyBox temp = new MoneyBox(tempMap);
		
		ExchangeSite tempSite = new ExchangeSite();
		Boolean test =tempSite.sellMoney(orderData);
		System.out.println(test + "test");
		
		assertEquals(3000, temp.getCurrencyMap().get("EUR").getTotalValue());
	}

}
