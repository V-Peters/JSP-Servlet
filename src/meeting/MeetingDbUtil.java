package meeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class MeetingDbUtil {
	
	private DataSource dataSource;
	
	public MeetingDbUtil(DataSource dataSource) {
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

	public void addMeeting(Meeting meeting) {
		
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
			// TODO: handle exception
		} finally {
			close(connection, statement, null);
		}
		
	}

	public Meeting getMeeting(String meetingsId) {
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
				throw new Exception("Could not find meeting id: " + mettingsId);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement, resultSet);
		}
		return meeting;
	}

	public void updateMeeting(Meeting meeting) {
		
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
			// TODO: handle exception
		} finally {
			close(connectoion, statement, null);
		}
		
	}

	public void deleteMeeting(String meeting) {
		
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
			// TODO: handle exception
		} finally {
			close(connection, statement, null);
		}
		
	}

	public void refreshMeetings(int value, int id) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			String sql1 = "SELECT display FROM jsp_test.meeting WHERE id = ?;";
			
			statement = connection.prepareStatement(sql1);
			
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				int display = resultSet.getInt("display");
				
				if (display != value) {
					String sql2 = "UPDATE jsp_test.meeting SET display = ?, last_updated = now() WHERE id = ?";
					
					statement = connection.prepareStatement(sql2);
					
					statement.setInt(1, value);
					statement.setInt(2, id);
					
					statement.execute();
				}
				
			} else {
				System.out.println("Could not find meeting id: " + id);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(connection, statement, resultSet);
		}
		
	}

}
