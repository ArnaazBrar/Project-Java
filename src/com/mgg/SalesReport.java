package com.mgg;

import java.util.ArrayList;
/*
 * This is the main class to 
 * print reports
 */
public class SalesReport {

	public static void main(String args[]) {

		ArrayList<Sales> sales = LoadDatatbase.getSalesDb();
		ArrayList<Stores> stores = LoadDatatbase.getStoresDb();
		ArrayList<Persons> salesPersons = LoadDatatbase.getPersonDb();


		// Prints individual Sales
				for (Sales s : sales) {
					s.printSale();
				}

				
				// Prints Store Summary Report
				System.out.println("+------------------------------------------------------------------------------------------+\n"
								 + "|  Store Sales Summary Report                                                              |\n"
								 + "+------------------------------------------------------------------------------------------+");

				System.out.println("Store\t\tManager\t\t\t\t  # Sales\t\t\tGrand Total");

				for (Stores st : stores) {
					st.printStoreSales();
				}

				Double storesTotal = 0.0;
				int count = 0;
				for (Stores st : stores) {
					storesTotal = storesTotal + st.getsTotal();
					count = count + st.getStoreSaleCount();
				}

				System.out.println("+----------------------------------------------------------------+\n" + "\t\t\t\t\t\t\t"
						+ count + "\t\t$\t" + storesTotal);

				
				// Prints SalesPerson Summary Report
				System.out.println("+----------------------------------------------------------------+\n"
						+ "|  Salesperson Summary Report                                    |\n"
						+ "+----------------------------------------------------------------+");

				System.out.println("Salesperson\t\t\t\t# Sales\t\tGrand Total");

				for (Persons sp : salesPersons) {
					sp.printSalesPersonSummary();
				}

				Double salesPersonTotal = 0.0;
				int numOfSales = 0;
				for (Persons p : salesPersons) {
					salesPersonTotal = salesPersonTotal + p.getTotalSale();
					numOfSales = numOfSales + p.getSalesCount();
				}

				System.out.println("+----------------------------------------------------------------+\n" + "\t\t\t\t\t"
						+ numOfSales + "\t\t$\t" + salesPersonTotal);

			}


}
