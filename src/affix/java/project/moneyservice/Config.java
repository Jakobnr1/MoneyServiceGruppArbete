package affix.java.project.moneyservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

public class Config {
	private static String siteName= "default";
	private static String logName= "MoneyServiceLog";
	private static String logFormat = "text";
	private static String logLevel = "OFF";
	private static int MIN_AMMOUNT = 50;
	private static int MAX_AMMOUNT = 50000;
	private static float buyRateConfig = 0.995f;
	private static float sellRateConfig = 1.005f;
	private static char[] password = {'Q','w','e','r','t','y','u','i'}; 


	public static List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();

	private static Logger logger;

	static {
		logger = Logger.getLogger("affix.java.project.moneyservice");
	}

	public enum LogLev {ALL, CONFIG, FINE, FINER, FINEST, INFO, SEVERE, WARNING, OFF}

	public static boolean readConfigFile(String filename) {
		boolean okRead = false;
		int ok=0;
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			while(br.ready()){
				String input = br.readLine();
				String[] parts = input.split("="); 

				if(parts.length == 2) {
					String key = parts[0].strip();
					String value = parts[1].strip();


					switch (key) {
					case "siteName":
						if(value.isEmpty()) {
							System.out.println("Site name missing in config file!");
							System.out.println("Default used: "+Config.getSiteName());
							break;
						}
						Config.setSiteName(value);
						System.out.println("Site name set to: "+value);
						ok++;		
						break;
					case "logName":
						if(value.isEmpty()) {
							System.out.println("Log name missing in config file!");
							System.out.println("Default used: "+Config.getLogName());
							break;
						}
						Config.setLogName(value);
						System.out.println("Log name set to: "+value);
						ok++;			
						break;
					case "logFormat":	
						if((value.isEmpty()) || !(value.toLowerCase().equals("text") || value.toLowerCase().equals("xml"))){
							System.out.println("************Bad log format in config file!***********");
							System.out.println("Default used: "+Config.getLogFormat());
							break;
						}
						Config.setLogFormat(value.toLowerCase());
						System.out.println("Log format set to: "+value);
						ok++;		
						break;

					case "logLevel":					
						for(LogLev l: LogLev.values()) {
							String temp = l.toString();
							if(temp.equals(value)){
								logLevel = value.toUpperCase().trim();
								System.out.println("Log level set to: "+value);
								ok++;
								break;
							}
						}						 
						break;

					case "min_ammount":
						Config.setMIN_AMMOUNT(Integer.parseInt(value));
						System.out.println("min_ammount set to: "+value);
						ok++;
						break;
					case "max_ammount":
						Config.setMAX_AMMOUNT(Integer.parseInt(value));
						System.out.println("max_ammount set to: "+value);
						ok++;
						break;
					case "buyRate":
						setBuyRateConfig(Float.parseFloat(value));
						System.out.println("buyRate marginal set to: "+value);
						ok++;
						break;
					case "sellRate":
						setSellRateConfig(Float.parseFloat(value));
						System.out.println("sellRate marginal set to: "+value);
						ok++;
						break;
					case "password":
						setPassword(value);
						ok++;
						break;
					case "configFolder":
						MoneyServiceIO.setFolderPath(value);
						System.out.println("File Path for configFolder set");
						ok++;
						break;
					case "dailyRatesFolder":
						MoneyServiceIO.setFolderPath(value);
						System.out.println("File Path for DailyRateFolder set");
						ok++;
						break;
					case "orderFolder":
						MoneyServiceIO.setFolderPath(value);
						System.out.println("File Path for OrderFolder set");
						ok++;
						break;
					case "siteReportsFolder":
						MoneyServiceIO.setFolderPath(value);
						System.out.println("File Path for SiteReportFolder set");
						ok++;
						break;
					case "transactionsFolder":
						MoneyServiceIO.setFolderPath(value);
						System.out.println("File Path for TransactionFolder set");
						ok++;
						break;
					default:
						break;
					}
				}

			}
		}
		catch (IOException ioe) {
			System.out.println("Exception occurred: " + ioe);
		}
		catch (NumberFormatException e) {
				}

