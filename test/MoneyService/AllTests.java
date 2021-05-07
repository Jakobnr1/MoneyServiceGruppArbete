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
	testOrder.class,
	testReport.class,
	testTransaction.class,
	testTransactionMode.class,
	})
public class AllTests {
}
 