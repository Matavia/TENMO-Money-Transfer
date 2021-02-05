package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TenmoAccount {
	
	private int id;
	private BigDecimal balance;
	private int userId;
	
	
	

	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


}
