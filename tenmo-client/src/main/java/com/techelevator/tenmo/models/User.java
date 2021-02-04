package com.techelevator.tenmo.models;

public class User {

	private Integer id;
	private String username;
	private Long balance;
	
	public User(Integer id, String username, Long balance ) {
		this.id = id;
		this.username = username;
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	
	
}
