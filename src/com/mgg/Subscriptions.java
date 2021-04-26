package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
 * 
 * Subscriptions class extends the Sales class
 * 
 * It has additional Double variable called annualFee
 * 
 */

public class Subscriptions extends Items {
	
	private Double annualFee;
	private LocalDate beginDate;
	private LocalDate endDate;
	

	public Subscriptions(String itemCode, String type, String name) {
		super(itemCode, type, name);
	}
	
	public Subscriptions(String code, String type, String name, Double annualFee) {
		super(code, type, name);
		this.annualFee = annualFee;
	}
	
	public Subscriptions(String itemCode, String type, String name, Double annualFee, LocalDate beginDate, LocalDate endDate) {
		super(itemCode, type, name);
		this.annualFee = annualFee;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	

	public Double getAnnualFee() {
		return annualFee;
	}
	
	
	public LocalDate getBeginDate() {
		return beginDate;
	}

	
	public LocalDate getEndDate() {
		return endDate;
	}

	@Override
	public double getCost() {
		double value = ((this.beginDate.until(this.endDate,ChronoUnit.DAYS) + 1)/365.0) * this.annualFee;
		return Math.round(value);
	}

	@Override
	public double getTaxRate() {
		return 0.0;
	}
	
	public int getNumOfDays() {
		return (int) this.beginDate.until(this.endDate,ChronoUnit.DAYS);
	}
	
	@Override
	public String toString() {
		return "Subscriptions [annualFee=" + annualFee + ", beginDate=" + beginDate + ", endDate=" + endDate + this.getName() +"]";
	}
	
	}
	