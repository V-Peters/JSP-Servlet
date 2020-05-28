package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import direction.DirectionUtil;

public class UserDbUtil {

	private DataSource dataSource;
	private DirectionUtil directionUtil;
	
	public UserDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
		directionUtil = new DirectionUtil();
	}
	
	private void close(Connection connection, Statement statemet, ResultSet resultSet, HttpServletResponse response) {
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
			directionUtil.closeErrorDirect(response);
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

				User tempUser = getUser(user.getUsername(), response);

				this.createCookies(response, tempUser, 60 * 30);
				return true;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		}
		finally {
			close(connection, statementSelect, resultSet, response);
			close(null, statementInsert, null, response);
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
					User tempUser = this.getUser(user.getUsername(), response);
					this.createCookies(response, tempUser, 60 * 30);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		}
		finally {
			close(connection, statement, resultSet, response);
		}
		return false;
	}

	public void createCookies(HttpServletResponse response, User user, int timer) {

		Cookie userIdCookie = new Cookie("JSP.userId", user.getId() + "");
		Cookie userFirstnameCookie = new Cookie("JSP.userFirstname", user.getFirstname());
		Cookie userLastnameCookie = new Cookie("JSP.userLastname", user.getLastname());
		Cookie userIsAdminCookie = new Cookie("JSP.userIsAdmin", user.getIsAdmin());

		userIdCookie.setMaxAge(timer);
		userFirstnameCookie.setMaxAge(timer);
		userLastnameCookie.setMaxAge(timer);
		userIsAdminCookie.setMaxAge(timer);

		response.addCookie(userIdCookie);
		response.addCookie(userFirstnameCookie);
		response.addCookie(userLastnameCookie);
		response.addCookie(userIsAdminCookie);
		
	}
	
	private User getUser(String username, HttpServletResponse response) {

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
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		}
		System.out.println("null wird zurück gegeben.");
		return null;
		
	}
}
