package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO {
	
	List<Transfer> listAll();
	List<Transfer> listTransfersByUserId(int id);
	List<Transfer> findByStatus(int status);
    Transfer transfer(int userId, Transfer transfer) throws Exception;
    Transfer getTransferDetailsById(int id) throws Exception;
	
	
	
	

}
