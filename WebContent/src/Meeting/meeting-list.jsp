<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<title>Liste der Veranstaltungen</title>
</head>

<body>

	<h2>Veranstaltungen</h2>
	
	<hr>
	
	<%!
	private static final String USERIDCOOKIE = "JSP.userId";
	private static final String USERFIRSTNAMECOOKIE = "JSP.userFirstname";
	private static final String USERLASTNAMECOOKIE = "JSP.userLastname";
	private static final String USERISADMINCOOKIE = "JSP.userIsAdmin";
	private String userId;
	private String firstname;
	private String lastname;
	private boolean isAdmin;
	%>
	<%
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
					isAdmin = "1".equals(tempCookie.getValue()) ? true : false;
					break;
	
				default:
					break;
				}
			}
		}
	%>

	<c:set var="userId" value="<%= userId %>" />
	<c:set var="userName" value="<%= firstname + \" \" + lastname %>" />
	<c:set var="isAdmin" value="<%= isAdmin %>" />
	
	<c:if test="${isAdmin}" >
		<form action="MeetingControllerServlet" method=GET>
			<input type="hidden" name="command" value="REFRESHMEETINGS" />
			<input type="button" value="Veranstaltung hinzufügen" onclick="window.location.href='src/Meeting/meeting-add.jsp'; return false;" />
			Angemeldet als Admin,
			<a href="UserControllerServlet">Logout</a>

			<table border="1">
				<tr>
					<th>ID</th>
					<th width=700>Veranstaltungsname</th>
					<th>Datum</th>
					<th>Uhrzeit</th>
					<th>Aktion</th>
					<th>Für User sichtbar</th>
					<th>Teilnehmerliste</th>
				</tr>
				<c:set var="counter" value="${1}" />
				<c:forEach var="tempMeeting" items="${MEETING_LIST}">

					<c:url var="updateLink" value="MeetingControllerServlet">
						<c:param name="command" value="LOADMEETING" />
						<c:param name="meetingId" value="${tempMeeting.id}" />
					</c:url>

					<c:url var="deleteLink" value="MeetingControllerServlet">
						<c:param name="command" value="DELETEMEETING" />
						<c:param name="meetingId" value="${tempMeeting.id}" />
					</c:url>

					<c:url var="participantsLink" value="MeetingControllerServlet">
						<c:param name="command" value="LISTPARTICIPANTS" />
						<c:param name="meetingId" value="${tempMeeting.id}" />
					</c:url>

					<tr>
						<td align="right">${counter}</td>
						<td>${tempMeeting.getName()}</td>
						<td>${tempMeeting.getDate().substring(8)}.${tempMeeting.getDate().substring(5, 7)}.${tempMeeting.getDate().substring(0, 4)}</td>
						<td>${tempMeeting.getTime().substring(0, 5)} Uhr</td>
						<td>
							<a href="${updateLink}">Bearbeiten</a>
							 | 
							<a href="${deleteLink}" onclick="if (!(confirm('Sind Sie sicher, dass Sie diese Veranstaltung löschen wollen?'))) return false">Löschen</a>
						</td>
						<td align="center">
							<c:if test="${tempMeeting.isDisplay()}">
								<input type="checkbox" name="display${counter}" value=1 checked />
							</c:if>
							<c:if test="${not tempMeeting.isDisplay()}">
								<input type="checkbox" name="display${counter}" value=0 />
							</c:if>
							<input type="hidden" name="display${counter}hidden" value="${tempMeeting.id}" />
						</td>
						<c:set var="counter" value="${counter+1}" />
						<td align="center">
							<a href="${participantsLink}">anzeigen</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="end" value="${counter}" />
			<input type="submit" value="Änderungen speichern" name="Save" class="save" />
		</form>
	</c:if>
	
	<c:if test="${not isAdmin}" >
		<form action="MeetingUserControllerServlet" method=GET>
			<input type="hidden" name="command" value="SIGNUPUSERFORMEETING" />
			angemeldet als ${userName},
			<a href="UserControllerServlet">Logout</a>

			<table border="1">
				<tr>
					<th>ID</th>
					<th width=700>Veranstaltungsname</th>
					<th>Datum</th>
					<th>Uhrzeit</th>
					<th>teilnehmen</th>
				</tr>
				<c:set var="counter" value="${1}" />
				<c:forEach var="tempMeeting" items="${MEETING_LIST}">
					<c:if test="${tempMeeting.isDisplay()}">
						<tr>
							<td align="right">${counter}</td>
							<td>${tempMeeting.getName()}</td>
							<td>${tempMeeting.getDate().substring(8)}.${tempMeeting.getDate().substring(5, 7)}.${tempMeeting.getDate().substring(0, 4)}</td>
							<td>${tempMeeting.getTime().substring(0, 5)} Uhr</td>
							<td align="center">
								<c:set var="signedUp" value="false" />
								<c:forEach var="tempMeetingId" items="${MEETINGS_SIGNED_UP}">
									<c:if test="${tempMeetingId == tempMeeting.getId()}">
										<c:set var="signedUp" value="true" />
									</c:if>
								</c:forEach>
								<c:if test="${signedUp == true}" >
									<input type="checkbox" name="signup${counter}" value=1 checked />
								</c:if>
								<c:if test="${signedUp == false}" >
									<input type="checkbox" name="signup${counter}" value=0 />
								</c:if>
								<input type="hidden" name="signup${counter}hidden" value="${tempMeeting.id}" />
							</td>
							<c:set var="counter" value="${counter+1}" />
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<input type="hidden" name="end" value="${counter}" />
			<input type="submit" value="Zu Veranstaltungen ein-/austragen" name="Save" class="save" />
		</form>
	</c:if>

</body>
</html>