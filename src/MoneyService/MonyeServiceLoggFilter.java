package MoneyService;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MonyeServiceLoggFilter implements Filter{

	@Override
	public boolean isLoggable(LogRecord lr) {

		if(lr.getLevel().equals(Level.FINE) || lr.getLevel().equals(Level.FINER)
				|| lr.getLevel().equals(Level.FINEST)){
			return true;
		}
		else{	
			System.out.println("No logging: "+lr.getSourceClassName());
			return false;
		}
	}	
}