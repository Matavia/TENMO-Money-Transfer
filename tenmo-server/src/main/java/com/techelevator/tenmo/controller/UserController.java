package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.techelevator.tenmo.dao.TransferDAO;
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
	private TransferDAO transferDAO;

	public UserController(UserDAO userDAO, TransferDAO transferDAO) {
		this.userDAO = userDAO;
		this.transferDAO = transferDAO;
	}
	
	
	//Shows all transfers
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "transfers", method = RequestMethod.GET)
	public List<Transfer> listAllTransfers() {
		return transferDAO.listAll();
	}
	
	
	//View Transfer History - Sent and Received
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "transfers/{userId}", method = RequestMethod.GET)
	public List<Transfer> viewUserTransferHistory(@PathVariable int userId){
		return transferDAO.listTransfersByUserId(userId);
		
	}

	//View Transfer Details
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "transferdetails/{id}",method = RequestMethod.GET)
	public Transfer viewTransactionDetails(@PathVariable int id) throws Exception {
		
		return transferDAO.getTransferDetailsById(id);
	}
	// Gets a list of all the users
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> getAll() {
		return userDAO.findAll();
	}

	// pass in username information to display the correct balance
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/balance/", method = RequestMethod.GET)
	public BigDecimal getBalance(Principal principal) {
	String userName = principal.getName();
	int userId = this.userDAO.findIdByUsername(userName);
		return userDAO.findBalanceByUserId(userId);
	
	}

	
	//sends transfer
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/sendtransfer", method = RequestMethod.POST)
	public Transfer sendTransfer(@RequestBody Transfer transfer, Principal principal) throws Exception {
		
		String userName = principal.getName();
		int userId = this.userDAO.findIdByUsername(userName);
		
		return transferDAO.transfer(userId,transfer);
		
		
	}

	//not a requirement yet to send requests 
	@RequestMapping(path = "/requesttransfer", method = RequestMethod.POST)
	public Transfer requestTransfer(@RequestBody Transfer transfer) {
		Transfer newTransfer = transfer;
		return newTransfer;

	}

}
