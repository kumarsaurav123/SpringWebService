package bank.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import bank.bo.Payee;

@Service
public class PayeeDaoJDBCTemplate {
	private JdbcTemplate jdbcTemplateObject;
	
	public void create(String name, String cellnumber, String email, Integer userid) {
		String SQL = "insert into Payee (name, cellnumber,email,userid) values (?, ?, ?,?)";
		
		jdbcTemplateObject.update(SQL, name, cellnumber, email, userid);
		System.out.println("Created Record Name = " + name + " cellnumber = " + cellnumber + "email=" + email);
		return;
	}
	
	public void delete(Integer id){
		String SQL = "delete from Payee where id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id );
		return;
	}
	
	public Payee getPayee(Integer id) {
		String SQL = "select * from Payee where id = ?";
		Payee payee = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new PayeeMapper());
		return payee;
	}
	
	public Payee getPayee(Integer id, Integer userid) {
		String SQL = "select * from Payee where id = ? and userid=?";
		Payee payee = jdbcTemplateObject.queryForObject(SQL, new Object[] { id, userid }, new PayeeMapper());
		return payee;
	}
	
	public Payee getPayee(String cellnumber, Integer userid) {
		String SQL = "select * from Payee where cellnumber = ? and userid=?";
		Payee payee = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { cellnumber, userid }, new PayeeMapper());
		return payee;
	}
	
	public List<Payee> listPayees(Integer userid) {
		String SQL = "select * from Payee where userid = ?";
		List<Payee> payees = jdbcTemplateObject.query(SQL, new Object[] { userid },
				new PayeeMapper());
		return payees;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public void updateBalance(Integer id, double balance) {
		String SQL = "update Payee set balance = ? where id = ?";
		jdbcTemplateObject.update(SQL, balance, id);
		System.out.println("Updated Record with ID = " + id);
		return;
	}
	
	
}



