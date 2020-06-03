package navigation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NavigationUtil {
	
	public static void navigate(HttpServletResponse response, ValidPath validPath) {
		try {
			response.sendRedirect(validPath.getPath());
		} catch (Exception e) {
			navigationErrorDirect(response, ValidPath.ERROR_LINKING);
			e.printStackTrace();
		}
	}
	
	public static void navigate(HttpServletRequest request, HttpServletResponse response, ValidPath validPath) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(validPath.getPath());
			dispatcher.forward(request, response);
		} catch (Exception e) {
			navigationErrorDirect(response, ValidPath.ERROR_LINKING);
			e.printStackTrace();
		}
	}
	
	private static void navigationErrorDirect(HttpServletResponse response, ValidPath validPath) {
		try {
			response.sendRedirect(validPath.getPath());
		} catch (IOException e) {
			System.out.println("Die Adresse konnte nicht gefunden werden.");
			e.printStackTrace();
		}
	}

}
