package MoneyService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MoneyServiceIO {

	static String configFilename = "Config.txt";
	static String serializedDailyTransactionFilename = "DailyTransactions.ser";
	static String serializedCustomerDataBaseFilename = "CustomerDatabase.ser";
	static String textFormattedDailyTransactions = "DailyTransactions.txt";
	

	/**
	 * Helping method for parsing configuration data.
	 * @param StringToBeParsed
	 * @return
	 */
	public static ExchangeRate parseConfig(String StringToBeParsed) {
		String[] parts = StringToBeParsed.split("=");
		Float rate = Float.parseFloat(parts[1].trim());
		ExchangeRate parsed = new ExchangeRate(parts[0].trim(),rate);
		if(parsed.currencyName != null && parsed.exchangeRate != 0) {
			return parsed;
		}
		else {
			throw new IllegalArgumentException(String.format("Error while parsing ExchangeRates"));
		}
	}

	/**
	 * Reads the configuration file and parses the ExchangeRates into the configuration
	 * @return boolean true if read.
	 */
	public static boolean readConfigTextFile() {
		boolean read = false;
		try(BufferedReader bf = new BufferedReader(new FileReader(configFilename))) {
			while(bf.ready()) {
				String temporaryString = bf.readLine();
				ExchangeRate parsedRate = parseConfig(temporaryString);
				Config.exchangeRateList.add(parsedRate);
			}
		}catch(IOException ioe) {System.out.println("Exception when reading file");}
		read = true;
		return read;
	}
	
	/**
	 * Saves the daily transactions in serialized format
	 * @param listToBeSaved
	 * @return boolean true if done
	 */
	public static boolean saveSerializedDailyTransactions(List<Transaction> listToBeSaved) {
		boolean saved = false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedDailyTransactionFilename))){
			oos.writeObject(listToBeSaved);
		}
		catch(IOException ioe) {System.out.println(String.format("Error when saving serialized daily transaction"+ioe.toString()));}
		saved = true;
		return saved;
	}
	
	/**
	 * Reads Serialized daily transactions
	 * @return the list
	 */
	
	public static List<Transaction> readSerializedDailyTransactionList(){
		List<Transaction> transactionList = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedDailyTransactionFilename))){
		transactionList = (List<Transaction>)ois.readObject();
		}
		catch(IOException |ClassNotFoundException ioe) {System.out.println(String.format("Error when reading serialized daily transactions"+ioe.toString()));
		}
		return transactionList;
	}
	
	/**
	 * Saves daily transactions as List
	 * @param dailyList
	 * @return boolean true if successful.
	 */
	public static boolean saveDailyTransactionListAsText(List<Transaction> dailyList){
		boolean saved = false;
		try(PrintWriter pw = new PrintWriter(new FileWriter(textFormattedDailyTransactions))){
			for(Transaction t:dailyList) {
				pw.println(t.toString());
			}
		}
			catch(IOException ioe) {System.out.println(String.format("Error when Saving Daily Transactions as text" + ioe.toString()));
		}
		saved = true;
		return saved;
		
	}

	/**
	 * Saves serialized customer database 
	 * @param mapToBeSaved
	 * @return boolean true if done
	 */
	
	public static boolean saveSerializedCustomerDatabase(Map<String,List<Customer>> mapToBeSaved) {
		boolean saved = false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedCustomerDataBaseFilename))){
			oos.writeObject(mapToBeSaved);
		}
		catch(IOException ioe) {System.out.println(String.format("Error when saving serialized daily transaction"+ioe.toString()));}
		saved = true;
		return saved;
	}	
	
	/**
	 * Reads serialized customer database 
	 * @return the map
	 */
	public static Map<String,List<Customer>> readSerializedCustomerDatabase(){
		Map<String,List<Customer>> customerMap = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedCustomerDataBaseFilename))){
		customerMap = (Map<String,List<Customer>>)ois.readObject();
		}
		catch(IOException |ClassNotFoundException ioe) {System.out.println(String.format("Error when reading serialized daily transactions"+ioe.toString()));
		}
		return customerMap;
	}
	

}
