package user;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private String company;
	private String isAdmin;
	
	public User(String username, String password, String firstname, String lastname, String email, String company) {
		this(firstname, lastname, email, company);
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String firstname, String lastname, String isAdmin) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isAdmin = isAdmin;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(String firstname, String lastname, String email, String company) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.company = company;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
