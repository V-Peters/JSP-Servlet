package navigation;

public enum ValidPath {

	ERROR_LINKING("src/Error/linking-error.jsp"),
	ERROR_PARAMETER("src/Error/parameter-error.jsp"),
	ERROR_DATABASE("src/Error/database-error.jsp"),
	ERROR_CLOSE("src/Error/close-error.jsp"),
	
	LIST_METINGS_FOR_USER("src/Meeting/list-user-for-meeting.jsp"),
	MEETING_UPDATE("src/Meeting/meeting-update.jsp"),
	MEETING_LIST("src/Meeting/meeting-list.jsp"),
	CORRECT_USER("src/User/correct-user.jsp"),
	WRONG_USER("src/User/wrong-user.jsp"),
	DOUBLE_USER("src/User/double-user.jsp"),
	LOGOUT("src/Logout/logout.jsp"),
	SESSION_TIMED_OUT("src/Logout/session-timed-out.jsp"),

	MEETING_CONTROLLER_SERVLET("MeetingControllerServlet");
	
	private String path;

	ValidPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
