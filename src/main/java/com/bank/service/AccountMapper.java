package bank.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import bank.bo.Account;

public class AccountMapper implements RowMapper<Account> {
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Account account = new Account();
		try {
			account.setId(rs.getInt("id"));
			account.setName(rs.getString("name"));
			account.setCellnumber(rs.getString("cellnumber"));
			account.setEmail(rs.getString("email"));
			account.setBalance(rs.getDouble("balance"));
		} catch (Exception e) {
			e.printStackTrace();
			return account.setMessage("no User found");
		}
		return account;
	}
}
