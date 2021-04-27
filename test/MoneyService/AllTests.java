package MoneyService;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	testConfig.class, 
	TestCurrency.class,
	testExchangeRate.class, 
	TestExchangeSite.class,
	TestMoneyBox.class, 
	testMoneyServiceIO.class,
	})
public class AllTests {

}
