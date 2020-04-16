package JSP_Servlets;

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
@WebServlet("/MeetingControllerServlet")
public class MeetingControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST = "LIST";
	private static final String ADD = "ADD";
	private static final String LOAD = "LOAD";
	private static final String UPDATE = "UPDATE";
	private static final String DELETE = "DELETE";
	private static final String REFRESH = "REFRESH";

	private MeetingDbUtil meetingDbUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		//create our meeting db util ... and pass in the conn pool / database
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
			// read the "command" parameter
			String command = request.getParameter("command");
			
			//if the command is missing, then default to listing meetings
			if (command == null) {
				command = LIST;
			}
			
			// route to the appropriate method
			switch (command) {
			case LIST:
				listMeetings(request, response);
				break;
			case ADD:
				addMeeting(request, response);
				break;
			case LOAD:
				loadMeeting(request, response);
				break;
			case UPDATE:
				updateMeeting(request, response);
				break;
			case DELETE:
				deleteMeeting(request, response);
				break;
			case REFRESH:
				refreshMeetings(request, response);
				break;

			default:
				listMeetings(request, response);
				break;
			}
			
			
			// list the meetings ... in MVC fashion
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void refreshMeetings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int counter = 1;
		while (!request.getParameter("end").equals(counter + "")) {
			meetingDbUtil.refreshMeetings(Integer.parseInt(request.getParameter("display" + counter)), counter - 1);
			System.out.println(request.getParameter("display" + counter));
			counter++;
		}

		
		
	}

	private void deleteMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// read meeting id from form data
		String meeting = request.getParameter("meetingId");
		
		// delete meeting from database
		meetingDbUtil.deleteMeeting(meeting);
		
		// send meeting back to "list meetings" page
		this.redirect(response);		
	}

	private void updateMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// read meeting info from form data
		int id = Integer.parseInt(request.getParameter("meetingId"));
		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;
		if (request.getParameter("display") != null) {
			display = true;
		}
		
		// create new meeting object
		Meeting meeting = new Meeting(id, name, date, time, display);
		
		// perform update on database
		meetingDbUtil.updateMeeting(meeting);
		
		// send them back to the "list meetings" page
		this.redirect(response);		
	}

	private void loadMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{

		// read meeting id from data
		String meetingId = request.getParameter("meetingId");
		
		// get meeting from database
		Meeting meeting = meetingDbUtil.getMeeting(meetingId);
		
		// place meeting in the request attribute
		request.setAttribute("MEETING", meeting);
		
		// send to jsp page: meeting-update.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/meeting-update.jsp");
		dispatcher.forward(request, response);
	}

	private void addMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read meeting info from form data
		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;
		if (request.getParameter("display") != null) {
			display = true;
		}
		
		//create a new meeting object
		Meeting meeting = new Meeting(name, date, time, display);
		
		// add the meeting to the database
		meetingDbUtil.addMeeting(meeting);
		
		// send back to main page (the meeting list)
		this.redirect(response);
	}

	private void listMeetings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get meeting from db util
		List<Meeting> meetings = meetingDbUtil.getMeetings();
		
		//add meetings to the request
		request.setAttribute("MEETING_LIST", meetings);
		
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/meeting-list.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void redirect(HttpServletResponse response) throws Exception{
		response.sendRedirect("/JSP_Servlets/MeetingControllerServlet");
	}

}
