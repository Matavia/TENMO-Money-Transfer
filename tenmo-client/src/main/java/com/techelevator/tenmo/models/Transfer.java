package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
	
	
	private int transferId;
	private int accountTo; 
	private String accountToUsername;
	private String accountFromUsername;
	private int accountFrom;
	private BigDecimal amount;
	private int transferType;
	private int transferStatus;
	private String transferTypeDesc;
	


	public Transfer() {}
	
	public Transfer(int transferId, int accountTo, int accountFrom, BigDecimal amount, int trasnferType, int transferStatus, int transferType) {
		this.transferId = transferId;
		this.accountTo = accountTo;
		this.accountFrom = accountFrom;
		this.amount = amount;
		this.transferType = transferType;
		this.transferStatus = transferStatus;
	}
	
	public Transfer(int accountTo, int accountFrom, BigDecimal amount) {
		
		this.accountTo = accountTo;
		this.accountFrom = accountFrom;
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
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setAccountFromUsername(String accountFromUsername) {
		this.accountFromUsername = accountFromUsername;
	}
	public void setAccountToUsername(String accountToUsername) {
		this.accountToUsername = accountToUsername;
	}
	
	 public String getAccountToUsername() {
		return accountToUsername;
	}
	 public String getAccountFromUsername() {
			return accountFromUsername;
		}
		public String getTransferTypeDesc() {
			return transferTypeDesc;
		}

		public void setTransferTypeDesc(String transferTypeDesc) {
			this.transferTypeDesc = transferTypeDesc;
		}

	 @Override
	 public String toString() {
		 String output =
				 "\n\n\nId: " + getTransferId() + "\n" +
				 "From: " + getAccountFromUsername() + "\n" +
				 "To: " + getAccountToUsername() + "\n" + 
				 "Amount: $" + getAmount() + "\n" +
				 "Type: " + getTransferTypeDesc() + "\n";
				 
		 return output;
	 }

	}

