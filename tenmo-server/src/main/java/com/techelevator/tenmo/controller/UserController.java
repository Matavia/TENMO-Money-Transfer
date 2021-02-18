package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
public class UserController {

	private UserDAO userDAO;
	private TransferDAO transferDAO;

	public UserController(UserDAO userDAO, TransferDAO transferDAO) {
		this.userDAO = userDAO;
		this.transferDAO = transferDAO;
	}

	// Gets a list of all the users
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> getAll() {
		return userDAO.findAll();
	}

	// Gets Balance by Username
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "balance", method = RequestMethod.GET)
	public BigDecimal getBalance(Principal principal) {

		String userName = principal.getName();
		int userId = this.userDAO.findIdByUsername(userName);
		return userDAO.findBalanceByUserId(userId);

	}

	// Shows all transfers
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "transfers", method = RequestMethod.GET)
	public List<Transfer> listAllTransfers() {
		return transferDAO.listAll();
	}

	// View Transfer History - Sent and Received
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "transferhistory", method = RequestMethod.GET)
	public List<Transfer> viewUserTransferHistory(Principal principal) {
		String userName = principal.getName();
		int userId = this.userDAO.findIdByUsername(userName);

		return transferDAO.listTransfersByUserId(userId);

	}

	// sends transfer
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "sendtransfer", method = RequestMethod.POST)
	public Transfer sendTransfer(@RequestBody Transfer transfer, Principal principal) throws Exception {

		String userName = principal.getName();
		int userId = this.userDAO.findIdByUsername(userName);
		transfer = transferDAO.transfer(userId, transfer);
		transfer.setAccountToUsername(userDAO.findUsernameById(transfer.getAccountTo()));
		transfer.setAccountFromUsername(principal.getName());
		return transfer;

	}

}
