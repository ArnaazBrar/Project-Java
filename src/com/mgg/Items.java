package com.mgg;

/*
 * Sales class creates object for any
 * sales that take place
 * 
 */

public abstract class Items {
	
	private Integer itemId;
	private String itemCode;
	private String name;
	private String type;
	
	
	public Items(Integer itemId, String itemCode, String name, String type) {
		super();
		this.itemId = itemId;
		this.itemCode = itemCode;
		this.name = name;
		this.type = type;
	}

	public Items(String itemCode, String type, String name) {
		super();
		this.itemCode = itemCode;
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return itemCode;
	}

	public String getName() {
		return name;
	}
	
	public Integer getItemId() {
		return itemId;
	}

	public abstract double getCost();
	
	public abstract double getTaxRate();
	
	public double getTotal() {
		return this.getTax() + this.getCost();	
	}
	
	public double getTax() {
		return this.getTaxRate() * this.getCost();
	}
	
	@Override
	public String toString() {
		return "Sales [itemCode=" + itemCode +  ", name=" + name + "]";
	}
	

}