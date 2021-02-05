package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {
	
	public  String authToken = "";
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	

	public UserService(String url) {
		BASE_URL = url;
	}
	
	//get balance of user
	public User getBalance(int id) {
		User user = null;
		try {
			user = restTemplate.exchange(BASE_URL + "balance/" + id, HttpMethod.GET, makeAuthEntity(), User.class).getBody();
		} catch (RestClientResponseException ex) {
			
		}
		return user;
	}
	
	public List<Transfer> viewTransferHistory(){
	}
	
	
	public Transfer viewTransferDetails() {
	}
	
	public List<User> listAllUsers(){
		
	}
	
	public Transfer sendTransfer() {
		
	}
	
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authToken);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
}
