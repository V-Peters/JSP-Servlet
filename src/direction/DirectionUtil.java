package direction;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DirectionUtil {
	
	public void direct(HttpServletResponse response, String destination) {
		try {
			response.sendRedirect(destination);
		} catch (Exception e) {
			directionErrorDirect(response);
			e.printStackTrace();
		}
	}
	
	public void forward(HttpServletRequest request, HttpServletResponse response, String direction) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			directionErrorDirect(response);
			e.printStackTrace();
		}
	}
	
	public void directionErrorDirect(HttpServletResponse response) {
		try {
			response.sendRedirect("src/Error/linking-error.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Die Adresse konnte nicht gefunden werden.");
			e.printStackTrace();
		}
	}

	public void closeErrorDirect(HttpServletResponse response) {
		this.direct(response, "src/Error/close-error.jsp");
	}

	public void parameterErrorDirect(HttpServletResponse response) {
		this.direct(response, "src/Error/parameter-error.jsp");
	}
	
	public void databaseErrorDirect(HttpServletResponse response) {
		this.direct(response, "src/Error/database-error.jsp");
	}
	
}
