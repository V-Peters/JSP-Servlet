package meetings;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MeetingControllerServlet
 */
@WebServlet("/MeetingAdminControllerServlet")
public class MeetingAdminControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LISTMEETINGSADMIN = "LISTMEETINGSADMIN";
	private static final String ADDMEETING = "ADDMEETING";
	private static final String LOADMEETING = "LOADMEETING";
	private static final String UPDATEMEETING = "UPDATEMEETING";
	private static final String DELETEMEETING = "DELETEMEETING";
	private static final String REFRESHMEETINGS = "REFRESHMEETINGS";

	private MeetingDbUtil meetingDbUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			meetingDbUtil = new MeetingDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String command = request.getParameter("command");
			
			if (command == null) {
				command = LISTMEETINGSADMIN;
			}
			
			
			switch (command) {
				case LISTMEETINGSADMIN:
					listMeetingsAdmin(request, response);
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
	
				default:
					listMeetingsAdmin(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void refreshMeetings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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

	private void deleteMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String meeting = request.getParameter("meetingId");
		
		meetingDbUtil.deleteMeeting(meeting);
		
		this.redirect(response);		
	}

	private void updateMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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

	private void loadMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{

		String meetingId = request.getParameter("meetingId");
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId);
		
		request.setAttribute("MEETING", meeting);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Meetings/meeting-update.jsp");
		dispatcher.forward(request, response);
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

	private void listMeetingsAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Meeting> meetings = meetingDbUtil.getMeetings();
		
		request.setAttribute("MEETING_LIST", meetings);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Meetings/meeting-list.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void redirect(HttpServletResponse response) throws Exception{
		response.sendRedirect("MeetingAdminControllerServlet");
	}

}
