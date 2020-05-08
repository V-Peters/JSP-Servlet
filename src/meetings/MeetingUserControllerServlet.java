package meetings;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import meetingsUserConnection.MeetingUserConnectionDbUtil;

/**
 * Servlet implementation class MeetingControllerServlet
 */
@WebServlet("/MeetingUserControllerServlet")
public class MeetingUserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LISTMEETINGSUSER = "LISTMEETINGSUSER";

	private MeetingDbUtil meetingDbUtil;
	private MeetingUserConnectionDbUtil meetingUserConnectionDbUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			meetingDbUtil = new MeetingDbUtil(dataSource);
			meetingUserConnectionDbUtil = new MeetingUserConnectionDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
//			HttpSession session = request.getSession(false);
//			if (session == null) {
//				
//			}
			
			String command = request.getParameter("command");
			String userId = "0";

			if (command == null) {
				command = LISTMEETINGSUSER;
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
				case LISTMEETINGSUSER:
					listMeetingsUser(request, response, userId);
					break;
	
				default:
					listMeetingsUser(request, response, userId);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void listMeetingsUser(HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		
		List<Meeting> meetings = meetingDbUtil.getMeetings();
		List<String> meetingsSignedUp = meetingUserConnectionDbUtil.getMeetingUserConnection(userId);

		request.setAttribute("MEETING_LIST", meetings);
		request.setAttribute("MEETINGS_SIGNED_UP", meetingsSignedUp);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Meetings/meeting-list-user.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void redirect(HttpServletResponse response) throws Exception{
		response.sendRedirect("MeetingUserControllerServlet");
	}

}
