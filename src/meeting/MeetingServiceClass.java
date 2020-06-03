package meeting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import meetingUser.MeetingUserDbUtil;
import navigation.NavigationUtil;
import navigation.ValidPath;
import user.User;

public class MeetingServiceClass {
	private static final String DISPLAY = "display";
	private static final String MEETING_ID = "meetingId";

	private MeetingDbUtil meetingDbUtil;
	private MeetingUserDbUtil meetingUserDbUtil;
	
	public MeetingServiceClass(MeetingDbUtil meetingDbUtil, MeetingUserDbUtil meetingUserDbUtil) {
		this.meetingDbUtil = meetingDbUtil;
		this.meetingUserDbUtil = meetingUserDbUtil;
	}

	public void listParticipants(HttpServletRequest request, HttpServletResponse response, String meetingId) {
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId, response);
		List<User> userForMeetings = meetingUserDbUtil.getUserForMeeting(meetingId, response);
		boolean isEmpty = userForMeetings.isEmpty();

		request.setAttribute("MEETING", meeting);
		request.setAttribute("USERFORMEETING", userForMeetings);
		request.setAttribute("ISEMPTY", isEmpty);
		
		NavigationUtil.navigate(request, response, ValidPath.LIST_METINGS_FOR_USER);

	}

	public void refreshMeetings(HttpServletRequest request, HttpServletResponse response) {
		
		int counter = 1;
		int value = 0;
		while (!request.getParameter("end").equals(counter + "")) {
			value = 0;
			if (request.getParameter(DISPLAY + counter) != null) {
				value = 1;
			}
			meetingDbUtil.refreshMeetings(value, Integer.parseInt(request.getParameter(DISPLAY + counter + "hidden")), response);
			counter++;
		}
		this.redirect(response);
		
	}

	public void deleteMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		int meetingId = Integer.parseInt(request.getParameter(MEETING_ID));
		
		meetingDbUtil.deleteMeeting(meetingId, response);
		
		this.redirect(response);		
	}

	public void updateMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter(MEETING_ID));
		
		Meeting meeting = new Meeting(id, this.createMeeting(request));
		
		meetingDbUtil.updateMeeting(meeting, response);
		
		this.redirect(response);
		
	}

	public void loadMeeting(HttpServletRequest request, HttpServletResponse response) {

		String meetingId = request.getParameter(MEETING_ID);
		
		Meeting meeting = meetingDbUtil.getMeeting(meetingId, response);
		
		request.setAttribute("MEETING", meeting);
		
		NavigationUtil.navigate(request, response, ValidPath.MEETING_UPDATE);
		
	}

	public void addMeeting(HttpServletRequest request, HttpServletResponse response) {
		
		Meeting meeting = this.createMeeting(request);
		
		meetingDbUtil.addMeeting(meeting, response);
		
		this.redirect(response);
	}
	
	private Meeting createMeeting(HttpServletRequest request) {
		
		Meeting tempMeeting;
		
		String name = request.getParameter("name");
		String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
		String time = request.getParameter("hour") + ":" + request.getParameter("minute") + ":00";
		boolean display = false;

		if (request.getParameter(DISPLAY) != null) {
			display = true;
		}
		
		tempMeeting = new Meeting(name, date, time, display);
		
		return tempMeeting;
	
	}

	public void listMeetings(HttpServletRequest request, HttpServletResponse response, String userId) {

		List<Meeting> meetings = meetingDbUtil.getMeetings(response);
		List<String> meetingsSignedUp = meetingUserDbUtil.getMeetingForUser(userId, response);

		request.setAttribute("MEETING_LIST", meetings);
		request.setAttribute("MEETINGS_SIGNED_UP", meetingsSignedUp);
		
		NavigationUtil.navigate(request, response, ValidPath.MEETING_LIST);
		
	}
	
	private void redirect(HttpServletResponse response) {
		NavigationUtil.navigate(response, ValidPath.MEETING_CONTROLLER_SERVLET);
	}
}
