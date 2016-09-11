package bank.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import bank.bo.Payee;
public class PayeeMapper implements RowMapper<Payee> {
	@Override
	public Payee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Payee payee = new Payee();
		payee.setId(rs.getInt("id"));
		payee.setName(rs.getString("name"));
		payee.setCellnumber(rs.getString("cellnumber"));
		payee.setEmail(rs.getString("email"));
		payee.setUserid(rs.getString("userid"));
		return payee;
	}
}
