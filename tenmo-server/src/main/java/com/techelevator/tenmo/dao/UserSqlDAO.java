package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSqlDAO implements UserDAO {

	private static final double STARTING_BALANCE = 1000;
	private JdbcTemplate jdbcTemplate;

	public UserSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Lists all Users
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		String sql = "select * from users";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			User user = mapRowToUser(results);
			users.add(user);
		}

		return users;
	}

	// Finds user by Username, and returns User Object
	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		for (User user : this.findAll()) {
			if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return user;
			}
		}
		throw new UsernameNotFoundException("User " + username + " was not found.");
	}
	// TODO add an exception

	// Gets User Id by Username
	@Override
	public int findIdByUsername(String username) {
		return jdbcTemplate.queryForObject("SELECT user_id from users WHERE username = ?", int.class, username);
	}

	// Gets Username by Id
	public String findUsernameById(int id) {
		String username = null;
		for (User user : this.findAll()) {
			if (user.getId() == id) {
				username = user.getUsername();
			}
		}
		return username;
	}

	// Gets User balance by User Id
	@Override
	public BigDecimal findBalanceByUserId(int id) {
		return jdbcTemplate.queryForObject("SELECT balance FROM accounts WHERE user_id = ?", BigDecimal.class, id);
	}

	// Updates balances after a successful transfer
	@Override
	public void updateBalance(int accountTo, int accountFrom, BigDecimal amount) {

		BigDecimal accountFromBalance = findBalanceByUserId(accountFrom);
		accountFromBalance = accountFromBalance.subtract(amount);
		String updateAccountFromSql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(updateAccountFromSql, accountFromBalance, accountFrom);

		BigDecimal accountToBalance = findBalanceByUserId(accountTo);
		accountToBalance = accountToBalance.add(amount);
		String updateAccountToSql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(updateAccountToSql, accountToBalance, accountTo);

	}

	@Override
	public boolean create(String username, String password) {
		boolean userCreated = false;
		boolean accountCreated = false;

		// create user
		String insertUser = "insert into users (username,password_hash) values(?,?)";
		String password_hash = new BCryptPasswordEncoder().encode(password);

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "user_id";
		userCreated = jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertUser, new String[] { id_column });
			ps.setString(1, username);
			ps.setString(2, password_hash);
			return ps;
		}, keyHolder) == 1;
		int newUserId = (int) keyHolder.getKeys().get(id_column);

		// create account
		String insertAccount = "insert into accounts (user_id,balance) values(?,?)";
		accountCreated = jdbcTemplate.update(insertAccount, newUserId, STARTING_BALANCE) == 1;

		return userCreated && accountCreated;
	}

	private User mapRowToUser(SqlRowSet rs) {
		User user = new User();
		user.setId(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password_hash"));
		user.setActivated(true);
		user.setAuthorities("ROLE_USER");
		return user;
	}

}
