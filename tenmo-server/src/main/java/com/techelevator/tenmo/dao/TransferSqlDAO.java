package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@Override
	public List<Transfer> listAll() {
		// TODO Auto-generated method stub
		List<Transfer> transfers = new ArrayList<>();
		String query = "SELECT * FROM transfers";

		SqlRowSet results = jdbcTemplate.queryForRowSet(query);
		while (results.next()) {
			Transfer transfer = mapRowToTransfer(results);
			transfers.add(transfer);
		}
		return transfers;
	}

	@Override
	public Transfer transfer(Transfer transfer) {
		int accountTo = transfer.getAccountTo();
		int accountFrom = transfer.getAccountFrom();
		BigDecimal amount = transfer.getAmount();
		UserSqlDAO userDAO = new UserSqlDAO(jdbcTemplate);
		boolean transferComplete = false;
		/*while (!transferComplete) {
			try {
				BigDecimal balance = userDAO.findBalanceByUserId(accountFrom);
				
				//if (balance.compareTo(amount) < 0)
					;
			} catch (Exception e) {
				System.out.println("You cannot send more TE Bucks than you have in your account");
             
			}
			*/
			userDAO.updateBalance(accountTo, accountFrom, amount);
			
			transferComplete = true;
			
			return transfer;
			
		}
	//}

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

	@Override
	public List<Transfer> findByUsername() {

		// TODO Auto-generated method stub

		return null;
	}

	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getInt("transfer_id"));
		transfer.setTransferType(rs.getString("transfer_type_id"));
		transfer.setTransferStatus(rs.getInt("transfer_status_id"));
		transfer.setAccountFrom(rs.getInt("account_from"));
		transfer.setAccountTo(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));

		return transfer;

	}

}
