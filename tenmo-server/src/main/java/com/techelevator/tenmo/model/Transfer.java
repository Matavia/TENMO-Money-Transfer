package com.techelevator.tenmo.model;

public class Transfer {
	
	
	private int transferId;
	private String accountTo;
	private String accountFrom;
	private Long amount;
	private String transferType;
	private int transferStatus;
	
	public Transfer() {}
	
	public Transfer(int transferId, String accountTo, String accountFrom, Long amount, String trasnferType, int transferStatus, String transferType) {
		this.transferId = transferId;
		this.accountTo = accountTo;
		this.accountFrom = accountFrom;
		this.amount = amount;
		this.transferType = transferType;
		this.transferStatus = transferStatus;
	}
	
	
	public int getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public String getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}
	public String getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
