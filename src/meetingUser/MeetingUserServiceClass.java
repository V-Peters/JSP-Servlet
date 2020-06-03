package meetingUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import navigation.NavigationUtil;
import navigation.ValidPath;

public class MeetingUserServiceClass {

	private MeetingUserDbUtil meetingUserDbUtil;

	public MeetingUserServiceClass(MeetingUserDbUtil meetingUserDbUtil) {
		this.meetingUserDbUtil = meetingUserDbUtil;
	}

	public void signUpUserForMeeting(HttpServletRequest request, HttpServletResponse response, String userId) {

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
		NavigationUtil.navigate(response, ValidPath.MEETING_CONTROLLER_SERVLET);
	}

}
