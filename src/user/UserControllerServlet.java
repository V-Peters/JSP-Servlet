package user;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import direction.DirectionUtil;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADDUSER = "ADDUSER";
	private static final String LOGINUSER = "LOGINUSER";
	private static final String DELETECOOKIE = "DELETECOOKIE";

	private UserDbUtil userDbUtil;
	private DirectionUtil directionUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() {
		
		try {
			super.init();
			
			userDbUtil = new UserDbUtil(dataSource);
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
			
			if (command == null) {
				command = DELETECOOKIE;
			}
			
			switch (command) {
				case ADDUSER:
					addUser(request, response);
					break;
				case LOGINUSER:
					loginUser(request, response);
					break;
				case DELETECOOKIE:
					deleteCookie(request, response);
					break;
	
				default:
					deleteCookie(request, response);
					break;
			}
			
			
		} catch (Exception e) {
			directionUtil.parameterErrorDirect(response);
		}
	}

	private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		User nullUser = new User(0, "0", "0", "0");
		userDbUtil.createCookies(response, nullUser, 0);
		this.logout(response);
	
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User(username, password);

		if (userDbUtil.loginUser(response, user)) {
			this.correctUser(response);
		} else {
			this.wrongUser(response);
		}
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String company = request.getParameter("company");

		User user = new User(username, password, firstname, lastname, email, company);

		if (userDbUtil.addUser(response, user)) {
			this.correctUser(response);
		} else {
			this.doubleUser(response);
		}
	}
	
	private void correctUser(HttpServletResponse response) {
		directionUtil.direct(response, "src/User/correct-user.jsp");
	}
	
	private void wrongUser(HttpServletResponse response) {
		directionUtil.direct(response, "src/User/wrong-user.jsp");
	}
	
	private void doubleUser(HttpServletResponse response) {
		directionUtil.direct(response, "src/User/double-user.jsp");
	}
	
	private void logout(HttpServletResponse response) {
		directionUtil.direct(response, "src/Logout/logout.jsp");
	}

}