		if(ok == 14) {
			System.out.println("Configuration of the system OK!");
			return okRead=true;
		}

		return okRead;
	}

	/**
	 * The method sets buy and sell rate in each currency.
	 * @param currencyList
	 * @param currencyMap
	 */

	public static void setRatesInCurrency(List<ExchangeRate> currencyList, Map<String, Currency> currencyMap) {			
		for(ExchangeRate s : currencyList) {
			String key = s.getName();
			if(currencyMap.containsKey(s.getName())) {
				Float buyRate = s.getExchangeRate() * getBuyRateConfig();
				currencyMap.get(key).setBuyRate(buyRate);
				Float sellRate = s.getExchangeRate() * getSellRateConfig();
				currencyMap.get(key).setSellRate(sellRate);
				logger.finer(""+key+" buyRate: "+buyRate+ ", sellRate: "+sellRate);				
			}
		}
	}


	public static List<ExchangeRate> setTheRates() {
		List<ExchangeRate> test = new ArrayList<ExchangeRate>(MoneyServiceIO.parseCurrencyConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.getPathName("DailyRates")+MoneyServiceIO.currencyConfigFilename)));
		logger.finer("*********** Getting rates from "+MoneyServiceIO.currencyConfigFilename+ " ************");

		return test;
	}


	public static MoneyBox fillTheMoneyBox(MoneyBox theBox, Map<String, Currency> currencyMap ) {
		Map<String, Double> testMap = new HashMap<String,Double>(MoneyServiceIO.parseProjectConfig(MoneyServiceIO.readTextFiles(MoneyServiceIO.getPathName("Configs")+MoneyServiceIO.projectConfigFilename)));

		Set<String> keySet = testMap.keySet();
		for(String k:keySet) {
			Currency tempCurrency = new Currency(testMap.get(k).intValue(), 0.0f, 0.0f);
			currencyMap.putIfAbsent(k, tempCurrency);
			logger.finer("Filled MoneyBox with: "+k+" amount: "+tempCurrency);
		}

		return theBox;
	}

	public static boolean controlPwd(String temp) {

		char[] tempPass = new char[temp.length()];

		for(int i=0; i<temp.length(); i++) {
			tempPass[i] = temp.charAt(i);
		}
System.out.println(password);
		boolean passwordMatch = Arrays.equals(tempPass, password);

		return passwordMatch;
	}


	static void setPassword(String password) {

		for(int i=0; i<password.length();i++) {
			Config.password[i] = password.charAt(i);			
		}
	}

	public static String getLogName() {
		return logName;
	}


	public static List<ExchangeRate> getExchangeRateList(){
		return exchangeRateList;
	}

	public static boolean setMIN_AMMOUNT(int MIN_AMMOUNT) {
		Config.MIN_AMMOUNT = MIN_AMMOUNT;
		return true;
	}

	public static boolean setMAX_AMMOUNT(int MAX_AMMOUNT) {
		Config.MAX_AMMOUNT = MAX_AMMOUNT;
		return true;
	}


	public static int getMIN_AMMOUNT() {
		return MIN_AMMOUNT;
	}

	public static int getMAX_AMMOUNT() {
		return MAX_AMMOUNT;
	}

	public static float getBuyRateConfig() {
		return buyRateConfig;
	}


	public static void setBuyRateConfig(float buyRateConfig) {
		Config.buyRateConfig = buyRateConfig;
	}


	public static float getSellRateConfig() {
		return sellRateConfig;
	}


	public static void setSellRateConfig(float sellRateConfig) {
		Config.sellRateConfig = sellRateConfig;
	}

	public static String getLogFormat() {
		return logFormat;
	}

	public static String getLogLevel() {
		return logLevel;
	}


	public static void setLogName(String logName) {
		Config.logName = logName;
	}

	public static void setLogFormat(String logFormat) {
		Config.logFormat = logFormat;
	}

	public static void setLogLevel(Level logLevel) {
		logger.setLevel(logLevel);
	}

	public static void setLogger(Logger logger) {
		Config.logger = logger;
	}




	public static String getSiteName() {
		return siteName;
	}


	public static void setSiteName(String siteName) {
		Config.siteName = siteName;
	}

}
