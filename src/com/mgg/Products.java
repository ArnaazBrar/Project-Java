package com.mgg;

 /*
  * 
  * Products class extends the Sales class
  * 
  * Includes additional variable (i.e. basePrice)
  * 
  */

public class Products extends Items {
	
	private Double basePrice;
	private Integer quantity;
	private Double amountQuantity;
	

	public Products(String itemCode, String type, String name) {
		super(itemCode, type, name);
	}
	
	
	public Products(String code, String type, String name, Double basePrice) {
		super(code, type, name);
		this.basePrice = basePrice;
	}
	
	
	public Products(String itemCode, String type, String name, Double basePrice,
			Integer quantity) {
		super(itemCode, type, name);
		this.basePrice = basePrice;
		this.quantity = quantity;
	}
	
	public Products(String itemCode, String type, String name, Double basePrice,
			Double amountQuantity) {
		super(itemCode, type, name);
		this.basePrice = basePrice;
		this.amountQuantity = amountQuantity;
	}
	

	public Double getBasePrice() {
		return basePrice;
	}
	

	public Integer getQuantity() {
		return quantity;
	}

	
	public Double getAmountQuantity() {
		return amountQuantity;
	}


	@Override
	public double getCost() {
		if (basePrice == null) {
			return this.amountQuantity;
		} else if (basePrice != null) {
			if (this.getType().equals("PN")) {
			return  (this.basePrice  *  this.quantity); 
			} else if (this.getType().equals("PU")) {
				return this.basePrice  *  this.quantity * 0.80; 
				}
		}
		return 0;
	}


	@Override
	public double getTaxRate() {
		return 0.0725;
	}
 
	
	@Override
	public String toString() {
		return "Products [basePrice=" + basePrice + ", quantity=" + quantity + ", amountQuantity=" + amountQuantity
				+ this.getName() + "]";
	}
}