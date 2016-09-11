package bank.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import bank.service.DataSource;

import bank.bo.Account;

@Service
public class AccountJdbcTemplate {
	private JdbcTemplate jdbcTemplateObject;
	
	
	
	
	public void create(String name, String cellnumber, String email) {
		String SQL = "insert into Account (name, cellnumber,email) values (?, ?, ?)";
		
		jdbcTemplateObject.update(SQL, name, cellnumber, email);
		System.out.println("Created Record Name = " + name + " cellnumber = " + cellnumber + "email=" + email);
		return;
	}
	
	
	public void delete(Integer id) {
		String SQL = "delete from Account where id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}
	
	public Account getAccount(Integer id) {
		String SQL = "select * from Account where id = ?";
		Account account = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new AccountMapper());
		return account;
	}
	
	public Account getAccount(Integer id, String pass) {
		String SQL = "select * from Account where id = ? and password=?";
		Account account = jdbcTemplateObject.queryForObject(SQL, new Object[] { id, pass }, new AccountMapper());
		return account;
	}
	
	public Account getAccount(String cellnumber) {
		String SQL = "select * from Account where cellnumber = ?";
		Account account = jdbcTemplateObject.queryForObject(SQL, new Object[] { cellnumber }, new AccountMapper());
		return account;
	}
	
	public String getPassword(Integer id) {
		String SQL = "select password from Account where id = ?";
		String pass = (String) jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, String.class);
		return pass;
	}
	
	
	public List<Account> listAccounts() {
		String SQL = "select * from Account";
		List<Account> accounts = jdbcTemplateObject.query(SQL, new AccountMapper());
		return accounts;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	
	public void setPassword(String password, Integer id) {
		String SQL = "update Account set password = ? where id = ?";
		jdbcTemplateObject.update(SQL, password, id);
		System.out.println("Updated Record with ID = " + id);
		return;
	}
	
	public void updateBalance(Integer id, double balance) {
		String SQL = "update Account set balance = ? where id = ?";
		jdbcTemplateObject.update(SQL, balance, id);
		System.out.println("Updated Record with ID = " + id);
		return;
	}
	
}

