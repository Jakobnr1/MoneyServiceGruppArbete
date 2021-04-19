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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MoneyServiceIO {

	public static  String projectConfigFilename = "ProjectConfig_2021-04-01.txt";
	public static String currencyConfigFilename = "CurrencyConfig_2021-04-01.txt";
	static String serializedDailyTransactionFilename = "DailyTransactions.ser";
	static String serializedCustomerDataBaseFilename = "CustomerDatabase.ser";
	static String textFormattedDailyTransactions = "DailyTransactions.txt";
	public static String referenceCurrency;
	public static LocalDate refDate;
	public static LocalDateTime LDT = LocalDateTime.now(); //TODO
	
	
	/**
	 * Parses the ProjectConfigFile
	 * @param listToBeParsed
	 * @return Map<String,Integer> Containing: CurrencyName and Value
	 */
	public static Map<String,Integer> parseProjectConfig(List<String> listToBeParsed){
		Map<String,Integer> currencyMap = new HashMap<>();
		Stream<String> fileNameStream = listToBeParsed.stream().limit(1);
		Iterator<String> fileNameStreamIter = fileNameStream.iterator();
		while(fileNameStreamIter.hasNext()) {
			String[] configParts = fileNameStreamIter.next().split("=");
			for(String s:configParts) {
				s=s.trim();
			}
			MoneyServiceIO.currencyConfigFilename = configParts[1].trim();
			String[] dateParts = configParts[1].split("_");
			String[] dateParts2 = dateParts[1].split("\\.");
			MoneyServiceIO.refDate = LocalDate.parse(dateParts2[0].trim());
		}
		Stream<String> currencyStream = listToBeParsed.stream().skip(2);
		Iterator<String> currencyIterator = currencyStream.iterator();
		while(currencyIterator.hasNext()){
			String temp = currencyIterator.next();
			if(!(temp.contains("End") || temp.contains("ReferenceCurrency"))){
			String[] boxParts = temp.split("=");
			currencyMap.putIfAbsent(boxParts[0].trim(), Integer.parseInt(boxParts[1].trim())); //TODO ändra type till currency
		}
		}
		Stream<String> refString = listToBeParsed.stream().skip(2);
		Iterator<String> refIterator = refString.iterator();
		while(refIterator.hasNext()) {
			String tempString = refIterator.next();
			if(tempString.contains("ReferenceCurrency")) {
				String[] parts = tempString.split("=");
				MoneyServiceIO.referenceCurrency = parts[1].trim();
			}
			
		}
		return currencyMap;
	}
	
	/**
	 * Parses an entire list of Currencies
	 * @param List of Strings to be parsed
	 * @return the parsed List<ExchangeRate>
	 */
	public static List<ExchangeRate> parseCurrencyConfig(List<String> listToBeParsed) {
		List<ExchangeRate> exchangeRateList = new ArrayList<>();
		List<String> CurrencyList = new ArrayList<String>();
		Stream<String> streamToBeIterated = listToBeParsed.stream().skip(1);
		Iterator<String> iter = streamToBeIterated.iterator();
		while(iter.hasNext()) {
			String tempString = iter.next();
			CurrencyList.add(tempString);
		}
		Iterator<String> listIter = CurrencyList.iterator();
		while(listIter.hasNext()) {
			String[] parts = listIter.next().split("\t");
			for(String s:parts) {
				s = s.trim();
			}
			LocalDate day = LocalDate.parse(parts[0]);
			String[] valueParts = parts[2].split(" ");
			int scalar = Integer.parseInt(valueParts[0].trim());
			Float price = Float.parseFloat(parts[3].trim());
			try {
			ExchangeRate er = new ExchangeRate(day,scalar,valueParts[1].trim(),price);
			exchangeRateList.add(er);
			}
			catch(IllegalArgumentException ioe) {System.out.println(String.format("NO"));
			};
		}
		return exchangeRateList;
	}

	/**
	 * Reads a file and returns a list of strings.
	 * @param  filename for storage.
	 * @return  The read List<String>.
	 */
	public static List<String> readTextFiles(String filename) {
		List<String> readStringList = new ArrayList<String>();
		try(BufferedReader bf = new BufferedReader(new FileReader(filename.trim()))) {
			while(bf.ready()) {
				String temporaryString = bf.readLine();
				readStringList.add(temporaryString);
			}
		}catch(IOException ioe) {System.out.println("Exception when reading file");}
		return readStringList;
	}

	/**
	 * Saves the daily transactions in serialized format
	 * @param listToBeSaved, filename for storage.
	 * @return boolean true if done
	 */
	public static boolean saveSerializedDailyTransactions(List<Transaction> listToBeSaved, String filename) {
		boolean saved = false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename.trim()))){
			oos.writeObject(listToBeSaved);
		}
		catch(IOException ioe) {System.out.println(String.format("Error when saving serialized daily transaction"+ioe.toString()));}
		saved = true;
		return saved;
	}
	

	/**
	 * Saves a daily report in serialized form.
	 * 
	 * @param Rapport R which includes the unique daily FileName
	 * @return Boolean true if successful.
	 */
	
	public static boolean saveSerializedReport(Rapport r) {
		boolean saved = false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(r.getUniqueFileName().trim()))){
			oos.writeObject(r.getDailyTransaction());
		}
		catch(IOException ioe) {System.out.println(String.format("Error when saving serialized daily transaction"+ioe.toString()));}
		saved = true;
		return saved;
	}
	
	

	/**
	 * Reads Serialized daily transactions
	 * @param  filename for storage.
	 * @return the list
	 */

	@SuppressWarnings("unchecked")
	public static List<Transaction> readSerializedDailyTransactionList(String filename){
		List<Transaction> transactionList = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename.trim()))){
			transactionList = (List<Transaction>) ois.readObject();
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
	public static boolean saveDailyTransactionListAsText(List<Transaction> dailyList, String filename){
		boolean saved = false;
		try(PrintWriter pw = new PrintWriter(new FileWriter(filename.trim()))){
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
	/*
	public static boolean saveSerializedCustomerDatabase(Map<String,List<Customer>> mapToBeSaved) {
		boolean saved = false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedCustomerDataBaseFilename))){
			oos.writeObject(mapToBeSaved);
		}
		catch(IOException ioe) {System.out.println(String.format("Error when saving serialized daily transaction"+ioe.toString()));}
		saved = true;
		return saved;
	}	
	 */
	/**
	 * Reads serialized customer database 
	 * @return the map
	 */
	/*
	public static Map<String,List<Customer>> readSerializedCustomerDatabase(){
		Map<String,List<Customer>> customerMap = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedCustomerDataBaseFilename))){
		customerMap = (Map<String,List<Customer>>)ois.readObject();
		}
		catch(IOException |ClassNotFoundException ioe) {System.out.println(String.format("Error when reading serialized daily transactions"+ioe.toString()));
		}
		return customerMap;
	}
	 */

}
