package com.mgg;

import java.util.ArrayList;
import java.util.Comparator;
/*
 * This is the main class to 
 * print reports
 */
public class SalesReport {

	public static final Comparator<Sales> comparatorCustomer = new Comparator<Sales>(){

		@Override
		public int compare(Sales s1, Sales s2) {
			return s1.getCustomer().getLastName().compareTo(s2.getCustomer().getLastName());
		}
			
	};
	
	public static final Comparator<Sales> comparatorTotal = new Comparator<Sales>(){

		@Override
		public int compare(Sales s1, Sales s2) {
			return s2.getSaleTotal().compareTo(s1.getSaleTotal());
		}
			
	};
	
	public static final Comparator<Sales> comparatorGroup = new Comparator<Sales>(){

		@Override
		public int compare(Sales s1, Sales s2) {
			if(s1.getStore().equals(s2.getStore())) {
				return s1.getSalesPerson().getLastName().compareTo(s2.getSalesPerson().getLastName());
			}
			
			return s1.getStore().getStoreCode().compareTo(s2.getStore().getStoreCode());
		}
			
	};
	
	public static void main(String args[]) {

		ArrayList<Sales> sales = LoadDatatbase.getSalesDb();
		
		LinkedList salesL = new LinkedList(comparatorTotal);
		for(Sales s: sales) {
			salesL.add(s);
		}
		
		Node currentNode = salesL.getstartSale();
		while(currentNode.getNext() != null) {
			System.out.println(currentNode.getSales().getSaleTotal());
			currentNode = currentNode.getNext();
		}
		System.out.println(currentNode.getSales().getSaleTotal());

		}


}
