package com.techelevator.tenmo.controller;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.TenmoAccount;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserAlreadyExistsException;
import com.techelevator.tenmo.security.jwt.JWTFilter;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import com.techelevator.tenmo.model.User;


@RestController
public class UserController {
	
	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	//Gets a list of all the users
	@RequestMapping(path = "users",method = RequestMethod.GET)
	public List<User> getAll(){
		return userDAO.findAll();
	}
	
	//Finds user based by username ( just a test that data is being transferred )
	@RequestMapping(path ="users/{user}",method = RequestMethod.GET)
	public User getUserByUsername(@PathVariable String user) {
		
		return userDAO.findByUsername(user);
	}
	
	
	
	
	//pass in username information to display the correct balance
	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public String getBalance(Principal principal) {
		TenmoAccount test = new TenmoAccount();
		 test.setBalance(300);
		String message = "Your current balance is: " + test.getBalance();
		
		return message;
		
	}
	
	@RequestMapping(path ="/transfers", method = RequestMethod.GET)
	public String viewTransfers() {
		
		TenmoAccount test = new TenmoAccount();
		TenmoAccount test1 =new TenmoAccount();
		
		  test.setAccountTo("me");
		  test.setAccountFrom("you");
		  test.setAmount(100);
		
		  return test.getAccountTo() +  test.getAccountFrom() + test.getAmount();
	}
	
	@RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
	public String viewTransferDetails(@PathVariable int id) {
		TenmoAccount test = new TenmoAccount();
		test.setTransferId(id);
		test.setUsername("Name");
		test.setAccountFrom("Me");
		test.setAccountTo("Them");
		test.setTransferType("Send");
		test.setAmount(20);
		
		return test.getTransferId() + test.getAccountFrom() + test.getAccountTo() + test.getAmount();
		
	}
	
	 @RequestMapping(path = "/sendtransfer", method = RequestMethod.POST)
	    public Transfer sendTransfer( @RequestBody Transfer transfer) {
		 Transfer newTransfer = transfer;
		 
		 return newTransfer;
	 }
		 
		 @RequestMapping(path = "/requesttransfer", method = RequestMethod.POST)
		    public Transfer requestTransfer( @RequestBody Transfer transfer) {
			 Transfer newTransfer = transfer;
			 return newTransfer;
		
		 
		 
	 }

}
