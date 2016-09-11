package bank.bo;

import org.apache.tomcat.util.codec.binary.Base64;

public class Account {
	
	public static String decocodepass(String pass) {
		byte[] valueDecoded = Base64.decodeBase64(pass.getBytes());
		return new String(valueDecoded);
	}
	private int id;
	private String name;
	private String cellnumber;
	private String email;
	private String message;
	private String password;
	private double balance;
	
	public void debit(double amount) {
		synchronized (this) {
			this.balance=balance-amount;
		}
		
	}
	
	public String generateLoginToken(String pass) {
		
		String data = pass;
		byte[] valueDecoded = Base64.encodeBase64(data.getBytes());
		String password = new String(valueDecoded);
		
		return password;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getCellnumber() {
		return cellnumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setCellnumber(String cellnumber) {
		this.cellnumber = cellnumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Account setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Account setPassword(String password) {
		this.password = password;
		return this;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", cellnumber=" + cellnumber +"]";
	}
	
}
