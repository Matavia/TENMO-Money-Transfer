package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {

	public String authToken = "";

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public UserService(String url) {
		BASE_URL = url;
	}

	// get balance of user
	public BigDecimal getBalance() {
		BigDecimal balance = null;
		try {

			balance = restTemplate.exchange(BASE_URL + "balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class)
					.getBody();
		} catch (RestClientResponseException ex) {

		}
		return balance;
	}
	
	public Transfer sendTransfer(int accountToId, BigDecimal amount, int accountFromId) {
		Transfer transfer = new Transfer(accountToId,accountFromId,amount);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);
		Transfer sentTransfer = restTemplate.postForObject(BASE_URL + "sendtransfer", entity, Transfer.class);
		
		return sentTransfer;
		
	}

	public Transfer[] viewTransferHistory() {
		Transfer[] transferHistory = null;
		transferHistory = restTemplate
				.exchange(BASE_URL + "transferhistory", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		return transferHistory;
	}

	public Transfer viewTransferDetails(int id) {
		Transfer foundTransfer = null;
		Transfer[] transfers = viewTransferHistory();
		for(Transfer transfer : transfers) {
			if(transfer.getTransferId() == id) {
				foundTransfer = transfer;
			}
		}
			return foundTransfer;
			
		
	
	}
	 // public Transfer viewTransferDetails() { }
	 
	  public User[] listAllUsers(){
		  User[] userList = null;
		  userList = restTemplate.exchange(BASE_URL + "users",HttpMethod.GET,makeAuthEntity(), User[].class).getBody();
		  return userList;
		  
	  }
	  
	//public Transfer sendTransfer() {
	 
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	

}
