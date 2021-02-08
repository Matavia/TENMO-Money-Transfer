package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
	
	
	private int transferId;
	private int accountToId;
	private String accountToUsername;
	private String accountFromUsername;
	private int accountFromId;
	private BigDecimal amount;
	private int transferType;
	private int transferStatus;
	private String transferTypeDesc;
	
	public Transfer() {}
	
	public Transfer(int transferId, int accountTo, int accountFrom, BigDecimal amount, int trasnferType, int transferStatus, int transferType) {
		this.transferId = transferId;
		this.accountToId = accountTo;
		this.accountFromId = accountFrom;
		this.amount = amount;
		this.transferType = transferType;
		this.transferStatus = transferStatus;
	}
	
	public Transfer(int accountTo, int accountFrom, BigDecimal amount) {
		
		this.accountToId = accountTo;
		this.accountFromId = accountFrom;
		this.amount = amount;
		
	}
	
	
	public int getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}
	public int getTransferType() {
		return transferType;
	}
	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public int getAccountFrom() {
		return accountFromId;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFromId = accountFrom;
	}
	public int getAccountTo() {
		return accountToId;
	}
	public void setAccountTo(int accountTo) {
		this.accountToId = accountTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getAccountToUsername() {
		return accountToUsername;
	}

	public void setAccountToUsername(String accountToUsername) {
		this.accountToUsername = accountToUsername;
	}

	public String getAccountFromUsername() {
		return accountFromUsername;
	}

	public void setAccountFromUsername(String accountFromUsername) {
		this.accountFromUsername = accountFromUsername;
	}
	
	 public String getTransferTypeDesc() {
		return transferTypeDesc;
	}

	public void setTransferTypeDesc(String transferTypeDesc) {
		this.transferTypeDesc = transferTypeDesc;
	}

	{
	
	}

	}

