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
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
		
			String sql = "SELECT * FROM jsp_test.user;";
			
			statement = connection.createStatement();
		
			resultSet = statement.executeQuery(sql);
		
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String email= resultSet.getString("email");
				String company = resultSet.getString("company");
				
				User tempUser = new User(id, username, password, firstname, lastname, email, company);
				
				user.add(tempUser);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
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

	public boolean addUser(HttpServletResponse response, User user) {
		
		Connection connection = null;
		PreparedStatement statementSelect = null;
		PreparedStatement statementInsert = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();

			String sqlSelect1 = "SELECT `username` FROM `jsp_test`.`user` WHERE username = ?;";
			statementSelect = connection.prepareStatement(sqlSelect1);
			
			statementSelect.setString(1, user.getUsername());

			resultSet = statementSelect.executeQuery();
			
			if (!resultSet.next()) {
				String sqlInsert = "INSERT INTO `jsp_test`.`user` (`username`, `password`, `firstname`, `lastname`, `email`, `company`) VALUES (?, ?, ?, ?, ?, ?);";
				statementInsert = connection.prepareStatement(sqlInsert);
				
				statementInsert.setString(1, user.getUsername());
				statementInsert.setString(2, user.getPassword());
				statementInsert.setString(3, user.getFirstname());
				statementInsert.setString(4, user.getLastname());
				statementInsert.setString(5, user.getEmail());
				statementInsert.setString(6, user.getCompany());
				
				statementInsert.execute();

				User tempUser = getUser(user.getUsername());

				this.createCookies(response, tempUser);
				return true;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Es gibt bereits einen Benutzer mit diesem Namen: " + user.getUsername());
			e.printStackTrace();
		}
		finally {
			close(connection, statementSelect, resultSet);
			close(null, statementInsert, null);
		}
		return false;
	}

	public boolean loginUser(HttpServletResponse response, User user) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();

			String sqlSelect = "SELECT `password` FROM `jsp_test`.`user` WHERE username = ?;";
			statement = connection.prepareStatement(sqlSelect);
			
			statement.setString(1, user.getUsername());

			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				if (resultSet.getString("password").equals(user.getPassword())) {
					User tempUser = this.getUser(user.getUsername());
					this.createCookies(response, tempUser);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Es gibt keinen Benutzer mit diesem Namen: " + user.getUsername());
			e.printStackTrace();
		}
		finally {
			close(connection, statement, resultSet);
		}
		return false;
	}

	private void createCookies(HttpServletResponse response, User user) {
		
		Cookie userIdCookie = new Cookie("JSP.userId", user.getId() + "");
		Cookie userFirstnameCookie = new Cookie("JSP.userFirstname", user.getFirstname());
		Cookie userLastnameCookie = new Cookie("JSP.userLastname", user.getLastname());
		Cookie userIsAdminCookie = new Cookie("JSP.userIsAdmin", user.getIsAdmin());
		
		userIdCookie.setMaxAge(60*30);
		userFirstnameCookie.setMaxAge(60*30);
		userLastnameCookie.setMaxAge(60*30);
		userIsAdminCookie.setMaxAge(60*30);
		
		response.addCookie(userIdCookie);
		response.addCookie(userFirstnameCookie);
		response.addCookie(userLastnameCookie);
		response.addCookie(userIsAdminCookie);
		
	}
	
	private User getUser(String username) {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sqlSelect2 = "SELECT `id`, `firstname`, `lastname`, `is_admin` FROM `jsp_test`.`user` WHERE username = ?;";
			statement = connection.prepareStatement(sqlSelect2);
		
			statement.setString(1, username);
	
			resultSet = statement.executeQuery();
			if (resultSet.next()) {

				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String isAdmin = resultSet.getString("is_admin");
		
				User tempUser = new User(id, firstname, lastname, isAdmin);

				return tempUser;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("null wird zurück gegeben.");
		return null;
		
	}
}
