package com.techelevator.tenmo.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransferNotFound extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1964308867887927005L;
	

	public TransferNotFound(String message) {
	}	
}
