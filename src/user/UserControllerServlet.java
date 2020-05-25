package user;

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
	
	private String id;
	
	@Resource(name="jdbc/jsp_test")
	private DataSource dataSource;
	

	@Override
	public void init() {
		
		try {
			super.init();
			
			userDbUtil = new UserDbUtil(dataSource);
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
			//TODO
			System.out.println("falsches Parameter");
		}
	}

	private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		

		String userId = "0";
		String firstname = "Nie";
		String lastname = "mand";
		
		Cookie deleteUserIdCookie = new Cookie("JSP.userId", null);
		Cookie deleteUserFirstnameCookie = new Cookie("JSP.userFirstname", null);
		Cookie deleteUserLastnameCookie = new Cookie("JSP.userLastname", null);
		Cookie deleteUserIsAdminCookie = new Cookie("JSP.userIsAdmin", null);
		System.out.println("Namen erstellt");
		
		deleteUserIdCookie.setMaxAge(0);
		deleteUserFirstnameCookie.setMaxAge(0);
		deleteUserLastnameCookie.setMaxAge(0);
		deleteUserIsAdminCookie.setMaxAge(0);
		System.out.println("timer auf 0 gesetzt");
		
		deleteUserIdCookie.setPath("/");
		deleteUserFirstnameCookie.setPath("/");
		deleteUserLastnameCookie.setPath("/");
		deleteUserIsAdminCookie.setPath("/");
		System.out.println("path auf / gesetzt");
		
		response.addCookie(deleteUserIdCookie);
		response.addCookie(deleteUserFirstnameCookie);
		response.addCookie(deleteUserLastnameCookie);
		response.addCookie(deleteUserIsAdminCookie);
		System.out.println("gelöscht");
		
		Cookie[] cookies2 = request.getCookies();
		if (cookies2 != null) {
			for (Cookie tempCookie : cookies2) {
				if ("JSP.userId".equals(tempCookie.getName())){
					userId = tempCookie.getValue();
				} else if ("JSP.userFirstname".equals(tempCookie.getName())){
					firstname = tempCookie.getValue();
				} else if ("JSP.userLastname".equals(tempCookie.getName())){
					lastname = tempCookie.getValue();
				}
			}
		} else {
			System.out.println("die Cookies sind leer");
		}
		String userName = firstname + " " + lastname;
		System.out.println("Nach Logout: userId: " + userId);
		System.out.println("Nach Logout: userName: " + userName);
		
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

//		userDbUtil.addUser(response, user);

		if (userDbUtil.addUser(response, user)) {
			this.correctUser(response);
		} else {
			this.doubleUser(response);
		}
//		this.correctUser(response);
	}
	
	private void redirect(HttpServletRequest request, HttpServletResponse response) {
		try {
		response.sendRedirect("MeetingUserControllerServlet");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Die Seite MeetingControllerServlet kann nicht gefunden werden.");
			e.printStackTrace();
		}
	}
	
	private void correctUser(HttpServletResponse response) {
		try {
		response.sendRedirect("src/User/correct-user.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Die Seite correct-user.jsp kann nicht gefunden werden.");
			e.printStackTrace();
		}
	}
	
	private void wrongUser(HttpServletResponse response) {
		try {
		response.sendRedirect("src/User/wrong-user.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Die Seite wrong-user.jsp kann nicht gefunden werden.");
			e.printStackTrace();
		}
	}
	
	private void doubleUser(HttpServletResponse response) {
		try {
		response.sendRedirect("src/User/double-user.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Die Seite double-user.jsp kann nicht gefunden werden.");
			e.printStackTrace();
		}
	}
	
	private void wrongDatabaseUser(HttpServletResponse response) {
		try {
			response.sendRedirect("src/Error/wrong-database-user.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void logout(HttpServletResponse response) {
		try {
			response.sendRedirect("src/User/UserLogin/user-login.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
