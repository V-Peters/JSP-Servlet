package meetingUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import meeting.Meeting;
import user.User;

public class MeetingUserDbUtil {
	
	private DataSource dataSource;
	
	public MeetingUserDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Meeting> getMeetings() {
		List<Meeting> meetings = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
		
			String sql = "SELECT * FROM jsp_test.meeting;";
			
			statement = connection.createStatement();
		
			resultSet = statement.executeQuery(sql);
		
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String date = resultSet.getString("date_time").substring(0, 10);
				String time = resultSet.getString("date_time").substring(11, 19);
				boolean display = resultSet.getBoolean("display");
				
				Meeting tempMeeting = new Meeting(id, name, date, time, display);
				
				meetings.add(tempMeeting);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return meetings;
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

	public void signUpUserForMeeting(int value, String userId, String meetingId) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sqlGet = "SELECT id FROM jsp_test.meeting_user WHERE id_user = ? AND id_meeting = ?;";
			
			statement = connection.prepareStatement(sqlGet);

			statement.setString(1, userId);
			statement.setString(2, meetingId);
			
			resultSet = statement.executeQuery();

			
			if(resultSet.next()) {
				if (value == 0) {
					String sqlDelete = "DELETE FROM jsp_test.meeting_user WHERE id = ?;";

					statement = connection.prepareStatement(sqlDelete);
					
					statement.setString(1, resultSet.getString("id"));

//					System.out.println(sqlDelete);
					
					statement.execute();
				}
			} else {
				if (value == 1) {
					String sqlInsert = "INSERT INTO `jsp_test`.`meeting_user` (`id_meeting`, `id_user`) VALUES (?, ?);";
					
					statement = connection.prepareStatement(sqlInsert);
					
					statement.setString(1, meetingId);
					statement.setString(2, userId);
					
					statement.execute();
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement, resultSet);
		}
		
	}

	public List<String> getMeetingForUser(String userId) {
		List<String> meetingIds = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sql = "SELECT id_meeting FROM jsp_test.meeting_user WHERE id_user = ? ORDER BY id_meeting ASC;";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, userId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				meetingIds.add(resultSet.getString("id_meeting"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement, resultSet);
		}
		return meetingIds;
	}

	public List<User> getUserForMeeting(String meetingId) {
		List<User> userIds = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statementSelectUserId = null;
		PreparedStatement statementSelectUser = null;
		ResultSet resultSetUserId = null;
		ResultSet resultSetUser = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sqlSelectUserId = "SELECT id_user FROM jsp_test.meeting_user WHERE id_meeting = ? ORDER BY id_user ASC;";
			
			statementSelectUserId = connection.prepareStatement(sqlSelectUserId);
			
			statementSelectUserId.setString(1, meetingId);
			
			resultSetUserId = statementSelectUserId.executeQuery();
			
			while(resultSetUserId.next()) {
				
				String sqlSelectUser = "SELECT firstname, lastname, email, company FROM jsp_test.user WHERE id = ?;";
				
				statementSelectUser = connection.prepareStatement(sqlSelectUser);
				
				statementSelectUser.setString(1, resultSetUserId.getString("id_user"));
				
				resultSetUser = statementSelectUser.executeQuery();
				
				while (resultSetUser.next()) {
					
					String firstname = resultSetUser.getString("firstname");
					String lastname = resultSetUser.getString("lastname");
					String email = resultSetUser.getString("email");
					String company = resultSetUser.getString("company");

					User tempUser = new User(firstname, lastname, email, company);
					
					userIds.add(tempUser);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statementSelectUserId, resultSetUserId);
			close(null, statementSelectUser, resultSetUser);
		}
		return userIds;
	}

}
