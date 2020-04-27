package JSP_Servlets;

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
		// get a connection
			Connection = dataSource.getConnection();
		
		// create a SQL statement
			String sql = "SELECT * FROM jsp_test.meetings;";
			
			Statement = Connection.createStatement();
		
		// execute query
			ResultSet = Statement.executeQuery(sql);
		
		// process result set
			while (ResultSet.next()) {
				// retrieve data from result set row
				int id = ResultSet.getInt("id");
				String name = ResultSet.getString("name");
				String date = ResultSet.getString("date_time").substring(0, 10);
				String time = ResultSet.getString("date_time").substring(11, 19);
				boolean display = ResultSet.getBoolean("display");
				
				// create new meetings object
				Meeting tempMeeting = new Meeting(id, name, date, time, display);
				
				// add it to the list of meetings
				meetings.add(tempMeeting);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close JDBC objects
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
			// get db connection
			Connection = dataSource.getConnection();

			// create sql for insert
			String sql = "INSERT INTO `jsp_test`.`meetings` (`name`, `date_time`, `display`) VALUES (?, ?, ?);";
			Statement = Connection.prepareStatement(sql);
			
			// set the param values for the meeting
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			
			Statement.setString(1, meeting.getName());
			Statement.setString(2, dateTime);
			if (meeting.isDisplay()) {
				Statement.setInt(3, 1);
			} else {
				Statement.setInt(3, 0);
			}
			
			System.out.println(meeting.getDate());
			System.out.println(Statement);
			
			// execute sql insert
			Statement.execute();
			
		}
		finally {
			// clean up JDBC object
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
			// convert meetingsId to int
			mettingsId = Integer.parseInt(meetingsId);
			
			// get connection to database
			Connection = dataSource.getConnection();
			
			// create sql to get selected meeting
			String sql = "SELECT * FROM jsp_test.meetings WHERE id = ?;";
			
			// create prepared statement
			Statement = Connection.prepareStatement(sql);
			
			// set params
			Statement.setInt(1, mettingsId);
			
			// execute statement
			ResultSet = Statement.executeQuery();
			
			// retrieve data from result set row
			if (ResultSet.next()) {
				int id = ResultSet.getInt("id");
				String name = ResultSet.getString("name");
				String date = ResultSet.getString("date_time").substring(0, 10);
				String time = ResultSet.getString("date_time").substring(11, 19);
				Boolean display = ResultSet.getBoolean("display");
				
				// use the meetingsId during construction
				meeting = new Meeting(id, name, date, time, display);
			} else {
				throw new Exception("Could not find meetings id: " + mettingsId);
			}
			return meeting;
		} finally {
			// clean up JDBC object
			close(Connection, Statement, ResultSet);
		}
	}

	public void updateMeeting(Meeting meeting) throws Exception{
		Connection Connectoion = null;
		PreparedStatement Statement = null;
		
			try {
			
			// get db connection
			Connectoion = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "UPDATE jsp_test.meetings SET name = ?, date_time = ?, display = ?, last_updated = now() WHERE id = ?";
			
			// prepare statement
			Statement = Connectoion.prepareStatement(sql);
			
			// set params
			String dateTime = meeting.getDate() + " " + meeting.getTime();
			Statement.setString(1, meeting.getName());
			Statement.setString(2, dateTime);
			Statement.setBoolean(3, meeting.isDisplay());
			Statement.setInt(4, meeting.getId());
			
			// execute SQL statement
			Statement.execute();
		} finally {
			// clean up JDBC objects
			close(Connectoion, Statement, null);
		}
		
	}

	public void deleteMeeting(String meeting) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		
		try {
			
			// convert meetings id to int
			int meetingsId = Integer.parseInt(meeting);
			
			// get connection to database
			Connection = dataSource.getConnection();
			
			// create sql to delete meeting
			String sql = "DELETE FROM jsp_test.meetings WHERE id = ?";
			
			// prepare statement
			Statement = Connection.prepareStatement(sql);
			
			// set params
			Statement.setInt(1, meetingsId);
			
			// execute sql statement
			Statement.execute();
			
		} finally {
			// clean up JDBC objects
			close(Connection, Statement, null);
		}
		
	}

	public void refreshMeetings(int value, int id) throws Exception{
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			
			// get connection to database
			Connection = dataSource.getConnection();
			
			// create SQL statement to get the value of display from the selected meeting
			String sql1 = "SELECT display FROM jsp_test.meetings WHERE id = ?;";
			
			// create prepared statement
			Statement = Connection.prepareStatement(sql1);
			
			// set params
			Statement.setInt(1, id);
			
			// execute statement
			ResultSet = Statement.executeQuery();
			
			// retrieve data from result set row
			if(ResultSet.next()) {
			int display = ResultSet.getInt("display");
			
			if (display != value) {
				// create SQL update statement
				String sql2 = "UPDATE jsp_test.meetings SET display = ?, last_updated = now() WHERE id = ?";
				
				// prepare statement
				Statement = Connection.prepareStatement(sql2);
				
				// set params
				Statement.setInt(1, value);
				Statement.setInt(2, id);
				
				// execute SQL statement
				Statement.execute();
			}
			
			} else {
				throw new Exception("Could not find meetings id: " + id);
			}
		} finally {
			// clean up JDBC object
			close(Connection, Statement, ResultSet);
		}
		
	}

}
