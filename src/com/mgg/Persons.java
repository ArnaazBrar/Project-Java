package com.mgg;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * Persons class creates objects for each person
 * whether customer or the manager
 * 
 * Has additional getter and setter methods
 * 
 */

public class Persons {

	private Integer personId;
	private String personCode;
	private Character type;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> emailAddresses = new ArrayList<String>();
	private int salesCount;
	private double totalSale;

	public Persons(Integer personId, String personCode, Character type, String lastName, String firstName,
			Address address, List<String> emailAddresses) {
		super();
		this.personId = personId;
		this.personCode = personCode;
		this.type = type;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emailAddresses = emailAddresses;
	}

	public Persons(String personCode, Character type, String lastName, String firstName, Address address,
			List<String> emailAddresses) {
		super();
		this.personCode = personCode;
		this.type = type;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emailAddresses = emailAddresses;
	}

	public String getPersonCode() {
		return personCode;
	}

	public Character getType() {
		return type;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getEmailAddresses() {
		return emailAddresses;
	}

	public Integer getPersonId() {
		return personId;
	}
	
	public int getSalesCount() {
		return salesCount;
	}

	public double getTotalSale() {
		return totalSale;
	}

	@Override
	public String toString() {
		return "(" + emailAddresses + ")\n";
	}

	/*
	 *  Prints the Employee Summary
	 */
	public void printSalesPersonSummary() {

		ArrayList<Sales> sales = LoadDatatbase.getSalesDb();

		if (this.getType().equals('E')) {

			int count = 0;
			Double salesPersonTotal = 0.0;

			for (Sales sale : sales) {

				if (this.getPersonCode().equals(sale.getSalesPerson().getPersonCode())) {

					ArrayList<Items> purchasedItems = sale.getItemsList();

					double tax = 0.0;
					double tCost = 0.0;

					for (Items i : purchasedItems) {

						String type = i.getType();

						if (type.equals("PN") || type.equals("PU")) {

							Products p = (Products) i;

							tax = tax + p.getTax();
							tCost = tCost + p.getCost();

						}

						if (type.equals("PG")) {

							Products p = (Products) i;

							tax = tax + p.getTax();
							tCost = tCost + p.getCost();

						}

						if (type.equals("SV")) {

							Services sv = (Services) i;

							tax = tax + sv.getTax();
							tCost = tCost + sv.getCost();
						}

						if (type.equals("SB")) {

							Subscriptions sb = (Subscriptions) i;

							tax = tax + sb.getTax();
							tCost = tCost + sb.getCost();
						}

					}

					tax = Math.round(tax);
					tCost = Math.round(tCost);

					// Discount Calculation

					double discount = 0.0;

					switch (sale.getCustomer().getType()) {

					case 'G':
						discount = (tCost * 0.05);
						break;

					case 'P':
						discount = (tCost * 0.10);
						break;

					case 'C':
						discount = (tCost * 0.00);
						break;

					case 'E':
						discount = (tCost * 0.15);
						break;

					default:
						discount = 0.0;
						break;

					}

					double grandTotal = ((tCost + tax) - discount);
					salesPersonTotal = salesPersonTotal + grandTotal;
					count++;


				}

			}
			
			this.salesCount = count;
			this.totalSale = salesPersonTotal;
			
			// Print Salesperson information

			System.out.println(this.getLastName() + "," + this.getFirstName() + "\t\t\t\t" + count + "\t\t$\t" + salesPersonTotal);

		}
	}
}