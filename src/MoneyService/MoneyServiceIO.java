package MoneyService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MoneyServiceIO {

	static String configFileName = "Config.txt";

	public static ExchangeRate parseConfigRates(String StringToBeParsed) {
		String[] parts = StringToBeParsed.split("=");
		Float rate = Float.parseFloat(parts[1].trim());
		ExchangeRate parsed = new ExchangeRate(parts[0].trim(),rate);
		if(parsed.currencyName != null && parsed.exchangeRate != null) {
			return parsed;
		}
		else {
			throw new IllegalArgumentException(String.format("Error while parsing ExchangeRates"));
		}
	}

	public static boolean readConfigTextFile() {
		boolean read = false;
		try(BufferedReader bf = new BufferedReader(new FileReader(configFileName))) {
			while(bf.ready()) {
				String temporaryString = bf.readLine();
				ExchangeRate parsedRate = parseConfigRates(temporaryString);
				Config.exchangeRateList.add(parsedRate);
			}
		}catch(IOException ioe) {System.out.println("Exception when reading file");}
		read = true;
		return read;
	}

}
