package meetingsUserConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import meetings.Meeting;

public class MeetingUserConnectionDbUtil {
	
	private DataSource dataSource;
	
	public MeetingUserConnectionDbUtil(DataSource dataSource) {
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

	public void signUpUserForMeeting(int value, String userId, String meetingId) throws Exception{

//		System.out.println("value: " + value);
//		System.out.println("userId: " + userId);
//		System.out.println("meetingId: " + meetingId);
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			
			Connection = dataSource.getConnection();
			
			String sqlGet = "SELECT id FROM jsp_test.meetings_user_connection WHERE id_user = ? AND id_meeting = ?;";
			
			Statement = Connection.prepareStatement(sqlGet);

			Statement.setString(1, userId);
			Statement.setString(2, meetingId);
			
			ResultSet = Statement.executeQuery();

			
			if(ResultSet.next()) {
				if (value == 0) {
					String sqlDelete = "DELETE FROM jsp_test.meetings_user_connection WHERE id = ?;";

					Statement = Connection.prepareStatement(sqlDelete);
					
					Statement.setString(1, ResultSet.getString("id"));

					System.out.println(sqlDelete);
					
					Statement.execute();
				}
			} else {
				if (value == 1) {
//					System.out.println("es gibt keinen eintrag");
					

//					if (display != value) {
//						String sql2 = "UPDATE jsp_test.meetings SET display = ?, last_updated = now() WHERE id = ?";
//						
//						Statement = Connection.prepareStatement(sql2);
//						
//						Statement.setInt(1, value);
//						Statement.setInt(2, id);
//						
//						Statement.execute();
//					}
					
					String sqlInsert = "INSERT INTO `jsp_test`.`meetings_user_connection` (`id_meeting`, `id_user`) VALUES (?, ?);";
					
					Statement = Connection.prepareStatement(sqlInsert);
					
					Statement.setString(1, meetingId);
					Statement.setString(2, userId);
					
					Statement.execute();
					
				}
//				throw new Exception("Could not find user id: " + userId);
			}
		} finally {
			close(Connection, Statement, ResultSet);
		}
		
	}

	public List<String> getMeetingUserConnection(String userId) throws Exception {
		List<String> userIds = new ArrayList<>();
		
		Connection Connection = null;
		PreparedStatement Statement = null;
		ResultSet ResultSet = null;
		
		try {
			
			Connection = dataSource.getConnection();
			
			String sql = "SELECT id_meeting FROM jsp_test.meetings_user_connection WHERE id_user = ? ORDER BY id_meeting ASC;";
			
			Statement = Connection.prepareStatement(sql);
			
			Statement.setString(1, userId);
			
			ResultSet = Statement.executeQuery();
			
			while(ResultSet.next()) {
				userIds.add(ResultSet.getString("id_meeting"));
			}
		} finally {
			close(Connection, Statement, ResultSet);
		}
		return userIds;
	}

}
