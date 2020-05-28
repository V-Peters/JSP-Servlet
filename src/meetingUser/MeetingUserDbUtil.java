package meetingUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import direction.DirectionUtil;
import meeting.Meeting;
import user.User;

public class MeetingUserDbUtil {
	
	private DataSource dataSource;
	private DirectionUtil directionUtil;
	
	public MeetingUserDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
		directionUtil = new DirectionUtil();
	}
	
	public List<Meeting> getMeetings(HttpServletResponse response) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, resultSet, response);
		}
		return meetings;
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

	public void signUpUserForMeeting(int value, String userId, String meetingId, HttpServletResponse response) {
		
		Connection connection = null;
		PreparedStatement statementSelect = null;
		PreparedStatement statementDelete = null;
		PreparedStatement statementInsert = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sqlGet = "SELECT id FROM jsp_test.meeting_user WHERE id_user = ? AND id_meeting = ?;";
			
			statementSelect = connection.prepareStatement(sqlGet);

			statementSelect.setString(1, userId);
			statementSelect.setString(2, meetingId);
			
			resultSet = statementSelect.executeQuery();

			
			if(resultSet.next()) {
				if (value == 0) {
					String sqlDelete = "DELETE FROM jsp_test.meeting_user WHERE id = ?;";

					statementDelete = connection.prepareStatement(sqlDelete);
					
					statementDelete.setString(1, resultSet.getString("id"));

					statementDelete.execute();
				}
			} else {
				if (value == 1) {
					String sqlInsert = "INSERT INTO `jsp_test`.`meeting_user` (`id_meeting`, `id_user`) VALUES (?, ?);";
					
					statementInsert = connection.prepareStatement(sqlInsert);
					
					statementInsert.setString(1, meetingId);
					statementInsert.setString(2, userId);
					
					statementInsert.execute();
					
				}
			}
		} catch (Exception e) {
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statementSelect, resultSet, response);
			close(null, statementDelete, null, response);
			close(null, statementInsert, null, response);
		}
		
	}

	public List<String> getMeetingForUser(String userId, HttpServletResponse response) {
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
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, resultSet, response);
		}
		return meetingIds;
	}

	public List<User> getUserForMeeting(String meetingId, HttpServletResponse response) {
		List<User> userIds = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {

			connection = dataSource.getConnection();
			
			String sql = "SELECT firstname, lastname, email, company FROM jsp_test.user JOIN jsp_test.meeting_user ON jsp_test.user.id = jsp_test.meeting_user.id_user WHERE id_meeting = ?;";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, meetingId);
			
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String email = resultSet.getString("email");
				String company = resultSet.getString("company");

				User tempUser = new User(firstname, lastname, email, company);
				
				userIds.add(tempUser);
			}
			
		} catch (Exception e) {
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, resultSet, response);
		}
		return userIds;
	}

}
