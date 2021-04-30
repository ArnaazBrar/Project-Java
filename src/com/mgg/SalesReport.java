package com.mgg;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * This is the main class to 
 * print reports
 */
public class SalesReport {

	public static final Comparator<Sales> comparatorCustomer = new Comparator<Sales>() {

		@Override
		public int compare(Sales s1, Sales s2) {
			
			if(s1.getCustomer().getLastName().equals(s2.getCustomer().getLastName())) {
				return s1.getSalesCode().compareTo(s2.getSalesCode());
			}
			
			return s1.getCustomer().getLastName().compareTo(s2.getCustomer().getLastName());
		}

	};

	public static final Comparator<Sales> comparatorTotal = new Comparator<Sales>() {

		@Override
		public int compare(Sales s1, Sales s2) {
			return s2.getSaleTotal().compareTo(s1.getSaleTotal());
		}

	};

	public static final Comparator<Sales> comparatorGroup = new Comparator<Sales>() {

		@Override
		public int compare(Sales s1, Sales s2) {
			if (s1.getStore().equals(s2.getStore())) {
				return s1.getSalesPerson().getLastName().compareTo(s2.getSalesPerson().getLastName());
			}

			return s1.getStore().getStoreCode().compareTo(s2.getStore().getStoreCode());
		}

	};

	public static void main(String args[]) {

		ArrayList<Sales> sales = LoadDatatbase.getSalesDb();

		LinkedList<Sales> salesList1 = new LinkedList<Sales>(comparatorCustomer);
		for (Sales s : sales) {
			salesList1.add(s);
		}

		/*
		 * Prints Summary Report sorted by sale Customer's lastName,firstName
		 */
		System.out.println("+--------------------------------------------------------------------------------------------------------------+\n"
						 + "|  Sales by Customer                                                                                           |\n"
					   	 + "+--------------------------------------------------------------------------------------------------------------+");

		System.out.printf("%-25s" + "%-25s" + "%-25s" + "%-25s" + "	%-25s","Sale", "Store", "Customer", "Salesperson", "Total");
		System.out.println("");
		
		for(Sales sale: salesList1) {
			sale.printSortedList();
		}
		
		System.out.println("\n\n#################################################################################################################\n\n");

		LinkedList<Sales> salesList2 = new LinkedList<Sales>(comparatorTotal);
		for (Sales s : sales) {
			salesList2.add(s);
		}

		/*
		 * Prints Summary Report sorted by sale total
		 */
		System.out.println(
				"+--------------------------------------------------------------------------------------------------------------+\n"
						+ "|  Sales by Total                                                                                              |\n"
						+ "+--------------------------------------------------------------------------------------------------------------+");

		System.out.printf("%-25s" + "%-25s" + "%-25s" + "%-25s" + "	%-25s","Sale", "Store", "Customer", "Salesperson", "Total");
		System.out.println("");

		for(Sales sale: salesList2) {
			sale.printSortedList();
		}

		System.out.println("\n\n#################################################################################################################\n\n");
		
//		LinkedList<Sales> salesList3 = new LinkedList<Sales>(comparatorGroup);
//		for (Sales s : sales) {
//			salesList3.add(s);
//		}
//
//		/*
//		 * Prints Summary Report sorted by storeCode and total
//		 */
//		System.out.println(
//				"+--------------------------------------------------------------------------------------------------------------+\n"
//			  + "|  Sales by Store                                                                                              |\n"
//			  + "+--------------------------------------------------------------------------------------------------------------+");
//
//		System.out.printf("%-25s" + "%-25s" + "%-25s" + "%-25s" + "	%-25s","Sale", "Store", "Customer", "Salesperson", "Total");
//		System.out.println("");
//
//		Node<Sales> currentNode3 = salesList3.getstartSale();
//		while (currentNode3.getNext() != null) {
//			currentNode3.getSales().printSortedList();
//			currentNode3 = currentNode3.getNext();
//		}
//		currentNode3.getSales().printSortedList();

	}

}
