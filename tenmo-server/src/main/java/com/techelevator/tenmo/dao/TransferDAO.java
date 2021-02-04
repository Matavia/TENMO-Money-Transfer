package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDAO {
	
	List<Transfer> listAll();
	
	List<Transfer> findByStatus(int status);
	
	List<Transfer> findByUsername();
	
	
	
	

}
