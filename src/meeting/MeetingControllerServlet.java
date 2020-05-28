package meeting;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import direction.DirectionUtil;
import meetingUser.MeetingUserDbUtil;
import user.User;

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

	private MeetingDbUtil meetingDbUtil;
	private MeetingUserDbUtil meetingUserDbUtil;
	private DirectionUtil directionUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() {
		try {
			super.init();
		
			meetingDbUtil = new MeetingDbUtil(dataSource);
			meetingUserDbUtil = new MeetingUserDbUtil(dataSource);
			directionUtil = new DirectionUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String command = request.getParameter("command");
			String meetingId = request.getParameter("meetingId");
			String userId = "0";
			
			if (command == null) {
				command = LISTMEETINGS;
			}
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie tempCookie : cookies) {
					if ("JSP.userId".equals(tempCookie.getName())){
						userId = tempCookie.getValue();
						break;
					}
				}
			}
			
			switch (command) {
				case LISTMEETINGS:
					listMeetings(request, response, userId);
					break;
				case ADDMEETING:
					addMeeting(request, response);
					break;
				case LOADMEETING:
					loadMeeting(request, response);
					break;
				case UPDATEMEETING:
					updateMeeting(request, response);
					break;
				case DELETEMEETING:
					deleteMeeting(request, response);
					break;
				case REFRESHMEETINGS:
					refreshMeetings(request, response);
					break;
				case LISTPARTICIPANTS:
					listParticipants(request, response, meetingId);
					break;
	
				default:
					listMeetings(request, response, userId);
					break;
			}
			
		} catch (Exception e) {
			directionUtil.parameterErrorDirect(response);
			e.printStackTrace();
		}
		
	}

	private void listParticipants(HttpServletRequest request, HttpServletResponse response, String meetingId) {
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId, response);
		List<User> userForMeetings = meetingUserDbUtil.getUserForMeeting(meetingId, response);
		boolean isEmpty = userForMeetings.isEmpty();

		request.setAttribute("MEETING", meeting);
		request.setAttribute("USERFORMEETING", userForMeetings);
		request.setAttribute("ISEMPTY", isEmpty);
		
		directionUtil.forward(request, response, "src/Meeting/list-user-for-meeting.jsp");

	}

	private void refreshMeetings(HttpServletRequest request, HttpServletResponse response) {
		
		int counter = 1;
		int value = 0;
		while (!request.getParameter("end").equals(counter + "")) {
			value = 0;
			if (request.getParameter("display" + counter) != null) {
				value = 1;
			}
			meetingDbUtil.refreshMeetings(value, Integer.parseInt(request.getParameter("display" + counter + "hidden")), response);
			counter++;
		}
		this.redirect(response);
		
	}

	private void deleteMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		String meeting = request.getParameter("meetingId");
		
		meetingDbUtil.deleteMeeting(meeting, response);
		
		this.redirect(response);		
	}

	private void updateMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("meetingId"));
		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;
		
		if (request.getParameter("display") != null) {
			display = true;
		}
		
		Meeting meeting = new Meeting(id, name, date, time, display);
		
		meetingDbUtil.updateMeeting(meeting, response);
		
		this.redirect(response);
		
	}

	private void loadMeeting(HttpServletRequest request, HttpServletResponse response) {

		String meetingId = request.getParameter("meetingId");
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId, response);
		
		request.setAttribute("MEETING", meeting);
		
		directionUtil.forward(request, response, "src/Meeting/meeting-update.jsp");
		
	}

	private void addMeeting(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;
		
		if (request.getParameter("display") != null) {
			display = true;
		}
		
		Meeting meeting = new Meeting(name, date, time, display);
		
		meetingDbUtil.addMeeting(meeting, response);
		
		this.redirect(response);
	}

	private void listMeetings(HttpServletRequest request, HttpServletResponse response, String userId) {

		List<Meeting> meetings = meetingDbUtil.getMeetings(response);
		List<String> meetingsSignedUp = meetingUserDbUtil.getMeetingForUser(userId, response);

		request.setAttribute("MEETING_LIST", meetings);
		request.setAttribute("MEETINGS_SIGNED_UP", meetingsSignedUp);
		
		directionUtil.forward(request, response, "src/Meeting/meeting-list.jsp");
		
	}
	
	private void redirect(HttpServletResponse response) {
		directionUtil.direct(response, "MeetingControllerServlet");
	}
	
}
