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
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADDUSER = "ADDUSER";
	private static final String LOGINUSER = "LOGINUSER";
	private static final String SIGNUPUSERFORMEETING = "SIGNUPUSERFORMEETING";

	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
		//create our user db util ... and pass in the conn pool / database
		try {
			userDbUtil = new UserDbUtil(dataSource);
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
				command = ADDUSER;
			}
			
			// route to the appropriate method
			switch (command) {
				case ADDUSER:
					addUser(request, response);
					break;
				case LOGINUSER:
					loginUser(request, response);
					break;
				case SIGNUPUSERFORMEETING:
					signUpUserForMeeting(request, response);
					break;
	
				default:
					addUser(request, response);
					break;
			}
			
			
			// list the meetings ... in MVC fashion
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	private void signUpUserForMeeting(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws Exception{

		// read user info from form data
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		//create a new user object
		User user = new User(username, password);

		// add the user to the database
		userDbUtil.loginUser(user);
		
		// send back to main page (the user list)
		this.redirect(response);
		
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read user info from form data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String vorname = request.getParameter("vorname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String company = request.getParameter("company");

		//create a new user object
		User user = new User(username, password, vorname, lastname, email, company);

		// add the user to the database
		userDbUtil.addUser(user);
		
		// send back to main page (the user list)
		this.redirect(response);
	}
	
	private void redirect(HttpServletResponse response) throws Exception{
		response.sendRedirect("/JSP_Servlets/MeetingControllerServlet");
	}

}
