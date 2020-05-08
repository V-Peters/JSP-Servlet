package meetingsUserConnection;

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

/**
 * Servlet implementation class MeetingControllerServlet
 */
@WebServlet("/MeetingUserConnectionControllerServlet")
public class MeetingUserConnectionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SIGNUPUSERFORMEETING = "SIGNUPUSERFORMEETING";

	private MeetingUserConnectionDbUtil meetingUserConnectionDbUtil;

	private String userId = "0";
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;


	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
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
			String command = request.getParameter("command");
			

			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie tempCookie : cookies) {
					if ("JSP.userId".equals(tempCookie.getName())){
						userId = tempCookie.getValue();
						break;
					}
				}
			}
			
			if (command == null) {
				command = SIGNUPUSERFORMEETING;
			}
			
			
			switch (command) {
				case SIGNUPUSERFORMEETING:
					signUpUserForMeeting(request, response);
					break;
	
				default:
					signUpUserForMeeting(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void signUpUserForMeeting(HttpServletRequest request, HttpServletResponse response) throws Exception{

		int counter = 1;
		int value = 0;
		while (!request.getParameter("end").equals(counter + "")) {
			value = 0;
			if (request.getParameter("signup" + counter) != null) {
				value = 1;
			}
			meetingUserConnectionDbUtil.signUpUserForMeeting(value, userId, request.getParameter("signup" + counter + "hidden"));
			counter++;
		}
		this.redirect(response);
	}
	
	private void redirect(HttpServletResponse response) throws Exception{
		response.sendRedirect("MeetingUserControllerServlet");
	}

}
