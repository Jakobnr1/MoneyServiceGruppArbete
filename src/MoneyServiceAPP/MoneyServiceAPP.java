package MoneyServiceAPP;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import MoneyService.Config;
import MoneyService.Currency;
import MoneyService.ExchangeRate;
import MoneyService.ExchangeSite;
import MoneyService.MoneyServiceIO;
import MoneyService.Order;
import MoneyService.Order.TransactionMode;
import MoneyService.Transaction;


public class MoneyServiceAPP {

	public static void main(String[] args) {

		//Starts the day
		ExchangeSite theSite = new ExchangeSite("North");
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
				MoneyServiceGUI.main(args);
				okInput=true;
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
				System.out.println("1. - Show todays exchange rates");
				System.out.println("2. - Create order");
				System.out.println("3. - Random generate 25 orders");
				System.out.println("4. - Exit the program");

				String input=keyboard.next();
				choice=Integer.parseInt(input);	

				switch (choice) {
				case 1: 
					System.out.println("********************* Todays rates *********************");
					for(ExchangeRate er:ExchangeSite.getRates()) {
						System.out.println(er.toString());
					}
					System.out.println("********************************************************");

					break;

				case 2: 

					boolean okInput = false;
					String transactionType ="";
					TransactionMode transMode = TransactionMode.BUY;
					int amount = 0;


					System.out.println("BUY or SELL?");  
					System.out.println("(BUY if you want to buy ex 500 EUR from exchange site)");
					System.out.println("(SELL if you want to sell ex 320 GBP to exchange site)");
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

					System.out.println("The supported currencies are: ");

					String currencyChoice="";

					if(transMode == TransactionMode.SELL) {
						Set<String> keySet = theSite.getCurrencyMap().keySet();
						for(String k:keySet) {
							if(theSite.getCurrencyMap().get(k).getTotalValue() >= Config.getMIN_AMMOUNT()){ 
								System.out.println(k.toString());									
							}
						}
					}
					else {
						Set<String> keySet = theSite.getCurrencyMap().keySet();
						for(String k:keySet) {
							System.out.println(k.toString());									
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
							if(!theSite.getCurrencyMap().keySet().contains(currencyChoice)) {
								System.out.println("Bad input of currency, try again!");
							}
							else {
								if(theSite.getCurrencyMap().get(currencyChoice).getTotalValue() >= Config.getMIN_AMMOUNT()){ 
									okInput = true;
								}
								else {
									System.out.println("Bad input of currency, try again!");
								}
							}	
						}
					}
					while(!okInput);

					if(!stopOrder) {

						System.out.format("Enter amount");
						do {
							try {
								okInput = false;
								String temp = keyboard.next();
								amount = Integer.parseInt(temp);
								if(amount >= Config.getMIN_AMMOUNT() && amount <= Config.getMAX_AMMOUNT()) {
									okInput = true;	
								}
								else {
									System.out.println("Bad input! The amount must be min "+Config.getMIN_AMMOUNT());
								}
							}
							catch (NumberFormatException e) {
								System.out.println("Bad input! Only number are allowed. Try again..");
							}
						}
						while(!okInput);

						int price= ExchangeSite.calculatePrice(currencyChoice, amount); // Show the price
						System.out.println("Cost: "+price+" "+MoneyServiceIO.getReferenceCurrency()); 

						// OK input from user.
						System.out.println("\nComplete order? ");
						System.out.println("press y for complete order ");
						System.out.println("press n for cancel order");

						String choiceContinue = keyboard.next().strip().toLowerCase();
						do {
							okInput = false;
							switch(choiceContinue) {
							case "y":
								Order myOrder = new Order(amount,currencyChoice, transMode);
								if(myOrder.getTransactionType() == TransactionMode.SELL) {
									if(theSite.sellMoney(myOrder)) {
										theSite.completeOrder(myOrder);
										System.out.println("Transaction completed! ");
										System.out.println(ExchangeSite.getTransactionList().get(ExchangeSite.getTransactionList().size()-1).toString());

									}
									else {
										System.out.println("Not enough money thst currency. Order canceled");
									}
								}
								else {
									if(theSite.buyMoney(myOrder)) {
										theSite.completeOrder(myOrder);
										System.out.println("Transaction completed! ");
										//Need to print out the transaction to user here!
									}
									else {
										System.out.println("Not enough money in that currency. Order canceled");
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
				case 4:
					exit=true;

					break;

				default:
					System.out.println("Wrong choice!");
					break;
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Wrong choice! Try again");
			}
		}
		while(!exit);

		String destination = "testShutDown.txt";
		theSite.shutDownService(destination);
		System.exit(0);
	}


}




