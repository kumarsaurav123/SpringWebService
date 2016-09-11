

package bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.bo.Account;
import bank.bo.Payee;

@RestController
@RequestMapping("/account/{userid}/payee")
public class PayeeController {
	@Autowired
	private PayeeDaoJDBCTemplate payeeDaoJDBCTemplate;
	@Autowired
	private AccountJdbcTemplate accountDaoJDBCTemplate;
	
	@RequestMapping(value = "/add")
	public Object add(@RequestParam(value = "name") String name, @RequestParam(value = "cellnumber") String cellnumber, @RequestParam(value = "email", required = false) String email,
			@PathVariable Integer userid) {
		
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// payeeDaoJDBCTemplate = (PayeeDaoJDBCTemplate)
		// appContext.getBean("PayeeDaoJDBCTemplate");
		payeeDaoJDBCTemplate.create(name, cellnumber, email, userid);
		Payee payee = payeeDaoJDBCTemplate.getPayee(cellnumber, userid);
		payee.setMessage("Payee Added!Use id to transact");
		return payee;
	}
	
	@RequestMapping("/{payeeid}/delete")
	public Object delete(@PathVariable Integer payeeid, @PathVariable Integer userid) {
		Payee payee = null;
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// payeeDaoJDBCTemplate = (PayeeDaoJDBCTemplate)
		// context.getBean("PayeeDaoJDBCTemplate");
		payee = payeeDaoJDBCTemplate.getPayee(payeeid, userid);
		payeeDaoJDBCTemplate.delete(payeeid);
		payee.setMessage("Deleted!");
		return payee;
	}
	
	@RequestMapping("/{payeeid}")
	public Object get(@PathVariable Integer payeeid, @PathVariable Integer userid) {
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// payeeDaoJDBCTemplate = (PayeeDaoJDBCTemplate)
		// context.getBean("PayeeDaoJDBCTemplate");
		Payee payee = payeeDaoJDBCTemplate.getPayee(payeeid, userid);
		payee.setMessage("Payee Found!Use id to transact");
		return payee;
	}
	
	@RequestMapping("/{payeeid}/pay")
	public Object get(@PathVariable Integer payeeid, @PathVariable Integer userid, @RequestParam double amount) {
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// context.getBean("accountDaoJDBCTemplate");
		Account account = accountDaoJDBCTemplate.getAccount(userid);
		if (account.getBalance() < amount) {
			return "Insufficient Balance";
		}
		accountDaoJDBCTemplate.updateBalance(userid, account.getBalance() - amount);
		account = accountDaoJDBCTemplate.getAccount(userid);
		account.setMessage("Payment successfull");
		return account;
	}
	
	public AccountJdbcTemplate getAccountDaoJDBCTemplate() {
		return accountDaoJDBCTemplate;
	}
	
	@RequestMapping("/all")
	public Object getAllPayee(@PathVariable Integer userid) {
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// payeeDaoJDBCTemplate = (PayeeDaoJDBCTemplate)
		// context.getBean("PayeeDaoJDBCTemplate");
		List<Payee> payees = payeeDaoJDBCTemplate.listPayees(userid);
		return payees;
	}
	
	
	
	
	
}




