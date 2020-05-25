package meeting;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			meetingDbUtil = new MeetingDbUtil(dataSource);
			meetingUserDbUtil = new MeetingUserDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
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
			// TODO
			System.out.println("Falscher Parameter");
			e.printStackTrace();
		}
		
	}

	private void listParticipants(HttpServletRequest request, HttpServletResponse response, String meetingId) {
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId);
		List<User> userForMeetings = meetingUserDbUtil.getUserForMeeting(meetingId);
		boolean isEmpty = userForMeetings.isEmpty();

		request.setAttribute("MEETING", meeting);
		request.setAttribute("USERFORMEETING", userForMeetings);
		request.setAttribute("ISEMPTY", isEmpty);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("src/Meeting/list-user-for-meeting.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void refreshMeetings(HttpServletRequest request, HttpServletResponse response) {
		
		int counter = 1;
		int value = 0;
		while (!request.getParameter("end").equals(counter + "")) {
			value = 0;
			if (request.getParameter("display" + counter) != null) {
				value = 1;
			}
			meetingDbUtil.refreshMeetings(value, Integer.parseInt(request.getParameter("display" + counter + "hidden")));
			counter++;
		}
		this.redirect(response);
		
	}

	private void deleteMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		String meeting = request.getParameter("meetingId");
		
		meetingDbUtil.deleteMeeting(meeting);
		
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
		
		meetingDbUtil.updateMeeting(meeting);
		
		this.redirect(response);		
	}

	private void loadMeeting(HttpServletRequest request, HttpServletResponse response) {

		String meetingId = request.getParameter("meetingId");
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId);
		
		request.setAttribute("MEETING", meeting);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("src/Meeting/meeting-update.jsp");
		this.forward(dispatcher, request, response);
	}

	private void addMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;
		if (request.getParameter("display") != null) {
			display = true;
		}
		
		Meeting meeting = new Meeting(name, date, time, display);
		
		meetingDbUtil.addMeeting(meeting);
		
		this.redirect(response);
	}

	private void listMeetings(HttpServletRequest request, HttpServletResponse response, String userId) {

		List<Meeting> meetings = meetingDbUtil.getMeetings();
		List<String> meetingsSignedUp = meetingUserDbUtil.getMeetingForUser(userId);

		request.setAttribute("MEETING_LIST", meetings);
		request.setAttribute("MEETINGS_SIGNED_UP", meetingsSignedUp);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("src/Meeting/meeting-list.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void redirect(HttpServletResponse response) {
		try {
			response.sendRedirect("MeetingControllerServlet");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void forward(RequestDispatcher dispatcher, HttpServletRequest request, HttpServletResponse response) {
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
