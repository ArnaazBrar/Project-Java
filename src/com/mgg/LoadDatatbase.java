package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoadDatatbase { // Line 292, correct type

	public static ArrayList<Persons> getPersonDb() {

		ArrayList<Persons> persons = new ArrayList<Persons>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select p.personId,p.personCode,p.personType,p.lastName,p.firstName"
				+ ",p.personAddressId,a.street,a.city,a.state,a.zip,a.country from Person p "
				+ "left join Address a on p.personAddressId = a.addressId;";
		
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String query1 = "select emailAddress from EmailAddress where personId = ?;";

		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				ArrayList<String> eList = new ArrayList<String>();
				int personId = rs.getInt("personId");
				String personCode = rs.getString("personCode");
				Character type = rs.getString("personType").charAt(0);
				String lastName = rs.getString("lastname");
				String firstName = rs.getString("firstname");
				int personAddressId = rs.getInt("personAddressId");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");

				Address address = new Address(street, city, state, zip, country);
				
				ps1 = conn.prepareStatement(query1);
				ps1.setInt(1, personId);
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					eList.add(rs1.getString("emailAddress"));
				}
				rs1.close();
				ps1.close();
				
				Persons p = new Persons(personCode, type, lastName, firstName, address, eList);
				persons.add(p);

			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return persons;
	}

	public static ArrayList<Stores> getStoresDb() {

		ArrayList<Stores> stores = new ArrayList<Stores>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select s.storeCode,s.storeManagerId,a.street,a.city,a.state,a.zip,a.country from Store s left join Address a on s.storeAddressId = a.addressId;";
	;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String storeCode = rs.getString("s.storeCode");
				int storeManagerId = rs.getInt("s.storeManagerId");
				Persons storeManager = getPersonById(storeManagerId);
				String street = rs.getString("a.street");
				String city = rs.getString("a.city");
				String state = rs.getString("a.state");
				String zip = rs.getString("a.zip");
				String country = rs.getString("a.country");

				Address address = new Address(street, city, state, zip, country);
				Stores s = new Stores(storeCode, storeManager, address);
				stores.add(s);

			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return stores;

	}

	public static ArrayList<Items> getItemsDb() {

		ArrayList<Items> items = new ArrayList<Items>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select itemId,itemCode,itemType,itemName from Item;";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int itemId = rs.getInt("ItemId");
				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");

				if (itemType.equals("PN") || itemType.equals("PU")) {
					double basePrice = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select basePrice from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						basePrice = rs1.getDouble("basePrice");
					} else {
						throw new IllegalStateException("No BasePrice found for this item");
					}

					Items product = new Products(itemCode, itemType, itemName, basePrice);

					items.add(product);
					rs1.close();
					ps1.close();

				} else if (itemType.equals("PG")) {

					Items product = new Products(itemCode, itemType, itemName);
					items.add(product);

				} else if (itemType.equals("SV")) {
					double hourlyRate = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select hourlyRate from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						hourlyRate = rs1.getDouble("hourlyRate");
					} else {
						throw new IllegalStateException("No Hourly Rate found for this item");
					}

					Items service = new Services(itemCode, itemType, itemName, hourlyRate);
					items.add(service);

					rs1.close();
					ps1.close();

				} else if (itemType.equals("SB")) {
					double annualFee = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select annualFee from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						annualFee = rs1.getDouble("annualFee");
					} else {
						throw new IllegalStateException("No Annual Fee Found for this item");
					}

					Items subscription = new Subscriptions(itemCode, itemType, itemName, annualFee);
					items.add(subscription);

					rs1.close();
					ps1.close();
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return items;

	}

	public static ArrayList<Sales> getSalesDb() {
		ArrayList<Sales> sales = new ArrayList<Sales>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String query = "select salesId,salesCode,storeId,customerId,salesPersonId from Sale;";
		
		try {
			ps1 = conn.prepareStatement(query);
			rs1 = ps1.executeQuery();
			
			while (rs1.next()) {
				Sales sale = null;
				ArrayList<Items> items = new ArrayList<Items>();
				int salesId = rs1.getInt("salesId");
				String salesCode = rs1.getString("salesCode");
				int storeId = rs1.getInt("storeId");
				Stores store = getStoreById(storeId);
				int customerId = rs1.getInt("customerId");
				Persons customer = getPersonById(customerId);
				int salesPersonId = rs1.getInt("salesPersonId");
				Persons salesPerson = getPersonById(salesPersonId);
				
				sale = new Sales(salesCode,store,customer,salesPerson);
				sales.add(sale);
				
				items = getSaleItems(salesId);
	
				
				for (Items it : items) {

        			Integer itemId = Helpers.getItemIdByCode((it.getCode()));
        			PreparedStatement ps3 = null;
    				ResultSet rs3 = null;
    				
					if (it.getType().equals("PN") || it.getType().equals("PU")) {
						int quantity = 0;
						String query3 = "select quantity from ItemSale where itemId = ?;";
						ps3 = conn.prepareStatement(query3);
						ps3.setInt(1, itemId);
						rs3 = ps3.executeQuery();
						
						if (rs3.next()) {
							quantity = rs3.getInt("quantity");
						} else {
							throw new IllegalStateException("No quantity found for this item");
						}
						
						double basePrice = getBasePrice(getItemsDb(),it.getCode());
						Items product = new Products(it.getCode(), it.getType(), it.getName(), basePrice,
								quantity);

						sale.addItem(product);
						
					} else if (it.getType().equals("PG")) {
						double quantity = 0.0;
					
						String query3 = "select giftCardQuantity from ItemSale where itemId = ?;";
						ps3 = conn.prepareStatement(query3);
						ps3.setInt(1, itemId);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							quantity = rs3.getDouble("giftCardQuantity");
						} else {
							throw new IllegalStateException("No quantity found for this item");
						}
						
						Items product = new Products(it.getCode(), it.getType(), it.getName(), null,
								quantity);

						sale.addItem(product);
						
					} else if (it.getType().equals("SV")) {
						String employeeCode = null;
						double numOfHours = 0;
						
						String query3 = "select employeeCode,numOfHours from ItemSale where itemId = ?;";
						ps3 = conn.prepareStatement(query3);
						ps3.setInt(1, itemId);
						rs3 = ps3.executeQuery();
						
						if (rs3.next()) {
							employeeCode = rs3.getString("employeeCode");
							numOfHours = rs3.getDouble("numOfHours");     // Correct type in database, then here
						} else {
							throw new IllegalStateException("Nothing found for this item");
						}
						
						Double hourlyRate = getHourlyRate(getItemsDb(), it.getCode());
						Items service = new Services(it.getCode(), it.getType(), it.getName(), hourlyRate,
								employeeCode, numOfHours);

						sale.addItem(service);
						
					} else  if (it.getType().equals("SB")) {
						LocalDate beginDate;
						LocalDate endDate;
						
						String query3 = "select beginDate, endDate from ItemSale where itemId = ?;";
						ps3 = conn.prepareStatement(query3);
						ps3.setInt(1, itemId);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							beginDate = LocalDate.parse(rs3.getString("beginDate"));
							endDate =  LocalDate.parse(rs3.getString("endDate"));
							
						} else {
							throw new IllegalStateException("No Date found for this item");
						}
						
						double annualFee = getAnnualFee(getItemsDb(), it.getCode());
						Items subscription = new Subscriptions(it.getCode(), it.getType(), it.getName(),
								annualFee, beginDate, endDate);

						sale.addItem(subscription);
						
					}
					
					rs3.close();
					ps3.close();
						
				}
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		try {
			rs1.close();
			ps1.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		return sales;
		
	}

	
	// Additional Helping Methods
	public static Address getAddressById(int addressId) {
		Address address = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select street,city,state,zip,country from Address where addressId = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, addressId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");

				address = new Address(street, city, state, zip, country);
			} else {
				throw new IllegalStateException("No Address Found");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return address;

	}

	public static ArrayList<String> getEmailListFromId(int personId) {
		ArrayList<String> eList = new ArrayList<String>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select emailAddress from EmailAddress where personId = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while (rs.next()) {
				eList.add(rs.getString("emailAddress"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return eList;

	}

	public static Persons getPersonById(int personId) {
		Persons p = null;
		ArrayList<String> eList = new ArrayList<String>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select p.personId,p.personCode,p.personType,p.lastName,p.firstName,p.personAddressId,a.street,a.city,a.state,a.zip,a.country from Person p left join Address a on p.personAddressId = a.addressId where personId = ?;";

		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String query1 = "select emailAddress from EmailAddress where personId = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int pId = rs.getInt("personId");
				String personCode = rs.getString("personCode");
				Character type = rs.getString("personType").charAt(0);
				String lastName = rs.getString("lastname");
				String firstName = rs.getString("firstname");
				int personAddressId = rs.getInt("personAddressId");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");

				Address address = new Address(street, city, state, zip, country);
				
				ps1 = conn.prepareStatement(query1);
				ps1.setInt(1, personId);
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					eList.add(rs1.getString("emailAddress"));
				}
				rs1.close();
				ps1.close();
	
				p = new Persons(personCode, type, lastName, firstName, address, eList);
			} else {
				throw new IllegalStateException("No Person Found");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return p;

	}

	public static Stores getStoreById(int sId) {

		Stores store = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select s.storeCode,s.storeId,s.storeManagerId,a.street,a.city,a.state,a.zip,a.country from Store s left join Address a on s.storeAddressId = a.addressId where s.storeId = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, sId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int storeId = rs.getInt("storeId");
				String storeCode = rs.getString("storeCode");
				int storeManagerId = rs.getInt("storeManagerId");
				Persons storeManager = getPersonById(storeManagerId);
				//int storeAddressId = rs.getInt("storeAddressId");
				String street = rs.getString("a.street");
				String city = rs.getString("a.city");
				String state = rs.getString("a.state");
				String zip = rs.getString("a.zip");
				String country = rs.getString("a.country");

				Address address = new Address(street, city, state, zip, country);
				
				store = new Stores(storeCode, storeManager, address);

			} else {
				throw new IllegalStateException("No Store Found");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return store;

	}
	
	public static ArrayList<Items> getSaleItems(Integer salesId) {
		ArrayList<Items> i = new ArrayList<Items>();
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select itemId from ItemSale where salesId = ?;";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, salesId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Integer itemId = rs.getInt("itemId");
				Items item = getItemById(itemId);
				i.add(item);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return i;
		
	}

	public static Items getItemById(int iId) {

		Items i = null;
		Connection conn = Factory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select itemId,itemCode,itemType,itemName from Item where itemId = ?;";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, iId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int itemId = rs.getInt("ItemId");
				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");

				if (itemType.equals("PN") || itemType.equals("PU")) {
					double basePrice = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select basePrice from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						basePrice = rs1.getDouble("basePrice");
					} else {
						throw new IllegalStateException("No BasePrice found for this item");
					}

					i = new Products(itemCode, itemType, itemName, basePrice);

					rs1.close();
					ps1.close();

				} else if (itemType.equals("PG")) {

					i = new Products(itemCode, itemType, itemName);

				} else if (itemType.equals("SV")) {
					double hourlyRate = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select hourlyRate from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						hourlyRate = rs1.getDouble("hourlyRate");
					} else {
						throw new IllegalStateException("No Hourly Rate found for this item");
					}

					i = new Services(itemCode, itemType, itemName, hourlyRate);

					rs1.close();
					ps1.close();
				
				} else if (itemType.equals("SB")) {
					double annualFee = 0.0;
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					String query1 = "select annualFee from Item where itemId = ?;";

					ps1 = conn.prepareStatement(query1);
					ps1.setInt(1, itemId);
					rs1 = ps1.executeQuery();

					if (rs1.next()) {
						annualFee = rs1.getDouble("annualFee");
					} else {
						throw new IllegalStateException("No Annual Fee Found for this item");
					}

					i = new Subscriptions(itemCode, itemType, itemName, annualFee);

					rs1.close();
					ps1.close();

				} else {
					throw new IllegalStateException("No Item Found");
				}

			}

			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return i;

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
