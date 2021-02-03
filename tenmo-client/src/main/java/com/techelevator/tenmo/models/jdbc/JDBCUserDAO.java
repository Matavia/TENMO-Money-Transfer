package com.techelevator.tenmo.models.jdbc;

import java.util.List;

import java.util.ArrayList;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.dao.UserDAO;



public class JDBCUserDAO implements UserDAO {
	
	
	@Override
	public User getBalance(int id) {
		// TODO Auto-generated method stub
		String query = "SELECT balance FROM users WHERE id = ?";
		return null;
	}

	@Override
	public User viewTransfers(int id) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM transfer WHERE id = ?";
		
		return null;
	}

	@Override
	public List<User> transfer(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> viewPending(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
