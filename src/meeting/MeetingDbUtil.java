package meeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import direction.DirectionUtil;

public class MeetingDbUtil {
	
	private DataSource dataSource;
	private DirectionUtil directionUtil;
	
	public MeetingDbUtil(DataSource dataSource) {
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

	public void addMeeting(Meeting meeting, HttpServletResponse response) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();

			String sql = "INSERT INTO `jsp_test`.`meeting` (`name`, `date_time`, `display`) VALUES (?, ?, ?);";
			statement = connection.prepareStatement(sql);
			
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			
			statement.setString(1, meeting.getName());
			statement.setString(2, dateTime);
			if (meeting.isDisplay()) {
				statement.setInt(3, 1);
			} else {
				statement.setInt(3, 0);
			}
			
			statement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, null, response);
		}
		
	}

	public Meeting getMeeting(String meetingsId, HttpServletResponse response) {
		Meeting meeting = null;
	
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int mettingsId;
		
		try {
			mettingsId = Integer.parseInt(meetingsId);
			
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM jsp_test.meeting WHERE id = ?;";
			
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, mettingsId);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String date = resultSet.getString("date_time").substring(0, 10);
				String time = resultSet.getString("date_time").substring(11, 19);
				Boolean display = resultSet.getBoolean("display");
				
				meeting = new Meeting(id, name, date, time, display);
			} else {
				System.out.println("Could not find meeting id: " + mettingsId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, resultSet, response);
		}
		return meeting;
	}

	public void updateMeeting(Meeting meeting, HttpServletResponse response) {
		
		Connection connectoion = null;
		PreparedStatement statement = null;
		
			try {
			
			connectoion = dataSource.getConnection();
			
			String sql = "UPDATE jsp_test.meeting SET name = ?, date_time = ?, display = ?, last_updated = now() WHERE id = ?";
			
			statement = connectoion.prepareStatement(sql);
			
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			statement.setString(1, meeting.getName());
			statement.setString(2, dateTime);
			statement.setBoolean(3, meeting.isDisplay());
			statement.setInt(4, meeting.getId());
			
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connectoion, statement, null, response);
		}
		
	}

	public void deleteMeeting(String meeting, HttpServletResponse response) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			
			int meetingsId = Integer.parseInt(meeting);
			
			connection = dataSource.getConnection();
			
			String sql = "DELETE FROM jsp_test.meeting WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, meetingsId);
			
			statement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statement, null, response);
		}
		
	}

	public void refreshMeetings(int value, int id, HttpServletResponse response) {
		
		Connection connection = null;
		PreparedStatement statementSelect = null;
		PreparedStatement statementUpdate = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sql1 = "SELECT display FROM jsp_test.meeting WHERE id = ?;";
			
			statementSelect = connection.prepareStatement(sql1);
			
			statementSelect.setInt(1, id);
			
			resultSet = statementSelect.executeQuery();
			
			if(resultSet.next()) {
				int display = resultSet.getInt("display");
				
				if (display != value) {
					String sql2 = "UPDATE jsp_test.meeting SET display = ?, last_updated = now() WHERE id = ?";
					
					statementUpdate = connection.prepareStatement(sql2);
					
					statementUpdate.setInt(1, value);
					statementUpdate.setInt(2, id);
					
					statementUpdate.execute();
				}
				
			} else {
				System.out.println("Could not find meeting id: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			directionUtil.databaseErrorDirect(response);
		} finally {
			close(connection, statementSelect, resultSet, response);
			close(null, statementUpdate, null, response);
		}
		
	}

}
