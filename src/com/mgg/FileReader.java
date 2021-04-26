package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 
 * This class is designed in order to read
 * the input from the flat comma delimited 
 * files from the o;d system. 
 * 
 * The methods create new instances of objects 
 * and store the data objects in Array based 
 * lists.
 * 
 */

public class FileReader {

	public FileReader() {
		super();
	}

	public static ArrayList<Persons> getPersons() {

		Scanner s = null;

		ArrayList<Persons> personsList = new ArrayList<Persons>();

		File personsFile = new File("data/Persons.csv");

		try {
			s = new Scanner(personsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String nextLn = s.nextLine();

		while (s.hasNextLine()) {

			ArrayList<String> emailAddresses = new ArrayList<String>();
			Integer i = 9;

			nextLn = s.nextLine();

			String tokens[] = nextLn.split(",");

			String personCode = tokens[0];
			Character type = tokens[1].charAt(0);
			String lastName = tokens[2];
			String firstName = tokens[3];

			String street = tokens[4];
			String city = tokens[5];
			String state = tokens[6];
			String zip = tokens[7];
			String country = tokens[8];

			Address address = new Address(street, city, state, zip, country);

			if (tokens.length > i) {

				while (i != tokens.length) {
					emailAddresses.add(tokens[i]);
					i++;
				}

			}

			Persons person = new Persons(personCode, type, lastName, firstName, address, emailAddresses);

			personsList.add(person);

		}

		s.close();

		return personsList;
	}

	public static ArrayList<Stores> getStores() { // Check

		Scanner s = null;

		ArrayList<Stores> storesList = new ArrayList<Stores>();
		ArrayList<Persons> persons = getPersons();

		File storesFile = new File("data/Stores.csv");

		try {
			s = new Scanner(storesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String nextLn = s.nextLine();

		while (s.hasNextLine()) {

			nextLn = s.nextLine();

			String tokens[] = nextLn.split(",");

			String storeCode = tokens[0];
			String managerCode = tokens[1];

			String street = tokens[2];
			String city = tokens[3];
			String state = tokens[4];
			String zip = tokens[5];
			String country = tokens[6];

			Address address = new Address(street, city, state, zip, country);

			Persons manager = getPersonFromCode(persons, managerCode);

			Stores store = new Stores(storeCode, manager, address);

			storesList.add(store);

		}

		s.close();

		return storesList;

	}

	public static ArrayList<Items> getItems() {

		Scanner s = null;

		ArrayList<Items> itemsList = new ArrayList<Items>();

		File itemsFile = new File("data/Items.csv");

		try {
			s = new Scanner(itemsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String nextLn = s.nextLine();

		while (s.hasNextLine()) {

			nextLn = s.nextLine();

			String tokens[] = nextLn.split(",");

			String itemCode = tokens[0];
			String type = tokens[1];
			String name = tokens[2];

			if (type.equals("PN") || type.equals("PU")) {

				Double basePrice = Double.parseDouble(tokens[3]);
				Items product = new Products(itemCode, type, name, basePrice);

				itemsList.add(product);

			} else if (type.equals("PG")) {

				Items product = new Products(itemCode, type, name);

				itemsList.add(product);

			} else if (type.equals("SV")) {

				Double hourlyRate = Double.parseDouble(tokens[3]);

				Items service = new Services(itemCode, type, name, hourlyRate);

				itemsList.add(service);

			} else if (type.equals("SB")) {

				Double annualFee = Double.parseDouble(tokens[3]);

				Items subscription = new Subscriptions(itemCode, type, name, annualFee);

				itemsList.add(subscription);

			}

		}

		s.close();

		return itemsList;

	}

	public static ArrayList<Sales> getSales() {

		Scanner s = null;

		ArrayList<Sales> salesList = new ArrayList<Sales>();
		ArrayList<Persons> persons = getPersons();
		ArrayList<Stores> storeList = getStores();
		ArrayList<Items> items = getItems();

		File salesFile = new File("data/Sales.csv");

		try {
			s = new Scanner(salesFile);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			e.printStackTrace();
		}

		String nextLn = s.nextLine();

		while (s.hasNextLine()) {

			nextLn = s.nextLine();

			Sales sale = null;
			String tokens[] = nextLn.split(",");
			String salesCode = tokens[0];
			Stores store = getStoreFromCode(storeList, tokens[1]);
			Persons customer = getPersonFromCode(persons, tokens[2]);
			Persons salesPerson = getPersonFromCode(persons, tokens[3]);
			Persons c = new Persons(customer.getPersonCode(), customer.getType(), customer.getLastName(),
					customer.getFirstName(), customer.getAddress(), customer.getEmailAddresses());
			sale = new Sales(salesCode, store, c, salesPerson);


			salesList.add(sale);

			int i = 4;

			while (i < tokens.length) {

				String code = tokens[i];
				String type = "";

				for (Items item : items) {

					if (item.getCode().equals(code)) {
						type = item.getType();

						switch (type) {

						case "PN":
							Integer quantity = Integer.parseInt(tokens[i + 1]);
							Double basePrice = getBasePrice(items, code);

							Items product = new Products(item.getCode(), item.getType(), item.getName(), basePrice,
									quantity);

							sale.addItem(product);
							i = i + 2;

							break;

						case "PU":
							Integer quantity2 = Integer.parseInt(tokens[i + 1]);
							Double basePrice2 = getBasePrice(items, code);

							Items product1 = new Products(item.getCode(), item.getType(), item.getName(), basePrice2,
									quantity2);

							sale.addItem(product1);
							i = i + 2;

							break;

						case "PG":
							Double quantityNum = Double.parseDouble(tokens[i + 1]);
							Double basePrice3 = null;

							Items product2 = new Products(item.getCode(), item.getType(), item.getName(), basePrice3,
									quantityNum);

							sale.addItem(product2);
							i = i + 2;

							break;

						case "SV":

							String employeeCode = tokens[i + 1];
							Double numHours = Double.parseDouble(tokens[i + 2]);
							Double hourlyRate = getHourlyRate(items, code);

							Items service = new Services(item.getCode(), item.getType(), item.getName(), hourlyRate,
									employeeCode, numHours);

							sale.addItem(service);
							i = i + 3;

							break;

						case "SB":

							Double annualFee = getAnnualFee(items, code);
							LocalDate beginDate;
							LocalDate endDate;

							beginDate = LocalDate.parse((tokens[i + 1]));
							endDate = LocalDate.parse((tokens[i + 2]));

							Items subscription = new Subscriptions(item.getCode(), item.getType(), item.getName(),
									annualFee, beginDate, endDate);

							sale.addItem(subscription);
							i = i + 3;
							break;

						default:
							System.out.println("No Input");
							break;
						}

					}

				}

			}

		}

		s.close();

		return salesList;

	}

	/*
	 * The following method finds the manager of a particular and returns the person
	 * concerned to the required method.
	 */

	private static Persons getPersonFromCode(List<Persons> persons, String code) {
		for (Persons p : persons) {
			if (p.getPersonCode().equals(code)) {
				return p;
			}
		}
		return null;
	}

	private static Stores getStoreFromCode(List<Stores> stores, String code) {
		for (Stores s : stores) {
			if (s.getStoreCode().equals(code)) {
				return s;
			}
		}
		return null;
	}

	private static Double getBasePrice(ArrayList<Items> items, String code) {
		for (Items item : items) {
			if (item.getCode().equals(code)) {
				Products tempProduct = (Products) item;
				return (tempProduct.getBasePrice());
			}
		}

		return 0.0;
	}

	private static Double getHourlyRate(ArrayList<Items> items, String code) {
		for (Items item : items) {
			if (item.getCode().equals(code)) {
				Services tempService = (Services) item;
				return (tempService.getHourlyRate());
			}
		}

		return 0.0;
	}

	private static Double getAnnualFee(ArrayList<Items> items, String code) {
		for (Items item : items) {
			if (item.getCode().equals(code)) {
				Subscriptions tempSubscription = (Subscriptions) item;
				return (tempSubscription.getAnnualFee());
			}
		}

		return 0.0;
	}

}