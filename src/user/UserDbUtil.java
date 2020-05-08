package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.ws.Response;

public class UserDbUtil {
	
	private DataSource dataSource;
	
	public UserDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<User> getUser() {
		List<User> user = new ArrayList<>();
		
		Connection Connection = null;
		Statement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			Connection = dataSource.getConnection();
		
			String sql = "SELECT * FROM jsp_test.user;";
			
			Statement = Connection.createStatement();
		
			ResultSet = Statement.executeQuery(sql);
		
			while (ResultSet.next()) {
				int id = ResultSet.getInt("id");
				String username = ResultSet.getString("username");
				String password = ResultSet.getString("password");
				String vorname = ResultSet.getString("vorname");
				String lastname = ResultSet.getString("lastname");
				String email= ResultSet.getString("email");
				String company = ResultSet.getString("company");
				
				User tempUser = new User(id, username, password, vorname, lastname, email, company);
				
				user.add(tempUser);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(Connection, Statement, ResultSet);
		}
		return user;
	}

	private void close(Connection connection, Statement statemet, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statemet != null) {
				statemet.close();
			}
			if (connection != null) {
				connection.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUser(User user) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			Connection = dataSource.getConnection();

			String sqlSelect = "SELECT `username` FROM `jsp_test`.`user` WHERE username = ?;";
			Statement = Connection.prepareStatement(sqlSelect);
			
			Statement.setString(1, user.getUsername());

			ResultSet = Statement.executeQuery();
			
			if (!ResultSet.next()) {
				String sqlInsert = "INSERT INTO `jsp_test`.`user` (`username`, `password`, `vorname`, `lastname`, `email`, `company`) VALUES (?, ?, ?, ?, ?, ?);";
				Statement = Connection.prepareStatement(sqlInsert);
				
				Statement.setString(1, user.getUsername());
				Statement.setString(2, user.getPassword());
				Statement.setString(3, user.getVorname());
				Statement.setString(4, user.getLastname());
				Statement.setString(5, user.getEmail());
				Statement.setString(6, user.getCompany());
				
				Statement.execute();
			} else {
				throw new Exception("Es gibt bereits einen Benutzer mit diesem Namen: " + user.getUsername());
			}
			
		}
		finally {
			close(Connection, Statement, null);
		}
		
	}

	public boolean loginUser(HttpServletResponse response, User user) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			Connection = dataSource.getConnection();

			String sqlSelect = "SELECT `password` FROM `jsp_test`.`user` WHERE username = \"" + user.getUsername() + "\";";
//			System.out.println(sqlSelect);
			Statement = Connection.prepareStatement(sqlSelect);
			
//			Statement.setString(1, user.getUsername());

			ResultSet = Statement.executeQuery();
			
			if (ResultSet.next()) {
//				System.out.println(ResultSet.getString("password"));
//				System.out.println(user.getPassword());
//				System.out.println(ResultSet.getString("password"));
//				System.out.println(user.getPassword());
				if (ResultSet.getString("password").equals(user.getPassword())) {
					this.createCookie(response, user);
					return true;
				}
			} else {
				throw new Exception("Es gibt keinen Benutzer mit diesem Namen: " + user.getUsername());
			}
			return false;
		}
		finally {
			close(Connection, Statement, null);
		}
	}

	private void createCookie(HttpServletResponse response, User user) throws Exception {
		Cookie userCookie = new Cookie("JSP.userId", this.getUserId(user));
		userCookie.setMaxAge(60*30);
		response.addCookie(userCookie);
	}

	public String getUserId(User user) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		String userId;
		
		try {
			Connection = dataSource.getConnection();

			String sqlSelect = "SELECT `id` FROM `jsp_test`.`user` WHERE `username` = ?;";
			Statement = Connection.prepareStatement(sqlSelect);

			Statement.setString(1, user.getUsername());

			ResultSet = Statement.executeQuery();
			
			if (ResultSet.next()) {
				userId = ResultSet.getString("id");
			} else {
				throw new Exception("Es gibt keinen Benutzer mit diesem Namen: " + user.getUsername());
			}
			return userId;
		}
		finally {
			close(Connection, Statement, null);
		}
	}

}
