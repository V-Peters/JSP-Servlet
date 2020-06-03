package meeting;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import meetingUser.MeetingUserDbUtil;
import navigation.NavigationUtil;
import navigation.ValidPath;
import user.User;
import user.UserServiceClass;

/**
 * Servlet implementation class MeetingControllerServlet
 */
@WebServlet("/MeetingControllerServlet")
public class MeetingControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LISTMEETINGS = "LISTMEETINGS";
	private static final String ADDMEETING = "ADDMEETING";
	private static final String LOADMEETING = "LOADMEETING";
	private static final String UPDATEMEETING = "UPDATEMEETING";
	private static final String DELETEMEETING = "DELETEMEETING";
	private static final String REFRESHMEETINGS = "REFRESHMEETINGS";
	private static final String LISTPARTICIPANTS = "LISTPARTICIPANTS";
	
	private MeetingServiceClass meetingServiceClass;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() {

		MeetingDbUtil meetingDbUtil;
		MeetingUserDbUtil meetingUserDbUtil;
		
		try {
			super.init();
		
			meetingDbUtil = new MeetingDbUtil(dataSource);
			meetingUserDbUtil = new MeetingUserDbUtil(dataSource);
			meetingServiceClass = new MeetingServiceClass(meetingDbUtil, meetingUserDbUtil);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String command = request.getParameter("command");
			String meetingId = request.getParameter("meetingId");
			User user;
			
			if (command == null) {
				command = LISTMEETINGS;
			}
			
			user = UserServiceClass.getCookie(request);
			
			if (user.getId() != 0) {
				UserServiceClass.createCookies(response, user, 30 * 60);
			} else {
				NavigationUtil.navigate(response, ValidPath.SESSION_TIMED_OUT);
				return;
			}
			
			switch (command) {
				case LISTMEETINGS:
					meetingServiceClass.listMeetings(request, response, user.getId() + "");
					break;
				case ADDMEETING:
					meetingServiceClass.addMeeting(request, response);
					break;
				case LOADMEETING:
					meetingServiceClass.loadMeeting(request, response);
					break;
				case UPDATEMEETING:
					meetingServiceClass.updateMeeting(request, response);
					break;
				case DELETEMEETING:
					meetingServiceClass.deleteMeeting(request, response);
					break;
				case REFRESHMEETINGS:
					meetingServiceClass.refreshMeetings(request, response);
					break;
				case LISTPARTICIPANTS:
					meetingServiceClass.listParticipants(request, response, meetingId);
					break;
	
				default:
					meetingServiceClass.listMeetings(request, response, user.getId() + "");
					break;
			}
			
		} catch (Exception e) {
			NavigationUtil.navigate(response, ValidPath.ERROR_PARAMETER);
			e.printStackTrace();
		}
		
	}

}
