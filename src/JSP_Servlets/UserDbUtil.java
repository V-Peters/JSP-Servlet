package JSP_Servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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
		// get a connection
			Connection = dataSource.getConnection();
		
		// create a SQL statement
			String sql = "SELECT * FROM jsp_test.user;";
			
			Statement = Connection.createStatement();
		
		// execute query
			ResultSet = Statement.executeQuery(sql);
		
		// process result set
			while (ResultSet.next()) {
				// retrieve data from result set row
				int id = ResultSet.getInt("id");
				String username = ResultSet.getString("username");
				String password = ResultSet.getString("password");
				String vorname = ResultSet.getString("vorname");
				String lastname = ResultSet.getString("lastname");
				String email= ResultSet.getString("email");
				String company = ResultSet.getString("company");
				
				// create new meetings object
				User tempUser = new User(id, username, password, vorname, lastname, email, company);
				
				// add it to the list of users
				user.add(tempUser);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close JDBC objects
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
			// get db connection
			Connection = dataSource.getConnection();

			// create sql for insert
			String sqlSelect = "SELECT `username` FROM `jsp_test`.`user` WHERE username = ?;";
			Statement = Connection.prepareStatement(sqlSelect);
			
			// set the param values for the user
			Statement.setString(1, user.getUsername());

			// execute sql insert
			ResultSet = Statement.executeQuery();
			
			if (!ResultSet.next()) {
				// create sql for insert
				String sqlInsert = "INSERT INTO `jsp_test`.`user` (`username`, `password`, `vorname`, `lastname`, `email`, `company`) VALUES (?, ?, ?, ?, ?, ?);";
				Statement = Connection.prepareStatement(sqlInsert);
				
				// set the param values for the user
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
			// clean up JDBC object
			close(Connection, Statement, null);
		}
		
	}

	public int loginUser(User user) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		int userId = 0;
		
		try {
			// get db connection
			Connection = dataSource.getConnection();

			// create sql for insert
			String sqlSelect = "SELECT `username` FROM `jsp_test`.`user` WHERE username = ?;";
			Statement = Connection.prepareStatement(sqlSelect);
			
			// set the param values for the user
			Statement.setString(1, user.getUsername());

			// execute sql insert
			ResultSet = Statement.executeQuery();
			
			if (ResultSet.next()) {
				if (ResultSet.getString("username") == user.getUsername() && ResultSet.getString("password") == user.getPassword()) {
					userId = ResultSet.getInt("id");
				}
			} else {
				throw new Exception("Es gibt keinen Benutzer mit diesem Namen: " + user.getUsername());
			}
			return userId;
		}
		finally {
			// clean up JDBC object
			close(Connection, Statement, null);
		}
	}

}
