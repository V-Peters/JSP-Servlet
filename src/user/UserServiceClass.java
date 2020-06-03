package user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import navigation.NavigationUtil;
import navigation.ValidPath;

public class UserServiceClass {
	
	UserDbUtil userDbUtil;


	public UserServiceClass(UserDbUtil userDbUtil) {
		this.userDbUtil = userDbUtil;
	}

	public void deleteCookie(HttpServletResponse response) {
		User nullUser = new User();
		UserServiceClass.createCookies(response, nullUser, 0);
		this.logout(response);
	
	}

	public void loginUser(HttpServletRequest request, HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User(username, password);

		if (userDbUtil.loginUser(response, user)) {
			this.correctUser(response);
		} else {
			this.wrongUser(response);
		}
	}

	public void registerUser(HttpServletRequest request, HttpServletResponse response) {

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

	public static void createCookies(HttpServletResponse response, User user, int maxAge) {

		Cookie userIdCookie = new Cookie("JSP.userId", user.getId() + "");
		Cookie userFirstnameCookie = new Cookie("JSP.userFirstname", user.getFirstname());
		Cookie userLastnameCookie = new Cookie("JSP.userLastname", user.getLastname());
		Cookie userIsAdminCookie = new Cookie("JSP.userIsAdmin", user.getIsAdmin());

		userIdCookie.setMaxAge(maxAge);
		userFirstnameCookie.setMaxAge(maxAge);
		userLastnameCookie.setMaxAge(maxAge);
		userIsAdminCookie.setMaxAge(maxAge);

		response.addCookie(userIdCookie);
		response.addCookie(userFirstnameCookie);
		response.addCookie(userLastnameCookie);
		response.addCookie(userIsAdminCookie);
		
	}
	
	public static User getCookie(HttpServletRequest request) {
		
		User tempUser;

		final String USERIDCOOKIE = "JSP.userId";
		final String USERFIRSTNAMECOOKIE = "JSP.userFirstname";
		final String USERLASTNAMECOOKIE = "JSP.userLastname";
		final String USERISADMINCOOKIE = "JSP.userIsAdmin";
		String userId = "0";
		String firstname = "Nie";
		String lastname = "nand";
		String isAdmin = "0";
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie tempCookie : cookies) {
				switch (tempCookie.getName()) {
				case USERIDCOOKIE:
					userId = tempCookie.getValue();
					break;
				case USERFIRSTNAMECOOKIE:
					firstname = tempCookie.getValue();
					break;
				case USERLASTNAMECOOKIE:
					lastname = tempCookie.getValue();
					break;
				case USERISADMINCOOKIE:
					isAdmin = tempCookie.getValue();
					break;
	
				default:
					break;
				}
			}
		}
		
		tempUser = new User(Integer.parseInt(userId), firstname, lastname, isAdmin);
		
		return tempUser;
		
	}
	
	private void correctUser(HttpServletResponse response) {
		NavigationUtil.navigate(response, ValidPath.CORRECT_USER);
	}
	
	private void wrongUser(HttpServletResponse response) {
		NavigationUtil.navigate(response, ValidPath.WRONG_USER);
	}
	
	private void doubleUser(HttpServletResponse response) {
		NavigationUtil.navigate(response, ValidPath.DOUBLE_USER);
	}
	
	private void logout(HttpServletResponse response) {
		NavigationUtil.navigate(response, ValidPath.LOGOUT);
	}


}
