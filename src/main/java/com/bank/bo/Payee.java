package bank.bo;
public class Payee {
	
	private int id;
	private String name;
	private String cellnumber;
	private String email;
	private String userid;
	private String message;
	
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
	
	public String getUserid() {
		return userid;
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
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	
	
	
	
}
