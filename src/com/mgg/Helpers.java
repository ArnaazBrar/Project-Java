package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 
 * This class contains helper methods
 * to implement functionality in SalesData.java
 * 
 */
public class Helpers {
	
	public Helpers() {
		super();
	}
	
	/*
	 *  Deletes all records in a given table
	 */
	public static void DeleteAllRecords(String table) {
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String query = "SET FOREIGN_KEY_CHECKS = 0;";
		String query1 = "delete from " + table + ";";
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps1 = conn.prepareStatement(query1);
			ps1.executeUpdate();
			
			ps1.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return;
	} 
	
	/*
	 *  This method returns saleId corresponding to a given saleCode
	 */
	public static int getSalesIdFromCode(String saleCode) {
		int id = 0;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select salesId from Sale where salesCode = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			
			id = rs.getInt("salesId");
			
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SaleId not found for the given saleCode");
			throw new RuntimeException(e);
		}
		
		return id;
		
	}
	
	/*
	 *  This method gets the Address from database using Area Specifics (street and ZIP)
	 */
	public static Address getAddressByAreaSpecifics(Address ad) {
		Address address = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select addressId from Address where street = ? and zip = ?;"; // doubtful

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ad.getStreet());
			ps.setString(2, ad.getZIP());
			rs = ps.executeQuery();
			if (rs.next()) {
				Integer addressId = rs.getInt("addressId");
				address = new Address(addressId,ad.getStreet(),ad.getCity(),ad.getState(),ad.getZIP(),ad.getCountry());
			} else {
				address = null;
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return address;

	}
	
	/* 
	 * Gets the Address if already in database else 
	 * inserts the record into the database
	 */
	public static Address getOrInsertAddress(Address a) {
		
		//If Address already exists in the Database, get it
		Address existingAddress = getAddressByAreaSpecifics(a);
		
		//If it does not exist, insert the record
		Address result = null;
		if (existingAddress == null)  {
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			String query = "insert into Address (street,city,state,zip,country) values (?,?,?,?,?);";
			
			try {
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, a.getStreet());
				ps.setString(2, a.getCity());
				ps.setString(3, a.getState());
				ps.setString(4, a.getZIP());
				ps.setString(5, a.getCountry());
				ps.executeUpdate();
				
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				int addressId = keys.getInt(1);
				result = new Address(addressId,a.getStreet(),a.getCity(),a.getState(),a.getZIP(),a.getCountry());
				
				keys.close();  // Check if required
				ps.close();
				conn.close();
		
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return result;
		} else {
			return existingAddress;
		}
		
	}
	

	/*
	* The method returns the personId of person with specified personCode
	*/
	public static Integer getPersonIdByCode (String personCode) {
		Integer pId = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select personId from Person where personCode = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				pId = rs.getInt("personId");
			} else {
				throw new IllegalStateException("No PersonId Found");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return pId;
		
	}
	
	// The method returns the storeId of store with specified storeCode
	public static Integer getStoreIdByCode (String storeCode) {
		Integer sId = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select storeId from Store where storeCode = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				sId = rs.getInt("storeId");
			} else {
				throw new IllegalStateException("No StoreId Found");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return sId;
		
	}
	
	// The method returns the itemId of item with specified itemCode
		public static Integer getItemIdByCode (String itemCode) {
			Integer itemId = null;
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String query = "select itemId from Item where itemCode = ?;";

			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, itemCode);
				rs = ps.executeQuery();
				if (rs.next()) {
					itemId = rs.getInt("itemId");
				} else {
					throw new IllegalStateException("No itemId Found");
				}
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			return itemId;
			
		}
		
		/*
		 *  The method returns the saleId of sale with specified saleCode
		 */
		public static Integer getSaleIdByCode (String saleCode) {
			Integer saleId = null;
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String query = "select salesId from Sale where salesCode = ?;";

			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, saleCode);
				rs = ps.executeQuery();
				if (rs.next()) {
					saleId = rs.getInt("salesId");
				} else {
					throw new IllegalStateException("No SaleId Found");
				}
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			return saleId;
			
		}
		
		/*
		 * The method checks for duplicate entries in Address
		 */
		public static boolean isDuplicateAddress(Address a) {
			Integer addressId = null;
			boolean flag = true;
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String query = "select addressId from Address where street = ? and city = ? and state = ? and zip = ? and country = ?;";

			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, a.getStreet());
				ps.setString(2, a.getCity());
				ps.setString(3, a.getState());
				ps.setString(4, a.getZIP());
				ps.setString(5, a.getCountry());
				rs = ps.executeQuery();
				if (rs.next()) {
					addressId = rs.getInt("addressId");
				} else {
					flag = false;
				}
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
				return flag;
	 
		}
		
		/*
		 * The method checks for duplicate entries in Address
		 */
		public static boolean isDuplicateEmailAddress(String e) {
			Integer emailId = null;
			boolean flag = true;
			Connection conn = Factory.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String query = "select emailId from EmailAddress where emailAddress = ?;";

			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, e);
				rs = ps.executeQuery();
				if (rs.next()) {
					emailId = rs.getInt("emailId");
				} else {
					flag = false;
				}
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
				return flag;
	 
		}

		
		

}
