package user;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import navigation.NavigationUtil;
import navigation.ValidPath;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REGISTER_USER = "REGISTER_USER";
	private static final String LOGIN_USER = "LOGIN_USER";
	private static final String DELETE_COOKIE = "DELETE_COOKIE";

	private UserDbUtil userDbUtil;
	private UserServiceClass userServiceClass;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() {
		
		try {
			super.init();
			
			userDbUtil = new UserDbUtil(dataSource);
			userServiceClass = new UserServiceClass(userDbUtil);
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
			
			if (command == null) {
				command = DELETE_COOKIE;
			}
			
			switch (command) {
				case REGISTER_USER:
					userServiceClass.registerUser(request, response);
					break;
				case LOGIN_USER:
					userServiceClass.loginUser(request, response);
					break;
				case DELETE_COOKIE:
					userServiceClass.deleteCookie(response);
					break;
	
				default:
					userServiceClass.deleteCookie(response);
					break;
			}
			
			
		} catch (Exception e) {
			NavigationUtil.navigate(response, ValidPath.ERROR_PARAMETER);
		}
	}

}
