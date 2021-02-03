package com.techelevator.tenmo.models.dao;

import java.util.List;

import com.techelevator.tenmo.models.User;

public interface  UserDAO {
	User getBalance(int id);
    
    User viewTransfers(int id);
    
    List<User> transfer(int id);
    
    List<User> viewPending(int id);
    
}


