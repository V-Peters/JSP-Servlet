package user;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADDUSER = "ADDUSER";
	private static final String LOGINUSER = "LOGINUSER";

	private UserDbUtil userDbUtil;
	
	private String id;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {
		super.init();
		
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
			String command = request.getParameter("command");
			
			if (command == null) {
				command = ADDUSER;
			}
			
			switch (command) {
				case ADDUSER:
					addUser(request, response);
					break;
				case LOGINUSER:
					loginUser(request, response);
					break;
	
				default:
					addUser(request, response);
					break;
			}
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws Exception{

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User(username, password);

		if (userDbUtil.loginUser(response, user)) {
//			id = userDbUtil.getUserId(user);
//			this.redirect(request, response);
			this.correctUser(response);
		} else {
			this.wrongUser(response);
		}
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String vorname = request.getParameter("vorname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String company = request.getParameter("company");

		User user = new User(username, password, vorname, lastname, email, company);

		userDbUtil.addUser(user);
		id = userDbUtil.getUserId(user);
		
		this.redirect(request, response);
	}
	
	private void redirect(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		HttpSession session = request.getSession(false);
//		session.setAttribute("id", id);
//		System.out.println(id);
		response.sendRedirect("MeetingUserControllerServlet");
	}
	
	private void correctUser(HttpServletResponse response) throws IOException {
		response.sendRedirect("User/correct-user.jsp");
	}
	
	private void wrongUser(HttpServletResponse response) throws IOException {
		response.sendRedirect("User/wrong-user.jsp");
	}

}
