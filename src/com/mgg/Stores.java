package com.mgg;

import java.util.ArrayList;

/*
 * Stores class creates objects for
 * all the stores at different locations
 * 
 */

public class Stores {

	private Integer storeId;
	private String storeCode;
	private Persons manager;
	private Address address;
	private double sTotal;
	private int storeSaleCount;


	public Stores(Integer storeId, String storeCode, Persons manager, Address address) {
		super();
		this.storeId = storeId;
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
	}

	public Stores(String storeCode, Persons manager, Address address) {
		super();
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Persons getManager() {
		return manager;
	}

	public Address getAddress() {
		return address;
	}
	
	public Integer getStoreId() {
		return storeId;
	}
	
	public double getsTotal() {
		return sTotal;
	}

	public int getStoreSaleCount() {
		return storeSaleCount;
	}

	/*
	 * Prints the Store Summary Report
	 */
	public void printStoreSales() {
		ArrayList<Sales> sales = LoadDatatbase.getSalesDb();
		
		int count = 0;
		Double storeTotal = 0.0;

		for (Sales sale : sales) {
			
			if (this.storeCode.equals(sale.getStore().getStoreCode())) {
			
            ArrayList<Items> purchasedItems = sale.getItemsList();

			double tax = 0.0;
			double tCost = 0.0;
		
            for (Items i : purchasedItems) {
				
				String type = i.getType();
				
				if (type.equals("PN") || type.equals("PU")) {
					
					Products p = (Products)i;
					
					tax = tax + p.getTax();
					tCost = tCost + p.getCost();
					
				}
				
				
               if (type.equals("PG")) {
					
            	   Products p = (Products)i;
					
					tax = tax + p.getTax();
					tCost = tCost + p.getCost();
				
				}
               
               if(type.equals("SV")) {
					
					Services sv = (Services)i;
					
					tax = tax + sv.getTax();
					tCost = tCost + sv.getCost();
				}
              
               if(type.equals("SB")) {
					
					Subscriptions sb = (Subscriptions)i;
					
					tax = tax + sb.getTax();
					tCost = tCost + sb.getCost();
				}
               
               
               
			}
            
               tax = Math.round(tax);
    		   tCost = Math.round(tCost);
					
              // Discount Calculation
    		   
    		  double discount = 0.0;
              
              switch (sale.getCustomer().getType())  {
              
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
           	   
              default :
           	   discount = 0.0;
           	   break;
              
              }
              
              double grandTotal = ((tCost + tax) - discount);
              
              storeTotal = storeTotal + grandTotal;
              count++;
			}

		}
		
		this.sTotal = storeTotal;
		this.storeSaleCount = count;
	
		// Print store information
					System.out.println(this.storeCode + "\t\t" + this.manager.getLastName() + " " + 
		            this.manager.getFirstName() + "\t\t\t\t" + count + "\t\t$\t" + storeTotal);
		
    }


	@Override
	public String toString() {
		return "Stores [storeCode=" + storeCode + ", manager=" + manager + ", address=" + address + "]";
	}
	
	
	
	
}