package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;

import java.sql.Connection;
import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// List all Transfers
	@Override
	public List<Transfer> listAll() {
		// TODO Auto-generated method stub
		List<Transfer> transfers = new ArrayList<>();
		String query = "SELECT * FROM transfers "
				+ "JOIN transfer_types ON transfer_types.transfer_type_id = transfers.transfer_type_id";

		SqlRowSet results = jdbcTemplate.queryForRowSet(query);
		while (results.next()) {
			Transfer transfer = mapRowToTransfer(results);
			transfers.add(transfer);
		}
		return transfers;
	}

	// List User Transfer History
	public List<Transfer> listTransfersByUserId(int id) {

		List<Transfer> transfers = new ArrayList<>();
		String query = "SELECT * FROM transfers "
				+ "JOIN transfer_types ON transfer_types.transfer_type_id = transfers.transfer_type_id "
				+ "WHERE account_from =" + id + " OR account_to=" + id;

		SqlRowSet results = jdbcTemplate.queryForRowSet(query);
		while (results.next()) {
			Transfer transfer = mapRowToTransfer(results);
			transfers.add(transfer);
		}
		return transfers;
	}

	// Sends Transfers, updates balance, and inserts a record in transfer table
	@Override
	public Transfer transfer(int userId, Transfer transfer) throws Exception {

		int accountTo = transfer.getAccountTo();
		int accountFrom = userId;

		BigDecimal amount = transfer.getAmount();
		UserSqlDAO userDAO = new UserSqlDAO(jdbcTemplate);

		Connection con = this.jdbcTemplate.getDataSource().getConnection();

		try {
			con.setAutoCommit(false);

			BigDecimal balance = userDAO.findBalanceByUserId(userId);

			if (balance.compareTo(amount) < 0) {
				// TODO write a custom exception that uses a 400 status exception
				throw new InsufficientFundsExceptions("You aint got no money");
			}

			userDAO.updateBalance(accountTo, accountFrom, amount);

			String insertTransfer = "INSERT INTO transfers (transfer_type_id,transfer_status_id,account_From, account_To, amount) "
					+ "VALUES ('2','2', ?, ?, ?);";

			jdbcTemplate.update(insertTransfer, accountFrom, accountTo, amount);

			con.commit();
		} catch (Exception ex) {
			con.rollback();
			throw ex;
		} finally {
			con.setAutoCommit(true);
		}

		return transfer;

	}

	// List transfer by Status Id
	@Override
	public List<Transfer> findByStatus(int status) {
		// TODO Auto-generated method stub
		List<Transfer> findStatus = new ArrayList<>();
		for (Transfer transfer : this.listAll()) {
			if (transfer.getTransferStatus() == status) {
				findStatus.add(transfer);

			}
		}

		return findStatus;
	}

	// Converts DB row into a transfer object
	private Transfer mapRowToTransfer(SqlRowSet rs) {
		UserSqlDAO userDAO = new UserSqlDAO(jdbcTemplate);

		Transfer transfer = new Transfer();
		transfer.setAccountToUsername(userDAO.findUsernameById(rs.getInt("account_to")));
		transfer.setAccountFromUsername(userDAO.findUsernameById(rs.getInt("account_from")));
		transfer.setTransferId(rs.getInt("transfer_id"));
		transfer.setTransferType(rs.getInt("transfer_type_id"));
		transfer.setTransferStatus(rs.getInt("transfer_status_id"));
		transfer.setAccountFrom(rs.getInt("account_from"));
		transfer.setAccountTo(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		transfer.setTransferTypeDesc(rs.getString("transfer_type_desc"));

		return transfer;

	}

}
