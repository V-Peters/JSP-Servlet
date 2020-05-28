package meetingUser;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import direction.DirectionUtil;

/**
 * Servlet implementation class MeetingUserControllerServlet
 */
@WebServlet("/MeetingUserControllerServlet")
public class MeetingUserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SIGNUPUSERFORMEETING = "SIGNUPUSERFORMEETING";

	private MeetingUserDbUtil meetingUserDbUtil;
	private DirectionUtil directionUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;


	@Override
	public void init() {
		
		try {
			super.init();
		
			meetingUserDbUtil = new MeetingUserDbUtil(dataSource);
			directionUtil = new DirectionUtil();
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String command = request.getParameter("command");
			String userId = "0";
			
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
					signUpUserForMeeting(request, response, userId);
					break;
	
				default:
					signUpUserForMeeting(request, response, userId);
					break;
			}
			
		} catch (Exception e) {
			directionUtil.parameterErrorDirect(response);
			e.printStackTrace();
		}
		
	}

	private void signUpUserForMeeting(HttpServletRequest request, HttpServletResponse response, String userId) {

		int counter = 1;
		int value = 0;
		while (!request.getParameter("end").equals(counter + "")) {
			value = 0;
			if (request.getParameter("signup" + counter) != null) {
				value = 1;
			}
			meetingUserDbUtil.signUpUserForMeeting(value, userId, request.getParameter("signup" + counter + "hidden"), response);
			counter++;
		}
		directionUtil.direct(response, "MeetingControllerServlet");
	}
	
}
