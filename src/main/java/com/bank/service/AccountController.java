
package bank.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bank.bo.Account;

@RestController
@RequestMapping("/account")
public class AccountController {
	// private PayeeDaoJDBCTemplate payeeDaoJDBCTemplate;
	@Autowired
	private AccountJdbcTemplate accountDaoJDBCTemplate;
	protected static final ConcurrentHashMap<Integer, Date> loginmap = new ConcurrentHashMap<Integer, Date>();
	
	@RequestMapping("/register")
	public Account add(@RequestParam(value = "name") String name, @RequestParam(value = "cellnumber") String cellnumber, @RequestParam(value = "email", required = false) String email) {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// AccountJdbcTemplate accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// appContext.getBean("accountDaoJDBCTemplate");
		// AutowireCapableBeanFactory beanFactory =
		// context.getAutowireCapableBeanFactory();
		// beanFactory.autowireBean(accountDaoJDBCTemplate);
		// accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// ApplicationContextProvider.getApplicationContext().getBean("accountDaoJDBCTemplate");
		System.out.println(accountDaoJDBCTemplate);
		// AccountJdbcTemplate accountDaoJDBCTemplate = new
		// AccountJdbcTemplate();
		accountDaoJDBCTemplate.create(name, cellnumber, email);
		Account account = accountDaoJDBCTemplate.getAccount(cellnumber);
		String token = account.generateLoginToken(cellnumber);
		accountDaoJDBCTemplate.setPassword(token, account.getId());
		return accountDaoJDBCTemplate.getAccount(account.getId()).setMessage("Register Success!Use id  and password to login").setPassword(token);
		//		return account.setMessage("Register Success!Use id to login");
		
	}
	@RequestMapping("/{userid}/deposit")
	public Object deposit(@PathVariable Integer userid, @RequestParam(value = "amount") double amount) {
		
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// AccountJdbcTemplate accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// context.getBean("accountDaoJDBCTemplate");
		Account account = accountDaoJDBCTemplate.getAccount(userid);
		accountDaoJDBCTemplate.updateBalance(userid, account.getBalance() + amount);
		account = accountDaoJDBCTemplate.getAccount(userid);
		return account;
	}
	
	public AccountJdbcTemplate getAccountDaoJDBCTemplate() {
		return accountDaoJDBCTemplate;
	}
	
	@RequestMapping("/all")
	public Object getAllAccounts() {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// AccountJdbcTemplate accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// context.getBean("accountDaoJDBCTemplate");
		List<Account> accounts = accountDaoJDBCTemplate.listAccounts();
		return accounts;
	}
	
	@RequestMapping("/{userid}/login")
	public Account login(@PathVariable Integer userid, @RequestParam(value = "pass") String passwd) {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// AccountJdbcTemplate accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// context.getBean("accountDaoJDBCTemplate");
		System.out.println(userid);
		System.out.println(passwd);
		Account account = accountDaoJDBCTemplate.getAccount(userid, passwd);
		if (null != account) {
			loginmap.put(account.getId(), new Date());
			account.setMessage("login Success!");
		}
		return account;
		// return new Account();
		
	}
	
	@RequestMapping("/{userid}/logout")
	public Object logout(@PathVariable Integer userid) {
		
		if (AccountController.loginmap.get(userid) == null) {
			return "Please login first." + "\n" + "Login Url=/account/{userid}/login?pass={pass}";
		}
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// AccountJdbcTemplate accountDaoJDBCTemplate = (AccountJdbcTemplate)
		// context.getBean("accountDaoJDBCTemplate");
		Account account = accountDaoJDBCTemplate.getAccount(userid);
		if (null == account.getMessage()) {
			loginmap.remove(userid);
			return account.setMessage("Logout Success!");
		}
		return account;
	}
	
	// public void setAccountDaoJDBCTemplate(AccountJdbcTemplate
	// accountDaoJDBCTemplate) {
	// this.accountDaoJDBCTemplate = accountDaoJDBCTemplate;
	// }
	
	
	
	
}

