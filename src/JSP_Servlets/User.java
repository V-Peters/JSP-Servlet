package JSP_Servlets;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String vorname;
	private String lastname;
	private String email;
	private String company;
	
	
	public User(int id, String username, String password, String vorname, String lastname, String email, String company) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.vorname = vorname;
		this.lastname = lastname;
		this.email = email;
		this.company = company;
	}
	public User(String username, String password, String vorname, String lastname, String email, String company) {
		this.username = username;
		this.password = password;
		this.vorname = vorname;
		this.lastname = lastname;
		this.email = email;
		this.company = company;
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
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
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lasrname) {
		this.lastname = lasrname;
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
	
}
