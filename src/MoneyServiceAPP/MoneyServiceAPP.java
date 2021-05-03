package MoneyServiceAPP;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Stream;
import affix.java.project.moneyservice.Config;
import affix.java.project.moneyservice.Currency;
import affix.java.project.moneyservice.ExchangeSite;
import affix.java.project.moneyservice.MoneyBox;
import affix.java.project.moneyservice.MoneyServiceIO;
import affix.java.project.moneyservice.Order;
import affix.java.project.moneyservice.Transaction;
import affix.java.project.moneyservice.TransactionMode;


public class MoneyServiceAPP {

	private static Logger logger;
	private static FileHandler fh;

	public static void main(String[] args) {

		if(args.length >= 1) {
			Config.readConfigFile(args[0]);
		}

		logger = Config.setUpLogger(logger, fh, args);

		ExchangeSite theSite = new ExchangeSite(Config.getSiteName());

		theSite.startTheDay();

		boolean okInput=false;
		do {
			System.out.println("\nDo you want to use console or graphic version?");
			System.out.println("\nPress c for console ");
			System.out.println("Press g for graphic");
			System.out.println("Press e for exit Program");

			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);

			String choice =input.next().strip().toLowerCase();

			switch (choice) {
			case "c":
				mainMenuCLI(theSite);
				break;
			case "g":
				System.out.println("GUI not supported right now, console started instead! ");
				
//				MoneyServiceGUI.main(args);
//				okInput=true;
				mainMenuCLI(theSite);
				break;
			case "e":
				okInput=true;
				break;

			default:
				System.out.println("Wrong choice!");
				break;
			}
		}
		while(!okInput);
	}

	public static void mainMenuCLI(ExchangeSite theSite) {

		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		keyboard.useDelimiter(System.lineSeparator());
		boolean exit=false;

		int choice=0;
		do { 
			try { 
				System.out.println("\n\nPlease make a choice:");
				System.out.println("-----------------------------");
				System.out.println("1. - Show todays exchange rates");
				System.out.println("2. - Create order");
				System.out.println("3. - Random generate 25 orders");
				System.out.println("4. - Random generate 25 orders per day for all days in april");
				System.out.println("5. - Show cash box content");
				System.out.println("6. - Exit the program");
				System.out.println("-----------------------------");

				String input=keyboard.next();
				choice=Integer.parseInt(input);	

				switch (choice) {
				case 1: 
					System.out.println("********************* Todays rates *********************");
					for(Entry<String, Currency> er:theSite.getCurrencyMap().entrySet()) {
						if(!(er.getKey().equalsIgnoreCase(MoneyServiceIO.referenceCurrency)) && !(er.getValue().getBuyRate() == 0.000f)){
							System.out.format(er.getKey().toString());
							System.out.format(" Buying: %.3f",er.getValue().getSellRate());
							System.out.format(" Selling: %.3f",er.getValue().getBuyRate());
							System.out.println("");							
						}
					}
					System.out.println("********************************************************");

					break;

				case 2: 
					createOrder(theSite, keyboard);
					break;

				case 3:
					List<Order> listOfOrders;
					int i=0;
					boolean stop = false;
					do {
						listOfOrders = Order.generateDailyOrder(ExchangeSite.getRates(), 35);
						for(Order d: listOfOrders) {
							if(i>24) {
								stop = true;
							}else {
								if(d.getTransactionType() == TransactionMode.BUY) {
									System.out.println("Tried: "+d.toString());
									if(theSite.buyMoney(d)) {
										theSite.completeOrder(d);
										i++;
										System.out.println(i + ": Worked");
									}
								}else {
									System.out.println("Tried: "+d.toString());
									if(theSite.sellMoney(d)) {
										theSite.completeOrder(d);
										i++;
										System.out.println(i + ": Worked");
									}
								}
							}
						}

					}while(!stop);

					for(Transaction t : ExchangeSite.transactionList) {
						System.out.println(""+t.toString());
					}

					Set<String> keySet = theSite.getCurrencyMap().keySet();

					for(String k:keySet) {
						System.out.println(k+": "+theSite.getCurrencyMap().get(k).toString());
					}

					break;

					//									case 4:
					//					addNewCurrency(keyboard);
					//										break;


				case 4:
					String userInput;
					Scanner sc = new Scanner(System.in);
					System.out.println("Enter the first date: YYYY-MM-DD");
					userInput = sc.nextLine();
					LocalDate firstDate = LocalDate.parse(userInput);
					System.out.println("Enter the second date: YYYY-MM-DD");
					userInput = sc.nextLine();
					//OBS tar inte med sista dagen d�rav plus en dag.
					LocalDate secondDate = LocalDate.parse(userInput).plusDays(1);
					Stream<LocalDate> ldStream = firstDate.datesUntil(secondDate)
							.filter(ld -> !(ld.getDayOfWeek().equals(DayOfWeek.SATURDAY)) && !(ld.getDayOfWeek().equals(DayOfWeek.SUNDAY)));
					ldStream.forEach(test());
					break;

				case 5:
					System.out.println("Content in cash box: ");
					keySet = theSite.getCurrencyMap().keySet();

					for(String k:keySet) {
						System.out.println(k+": "+theSite.getCurrencyMap().get(k).getTotalValue().intValue());
					}
					break;

				case 6:
					exit=true;
					break;

				default:
					System.out.println("Wrong choice!");
					break;
				}


			}
			catch (NumberFormatException e) {
				System.out.println("Wrong choice! Try again");
				logger.warning("Wrong choice! Try again");
			}
		}
		while(!exit);

		String destination = "testShutDown.db";

		theSite.shutDownService(destination);

		System.exit(0);
	}



	private static void createOrder(ExchangeSite theSite, Scanner keyboard) {
		boolean okInput = false;
		String transactionType ="";
		TransactionMode transMode = TransactionMode.BUY;
		int amount = 0;

		System.out.println("BUY or SELL?");  
		System.out.println("(BUY if you want to buy ex 50 EUR from exchange site)");
		System.out.println("(SELL if you want to sell ex 350 GBP to exchange site)");
		System.out.println();
		System.out.println("press b for BUY a currency");
		System.out.println("press s for SELL a currency");

		do {
			transactionType = keyboard.next().strip().toLowerCase();
			switch (transactionType) {
			case "b":
				okInput = true;
				transMode = TransactionMode.SELL;
				break;
			case "s":
				okInput = true;
				transMode = TransactionMode.BUY;
				break;
			default:
				System.out.println("Wrong choice!");
				break;
			}
		}
		while(!okInput);

		System.out.println("Available currencies are: ");

		String currencyChoice="";

		if(transMode == TransactionMode.SELL) {
			Set<String> keySet = theSite.getCurrencyMap().keySet();
			for(String k:keySet) {
				if(!(k.equalsIgnoreCase(MoneyServiceIO.referenceCurrency))){
					if(theSite.getCurrencyMap().get(k).getTotalValue() >= Config.getMIN_AMMOUNT()){ 
						System.out.println(k.toString());									
					}								
				}
			}
		}
		else {
			Set<String> keySet = theSite.getCurrencyMap().keySet();
			for(String k:keySet) {
				if(!(k.equalsIgnoreCase(MoneyServiceIO.referenceCurrency))){
					System.out.println(k.toString());																	
				}
			}
		}

		System.out.println("Enter USD for US dollar, GBP for British pound... ");
		System.out.println("If you want to exit press 0");
		boolean stopOrder = false;
		do {
			okInput = false;
			currencyChoice = keyboard.next().strip().toUpperCase();

			if(currencyChoice.equals("0")) {
				okInput = true;
				stopOrder = true;
			}
			else {
				if(!theSite.getCurrencyMap().keySet().contains(currencyChoice) || theSite.getCurrencyMap().get(currencyChoice).getBuyRate() == 0.000f) {
					System.out.println("Bad input of currency, try again!");
				}
				else {
					if(transMode == TransactionMode.SELL){ 
						if(theSite.getCurrencyMap().get(currencyChoice).getTotalValue() >= Config.getMIN_AMMOUNT() |! 
								currencyChoice.equalsIgnoreCase(MoneyServiceIO.referenceCurrency)){ 
							okInput = true;
						}
						else {
							System.out.println("Bad input of currency, try again!");
						}
					}
					else {
						if(theSite.getCurrencyMap().get(currencyChoice).getBuyRate() == 0.000f) {
							System.out.println("Rates are not set for "+currencyChoice);
							boolean rateOkInput = false;
							do {
								float rate = 0;
								try {
									System.out.println("Enter currency rate (x.xxxx): ");
									String temp = keyboard.next().strip();
									rate = Float.parseFloat(temp);
									theSite.getCurrencyMap().get(currencyChoice).setBuyRate(rate * Config.getBuyRateConfig());
									theSite.getCurrencyMap().get(currencyChoice).setSellRate(rate * Config.getSellRateConfig());	
									rateOkInput = true;
								}
								catch (NumberFormatException e) {
									System.out.println("Bad input! Only number are allowed. Try again..");
									logger.warning("Bad input! Only number are allowed.");
								}
							}
							while(!rateOkInput);

						}
						okInput = true;
					}
				}	
			}
		}
		while(!okInput);

		if(!stopOrder) {

			System.out.println("Enter amount in steps of 50");
			System.out.println("Existing denominations are: 50, 100, 200, 500, 1000");
			System.out.println("(If you ex write 453 you will have 450)");
			do {
				try {
					okInput = false;
					String temp = keyboard.next();
					amount = Integer.parseInt(temp);
					if(amount >= Config.getMIN_AMMOUNT() && amount <= Config.getMAX_AMMOUNT()) {
						amount= MoneyBox.denominationControl(currencyChoice, amount);
						okInput = true;	
					}
					else {
						System.out.println("Bad input! The amount must be between "+Config.getMIN_AMMOUNT()+" and "+Config.getMAX_AMMOUNT());
						System.out.println("Try again");
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Bad input! Only number are allowed. Try again..");
					logger.warning("Bad input! Only number are allowed.");
				}
			}
			while(!okInput);

			int price= ExchangeSite.calculatePrice(currencyChoice, amount, transMode); 

			if(transMode == TransactionMode.SELL) {
				System.out.println("\nCost for buying "+amount+" "+currencyChoice+ ": "+price+" "+MoneyServiceIO.getReferenceCurrency()); 
				System.out.format("Exchange buy rate in calculation: %.3f",theSite.getCurrencyMap().get(currencyChoice).getSellRate());
			}
			else {
				System.out.println("You will get paid: "+price+ " "+MoneyServiceIO.getReferenceCurrency()+" when selling "+amount+" "+currencyChoice);
				System.out.format("Exchange sell rate in calculation: %.3f",theSite.getCurrencyMap().get(currencyChoice).getBuyRate());
			}

			System.out.println("\n\nComplete order? ");
			System.out.println("press y for complete order ");
			System.out.println("press n for cancel order");

			String choiceContinue = keyboard.next().strip().toLowerCase();
			do {
				okInput = false;
				switch(choiceContinue) {
				case "y":
					Order myOrder = new Order(amount,currencyChoice, transMode);
					if(myOrder.getTransactionType() == TransactionMode.SELL) {
						logger.finer("Order created from user input: "+myOrder.toString());
						if(theSite.sellMoney(myOrder)) {
							theSite.completeOrder(myOrder);
							System.out.println("Transaction completed! ");
							System.out.println(ExchangeSite.getTransactionList().get(ExchangeSite.getTransactionList().size()-1).toString());

						}
						else {
							System.out.println("Not enough money that currency. Order canceled");
							logger.finer("Not enough money that currency. Order canceled");
						}
					}
					else {
						logger.finer("Order created from user input: "+myOrder.toString());
						if(theSite.buyMoney(myOrder)) {
							theSite.completeOrder(myOrder);
							System.out.println("Transaction completed! ");
							System.out.println(ExchangeSite.getTransactionList().get(ExchangeSite.getTransactionList().size()-1).toString());
						}
						else {
							System.out.println("Not enough money in that currency. Order canceled");
							logger.finer("Not enough money that currency. Order canceled");
						}

					}

					okInput = true;
					break;
				case "n":
					System.out.println("Order canceled");
					okInput = true;
					break;
				default:
					System.out.println("Wrong choice!");
					break;
				}
			}
			while(!okInput);
		}
	}

	private static void addNewCurrency(Scanner keyboard) {
		String[] currecyNamesOK = {"CNY"};
		String currencyName ="";
		Float sellRate =0.0F;
		boolean okInput = false;
		do {
			okInput = false;
			boolean okAddCurrency = false;
			System.out.println("Enter name of new currency: ");
			System.out.println("Accepted names right now: ");
			for(String s : currecyNamesOK) {
				System.out.println(s);							
			}
			currencyName = keyboard.next().strip().toUpperCase();
			for(String s : currecyNamesOK) {
				if(s.equals(currencyName)) {
					okAddCurrency = true;
				}
			}
			if(okAddCurrency) {
				okInput = true;
			}
			else {
				System.out.println("Bad input of name!");
			}
		}
		while(!okInput);

		do {
			try {
				okInput = false;
				System.out.println("Enter currency rate (x.xxxx): ");
				String temp = keyboard.next().strip();
				sellRate = Float.parseFloat(temp);
				okInput = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Bad input! Only number are allowed. Try again..");
				logger.warning("Bad input! Only number are allowed.");
			}
		}
		while(!okInput);

		MoneyBox.addNewCurrency(0, currencyName, sellRate);
	}
	public static Consumer<LocalDate> test(){
		Consumer<LocalDate> dateConsumer = (ld) -> {
			List <Transaction> templist = new ArrayList<>();
			LocalDateTime timestamp = ld.atStartOfDay();
			ExchangeSite temp = new ExchangeSite("North",timestamp);
			temp.startTheDay();
			MoneyServiceIO.setRefDate(ld);
			List<Order> listOfOrders = Order.generateDailyOrder(ExchangeSite.getRates(), 35);
			for(Order d: listOfOrders) {
				Transaction temptrans = new Transaction(d, timestamp);
				templist.add(temptrans);
				if(d.getTransactionType() == (TransactionMode.BUY)) {
					if(temp.buyMoney(d)) {
						temp.completeOrder(d);
						System.out.println("(b)Succses order complete");
					}
					else {
						System.out.println("Error couldnt afford");
					}
				}
				else {
					if(temp.sellMoney(d)) {
						temp.completeOrder(d);
						System.out.println("(s)Succses order complete");
					}
					else {
						System.out.println("Error not enough money");
					}
				}			
			}
			System.out.println(templist);
		};
		return dateConsumer;
	}
}