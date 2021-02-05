package com.techelevator.tenmo.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFundsExceptions extends Exception{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1378717497134306481L;

	public InsufficientFundsExceptions(String message) {
		
	}
	
}
