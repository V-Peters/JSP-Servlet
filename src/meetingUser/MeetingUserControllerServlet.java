package meetingUser;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import navigation.NavigationUtil;
import navigation.ValidPath;

/**
 * Servlet implementation class MeetingUserControllerServlet
 */
@WebServlet("/MeetingUserControllerServlet")
public class MeetingUserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SIGNUPUSERFORMEETING = "SIGNUPUSERFORMEETING";

	private MeetingUserDbUtil meetingUserDbUtil;
	
	private MeetingUserServiceClass meetingUserServiceClass;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;


	@Override
	public void init() {
		
		try {
			super.init();
		
			meetingUserDbUtil = new MeetingUserDbUtil(dataSource);
			meetingUserServiceClass = new MeetingUserServiceClass(meetingUserDbUtil);
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
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
					meetingUserServiceClass.signUpUserForMeeting(request, response, userId);
					break;
	
				default:
					meetingUserServiceClass.signUpUserForMeeting(request, response, userId);
					break;
			}
			
		} catch (Exception e) {
			NavigationUtil.navigate(response, ValidPath.ERROR_PARAMETER);
			e.printStackTrace();
		}
		
	}

}
