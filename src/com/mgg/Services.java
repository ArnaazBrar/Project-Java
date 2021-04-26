package com.mgg;

/*
 * 
 * Services class extends the Sales class
 * 
 * It has an additional variable in the 
 * constructor called hourlyRate
 * 
 */

public class Services extends Items {
	
	private Double hourlyRate;
	private String employeeCode;
	private Double numberOfHours; 

	
	public Services(String itemCode, String type, String name) {
		super(itemCode, type, name);
	}
	
	
	public Services(String code, String type, String name, Double hourlyRate) {
		super(code, type, name);
		this.hourlyRate = hourlyRate;
	}
	

	public Services(String itemCode, String type, String name, Double hourlyRate,
			String employeeCode, Double numberOfHours) {
		super(itemCode, type, name);
		this.hourlyRate = hourlyRate;
		this.employeeCode = employeeCode;
		this.numberOfHours = numberOfHours;
	}

	
	public Double getHourlyRate() {
		return hourlyRate;
	}

	
	public String getEmployeeCode() {
		return employeeCode;
	}

	
	public Double getNumberOfHours() {
		return numberOfHours;
	}

	
	@Override
	public String toString() {
		return "Services [hourlyRate=" + hourlyRate + ", employeeCode=" + employeeCode + ", numberOfHours="
				+ numberOfHours + this.getName() + "]";
	}


	@Override
	public double getCost() {
		return (this.hourlyRate * this.numberOfHours);
	}


	@Override
	public double getTaxRate() {
		return 0.0285;
	}
	
	

}