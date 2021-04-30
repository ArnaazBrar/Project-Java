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

		LinkedList salesList1 = new LinkedList(comparatorCustomer);
		for (Sales s : sales) {
			salesList1.add(s);
		}

		/*
		 * Prints Summary Report sorted by sale Customer's lastName,firstName
		 */
		System.out.println("+------------------------------------------------------------------------------------------+\n"
						 + "|  Sales by Customer                                                                       |\n"
					   	 + "+------------------------------------------------------------------------------------------+");

		System.out.println("Sale\t\tStore\t\tCustomer\t\tSalesperson\t\t\tTotal");

		Node currentNode1 = salesList1.getstartSale();
		while (currentNode1.getNext() != null) {
			currentNode1.getSales().printSortedList();
			currentNode1 = currentNode1.getNext();
		}
		currentNode1.getSales().printSortedList();
		
		System.out.println("\n\n#######################################################################################################\n\n");

		LinkedList salesList2 = new LinkedList(comparatorTotal);
		for (Sales s : sales) {
			salesList2.add(s);
		}

		/*
		 * Prints Summary Report sorted by sale total
		 */
		System.out.println(
				"+------------------------------------------------------------------------------------------+\n"
						+ "|  Sales by Total                                                                          |\n"
						+ "+------------------------------------------------------------------------------------------+");

		System.out.println("Sale\t\tStore\t\tCustomer\t\tSalesperson\t\t\tTotal");

		Node currentNode2 = salesList2.getstartSale();
		while (currentNode2.getNext() != null) {
			currentNode2.getSales().printSortedList();
			currentNode2 = currentNode2.getNext();
		}
		currentNode2.getSales().printSortedList();

		System.out.println("\n\n#######################################################################################################\n\n");
		
//		LinkedList salesList3 = new LinkedList(comparatorGroup);
//		for (Sales s : sales) {
//			salesList3.add(s);
//		}
//
////		/*
//		 * Prints Summary Report sorted by storeCode and total
//		 */
//		System.out.println(
//				"+------------------------------------------------------------------------------------------+\n"
//			  + "|  Sales by Store                                                                          |\n"
//			  + "+------------------------------------------------------------------------------------------+");
//
//		System.out.println("Sale\t\tStore\t\tCustomer\t\tSalesperson\t\t\tTotal");
//
//		Node currentNode3 = salesList3.getstartSale();
//		while (currentNode3.getNext() != null) {
//			currentNode3.getSales().printSortedList();
//			currentNode3 = currentNode3.getNext();
//		}
//		currentNode3.getSales().printSortedList();

	}

}
