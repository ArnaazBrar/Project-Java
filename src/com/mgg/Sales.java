package com.mgg;

import java.util.ArrayList;

public class Sales {

	private Integer salesId;
	private String salesCode;
	private Stores store;
	private Persons customer;
	private Persons salesPerson;
	private ArrayList<Items> itemsList;
	private double saleTotal;


	public Sales(Integer salesId, String salesCode, Stores store, Persons customer, Persons salesPerson,
			ArrayList<Items> itemsList) {
		super();
		this.salesId = salesId;
		this.salesCode = salesCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.itemsList = itemsList;
	}

	public Sales(String salesCode, Stores store, Persons customer, Persons salesPerson) {
		super();
		this.salesCode = salesCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.itemsList = new ArrayList<Items>();
	}

	public String getSalesCode() {
		return salesCode;
	}

	public Stores getStore() {
		return store;
	}

	public Persons getSalesPerson() {
		return salesPerson;
	}

	public Persons getCustomer() {
		return customer;
	}

	public ArrayList<Items> getItemsList() {
		return itemsList;
	}

	public void addItem(Items i) {
		this.itemsList.add(i);
	}

	public Integer getSalesId() {
		return salesId;
	}
	
	
	public double getSumOfCosts() {
		ArrayList<Items> saleItems = this.itemsList;
		double sumTotal = 0.0;
		for (Items i : saleItems) {
			sumTotal = sumTotal + i.getCost();
		}
		return sumTotal;
	}

	

	public Double getSaleTotal() {

		
		ArrayList<Items> saleItems = this.itemsList;
		double tax = 0.0;
		double tCost = 0.0;

		for (Items i : saleItems) {

			if (i.getType().equals("PN")) {
				Products p = (Products) i;
				

				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("PU")) {
				Products p = (Products) i;
				
				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("PG")) {
				Products p = (Products) i;
				
				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("SV")) {
				Services sv = (Services) i;

				tax = tax + sv.getTax();
				tCost = tCost + sv.getCost();

			} else if (i.getType().equals("SB")) {
				Subscriptions sb = (Subscriptions) i;

				tax = tax + sb.getTax();
				tCost = tCost + sb.getCost();

			}

		}
		
		
		double discount = 0.0;

		switch (this.customer.getType()) {

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

		this.saleTotal = ((tCost + tax) - discount);
		
		return this.saleTotal;

	}
	
	public void printSale() {
		System.out.println("Sale :    #" + this.salesCode);
		System.out.println("Store :    #" + this.store.getStoreCode());

		System.out.println("Customer: \n" + this.customer.getLastName() + ", " + this.customer.getFirstName() + "  ("
				+ this.customer.toString() + ")\n" + this.customer.getAddress().toString());

		System.out.println("Sales Person : \n" + this.salesPerson.getLastName() + ", " + this.salesPerson.getFirstName()
				+ "  (" + this.salesPerson.toString() + ")\n" + this.salesPerson.getAddress().toString());

		System.out.println(
				"Item\t\t\t\t\t\t\t\t\tTotal \n -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\t\t\t\t-=-=-=-=-=-=-=-=-=");

		ArrayList<Items> saleItems = this.itemsList;
		double tax = 0.0;
		double tCost = 0.0;

		for (Items i : saleItems) {

			if (i.getType().equals("PN")) {
				Products p = (Products) i;
				System.out.println(p.getName() + "\n" + " (New Item #" + p.getCode() + " @$" + p.getBasePrice()
						+ "/ea)\t\t\t\t\t$\t" + p.getCost());

				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("PU")) {
				Products p = (Products) i;
				System.out.println(p.getName() + "\n" + " (Used Item #" + p.getCode() + " @$" + p.getBasePrice()
						+ "/ea)\t\t\t\t        $\t" + p.getCost());

				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("PG")) {
				Products p = (Products) i;
				System.out
						.println(p.getName() + "\n" + " (Gift Card #" + p.getCode() + ")\t\t\t\t\t\t$\t" + p.getCost());

				tax = tax + p.getTax();
				tCost = tCost + p.getCost();

			} else if (i.getType().equals("SV")) {
				Services sv = (Services) i;
				System.out.println(sv.getName() + "\n" + " (Service #" + sv.getCode() + " by "
						+ this.salesPerson.getLastName() + "," + this.salesPerson.getFirstName() + " "
						+ sv.getNumberOfHours() + "hrs @$" + sv.getHourlyRate() + "/hr)\t\t$\t" + sv.getCost());

				tax = tax + sv.getTax();
				tCost = tCost + sv.getCost();

			} else if (i.getType().equals("SB")) {
				Subscriptions sb = (Subscriptions) i;
				System.out.println(sb.getName() + "\n" + " (Subscriptions #" + sb.getCode() + " " + sb.getNumOfDays()
						+ " days @$" + sb.getAnnualFee() + "/yr)\t\t\t$\t" + sb.getCost());

				tax = tax + sb.getTax();
				tCost = tCost + sb.getCost();

			}

		}
		
		
		
		System.out.println("\t\t\t\t\t\t\t\t-=-=-=-=-=-=-=-=-=\n" + "\t\t\t\t\t\t\t\t Subtotal $\t" + tCost
				+ "\n\t\t\t\t\t\t\t\t Tax $\t" + tax);

		double discount = 0.0;

		switch (this.customer.getType()) {

		case 'G':
			discount = (tCost * 0.05);
			System.out.println("\t\t\t\t\t\t\t\t Discount (5%) $\t" + discount);
			break;

		case 'P':
			discount = (tCost * 0.10);
			System.out.println("\t\t\t\t\t\t\t\t Discount (10%) $\t" + discount);
			break;

		case 'C':
			discount = (tCost * 0.00);
			break;

		case 'E':
			discount = (tCost * 0.15);
			System.out.println("\t\t\t\t\t\t\t\t Discount (15%) $\t" + discount);
			break;

		default:
			discount = 0.0;
			break;

		}

		this.saleTotal = ((tCost + tax) - discount);
		
		
		System.out.println("\t\t\t\t\t\t\t\t Grand Total $\t" + this.saleTotal);

	}
	
	public void printSortedList() {

		System.out.printf("%-25s" + "%-25s" + "%-25s" + "%-25s" + "$	%-25.2f", this.salesCode, this.store.getStoreCode(), this.customer.getLastName() + "," + this.customer.getFirstName(),
		this.salesPerson.getLastName() + "," + this.salesPerson.getFirstName(), this.getSaleTotal().floatValue());
		System.out.println("");
	}
	

	@Override
	public String toString() {
		return "Sales [salesCode=" + salesCode + ", store=" + store + ", customer=" + customer + ", salesPerson="
				+ salesPerson + ", itemsList=" + itemsList + "]";
	}
	
	

}