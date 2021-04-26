package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Database interface class
 */
public class SalesData {

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		/* Deletes all records from the table "Sale" and
		 * other tables associated with it
		 */
		Helpers.DeleteAllRecords("ItemSale");
		Helpers.DeleteAllRecords("Sale");
	   
	}

	/**
	 * Removes the single sales record associated with tehe given
	 * <code>saleCode</code>
	 * 
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		int saleId = Helpers.getSalesIdFromCode(saleCode);
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String query = "delete from ItemSale where salesId = ?;";
		String query1 = "delete from Sale where salesId = ?;";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.executeUpdate();
			
			ps1 = conn.prepareStatement(query1);
			ps1.setInt(1, saleId);
			ps1.executeUpdate();
			
			ps1.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		Helpers.DeleteAllRecords("ItemSale");
		Helpers.DeleteAllRecords("Item");
		Helpers.DeleteAllRecords("Sale");
		Helpers.DeleteAllRecords("Store");
		Helpers.DeleteAllRecords("EmailAddress");
		Helpers.DeleteAllRecords("Person");
		Helpers.DeleteAllRecords("Address");

	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 * 
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String type, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Address a = new Address(street,city,state,zip,country);
		String query = "insert into Person(personCode,personType,lastName,firstName,personAddressId) values (?,?,?,?,?);";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, String.valueOf(type)); // Check no error
			ps.setString(3, lastName);
			ps.setString(4, firstName);
			Address address = Helpers.getOrInsertAddress(a);
			ps.setInt(5, address.getAddressId());
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		if (Helpers.isDuplicateEmailAddress(email) == false) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer pId = Helpers.getPersonIdByCode(personCode);
		String query = "insert into EmailAddress(emailAddress,personId) values (?,?);";
		
		
		try {
			if (pId != null) {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
				ps.setInt(2, pId);
				ps.executeUpdate();

				ps.close();
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		} else {
			System.err.println("This email already exists in the Database..");
		}
		
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 * 
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer mId = Helpers.getPersonIdByCode(managerCode);
		Address a = new Address(street,city,state,zip,country);
		String query = "insert into Store(storeCode,storeManagerId,storeAddressId) values (?,?,?);";
		
		try {
			if (mId != null) {
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			ps.setInt(2, mId); 
			Address address = Helpers.getOrInsertAddress(a);
			ps.setInt(3, address.getAddressId());
			ps.executeUpdate();
			} else {
				System.err.println("No manager found for this store. Please create manager object before inserting the Store.");
			}
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 * 
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		String query1 = "insert into Item(itemCode,itemType,ItemName,basePrice) values (?,?,?,?);";
		String query2 = "insert into Item(itemCode,itemType,ItemName,hourlyRate) values (?,?,?,?);";
		String query3 = "insert into Item(itemCode,itemType,ItemName,annualFee) values (?,?,?,?);";
		
		if ((type.equals("PN")) || (type.equals("PU"))) {
			try {
				ps = conn.prepareStatement(query1);
				ps.setString(1, itemCode);
				ps.setString(2, type); 
				ps.setString(3, name);
				ps.setDouble(4, basePrice);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	
		}
		
		if (type.equals("PG")) {
			try {
				ps = conn.prepareStatement(query1);
				ps.setString(1, itemCode);
				ps.setString(2, type); 
				ps.setString(3, name);
				ps.setNull(4, Types.DOUBLE);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		if (type.equals("SV")) {
			try {
				ps = conn.prepareStatement(query2);
				ps.setString(1, itemCode);
				ps.setString(2, type); 
				ps.setString(3, name);
				ps.setDouble(4, basePrice);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		if (type.equals("SB")) {
			try {
				ps = conn.prepareStatement(query3);
				ps.setString(1, itemCode);
				ps.setString(2, type); 
				ps.setString(3, name);
				ps.setDouble(4, basePrice);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Adds a sales record to the database with the given data.
	 * 
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer sId = Helpers.getStoreIdByCode(storeCode);
		Integer cId = Helpers.getPersonIdByCode(customerCode);
		Integer spId = Helpers.getPersonIdByCode(salesPersonCode);
		String query = "insert into Sale(salesCode,storeId,customerId,salesPersonId) values (?,?,?,?);";
		
		try {
			if (sId == null) {
				//throw new RuntimeException(e);
				System.err.println("No store found for this sale. Please create store object before inserting the sale.");
			} else if (cId == null){
				
				System.err.println("No customer found for this sale. Please create customer object before inserting the sale");
			} else if (spId == null){
				
				System.err.println("No salesPerson found for this sale. Please create salesPerson object before inserting the sale");
			} else {
			
			
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			ps.setInt(2, sId); 
			ps.setInt(3, cId);
			ps.setInt(4, spId);
			ps.executeUpdate();
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer saleId = Helpers.getSaleIdByCode(saleCode);
		Integer itemId = Helpers.getItemIdByCode(itemCode);
		String query = "insert into ItemSale(itemId,salesId,quantity) values (?,?,?);";
		
		try {
			if (saleId == null) {
				//throw new RuntimeException(e);
				System.err.println("No sale found with this saleCode. Please create sale object before inserting the item.");
			} else if (itemId == null){
				
				System.err.println("No Product available with this itemCode. Please create Product object before inserting it into a sale record.");
			} else {
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, itemId); 
			ps.setInt(2, saleId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer saleId = Helpers.getSaleIdByCode(saleCode);
		Integer itemId = Helpers.getItemIdByCode(itemCode);
		String query = "insert into ItemSale(itemId,salesId,giftCardQuantity) values (?,?,?);";
		
		try {
			if (saleId == null) {
				//throw new RuntimeException(e);
				System.err.println("No sale found with this saleCode. Please create sale object before inserting the item.");
			} else if (itemId == null){
				
				System.err.println("No giftCard available with this itemCode. Please create giftCard object before inserting it into a sale record.");
			} else {
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, itemId); 
			ps.setInt(2, saleId);
			ps.setDouble(3, amount);
			ps.executeUpdate();
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * will be performed by the given employee for the specified number of
	 * hours.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer saleId = Helpers.getSaleIdByCode(saleCode);
		Integer itemId = Helpers.getItemIdByCode(itemCode);
		String query = "insert into ItemSale(itemId,salesId,employeeCode,numOfHours) values (?,?,?,?);";
		
		try {
			if (saleId == null) {
				//throw new RuntimeException(e);
				System.err.println("No sale found with this saleCode. Please create sale object before inserting the item.");
			} else if (itemId == null){
				
				System.err.println("No service available with this itemCode. Please create Service object before inserting it into a sale record.");
			} else {
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, itemId); 
			ps.setInt(2, saleId);
			ps.setString(3, employeeCode);
			ps.setDouble(4, billedHours);
			ps.executeUpdate();
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * is effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		Integer saleId = Helpers.getSaleIdByCode(saleCode);
		Integer itemId = Helpers.getItemIdByCode(itemCode);
		String query = "insert into ItemSale(itemId,salesId,beginDate,endDate) values (?,?,?,?);";
		
		try {
			if (saleId == null) {
				//throw new RuntimeException(e);
				System.err.println("No sale found with this saleCode. Please create sale object before inserting the item.");
			} else if (itemId == null){
				
				System.err.println("No Subscription available with this itemCode. Please create Subscription object before inserting it into a sale record.");
			} else {
			
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, itemId); 
			ps.setInt(2, saleId);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.executeUpdate();
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
