package meetings;

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
		
		Connection Connection = null;
		Statement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			Connection = dataSource.getConnection();
		
			String sql = "SELECT * FROM jsp_test.meetings;";
			
			Statement = Connection.createStatement();
		
			ResultSet = Statement.executeQuery(sql);
		
			while (ResultSet.next()) {
				int id = ResultSet.getInt("id");
				String name = ResultSet.getString("name");
				String date = ResultSet.getString("date_time").substring(0, 10);
				String time = ResultSet.getString("date_time").substring(11, 19);
				boolean display = ResultSet.getBoolean("display");
				
				Meeting tempMeeting = new Meeting(id, name, date, time, display);
				
				meetings.add(tempMeeting);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(Connection, Statement, ResultSet);
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

	public void addMeeting(Meeting meeting) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		
		try {
			Connection = dataSource.getConnection();

			String sql = "INSERT INTO `jsp_test`.`meetings` (`name`, `date_time`, `display`) VALUES (?, ?, ?);";
			Statement = Connection.prepareStatement(sql);
			
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			
			Statement.setString(1, meeting.getName());
			Statement.setString(2, dateTime);
			if (meeting.isDisplay()) {
				Statement.setInt(3, 1);
			} else {
				Statement.setInt(3, 0);
			}
			
			Statement.execute();
			
		}
		finally {
			close(Connection, Statement, null);
		}
		
	}

	public Meeting getMeeting(String meetingsId) throws Exception{
		Meeting meeting = null;
	
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		int mettingsId;
		
		try {
			mettingsId = Integer.parseInt(meetingsId);
			
			Connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM jsp_test.meetings WHERE id = ?;";
			
			Statement = Connection.prepareStatement(sql);
			
			Statement.setInt(1, mettingsId);
			
			ResultSet = Statement.executeQuery();
			
			if (ResultSet.next()) {
				int id = ResultSet.getInt("id");
				String name = ResultSet.getString("name");
				String date = ResultSet.getString("date_time").substring(0, 10);
				String time = ResultSet.getString("date_time").substring(11, 19);
				Boolean display = ResultSet.getBoolean("display");
				
				meeting = new Meeting(id, name, date, time, display);
			} else {
				throw new Exception("Could not find meetings id: " + mettingsId);
			}
			return meeting;
		} finally {
			close(Connection, Statement, ResultSet);
		}
	}

	public void updateMeeting(Meeting meeting) throws Exception{
		Connection Connectoion = null;
		PreparedStatement Statement = null;
		
			try {
			
			Connectoion = dataSource.getConnection();
			
			String sql = "UPDATE jsp_test.meetings SET name = ?, date_time = ?, display = ?, last_updated = now() WHERE id = ?";
			
			Statement = Connectoion.prepareStatement(sql);
			
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			Statement.setString(1, meeting.getName());
			Statement.setString(2, dateTime);
			Statement.setBoolean(3, meeting.isDisplay());
			Statement.setInt(4, meeting.getId());
			
			Statement.execute();
		} finally {
			close(Connectoion, Statement, null);
		}
		
	}

	public void deleteMeeting(String meeting) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		
		try {
			
			int meetingsId = Integer.parseInt(meeting);
			
			Connection = dataSource.getConnection();
			
			String sql = "DELETE FROM jsp_test.meetings WHERE id = ?";
			
			Statement = Connection.prepareStatement(sql);
			
			Statement.setInt(1, meetingsId);
			
			Statement.execute();
			
		} finally {
			close(Connection, Statement, null);
		}
		
	}

	public void refreshMeetings(int value, int id) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			
			Connection = dataSource.getConnection();
			
			String sql1 = "SELECT display FROM jsp_test.meetings WHERE id = ?;";
			
			Statement = Connection.prepareStatement(sql1);
			
			Statement.setInt(1, id);
			
			ResultSet = Statement.executeQuery();
			
			if(ResultSet.next()) {
				int display = ResultSet.getInt("display");
				
				if (display != value) {
					String sql2 = "UPDATE jsp_test.meetings SET display = ?, last_updated = now() WHERE id = ?";
					
					Statement = Connection.prepareStatement(sql2);
					
					Statement.setInt(1, value);
					Statement.setInt(2, id);
					
					Statement.execute();
				}
				
			} else {
				throw new Exception("Could not find meetings id: " + id);
			}
		} finally {
			close(Connection, Statement, ResultSet);
		}
		
	}

}
