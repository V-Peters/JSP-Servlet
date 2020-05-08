<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Liste der Veranstaltungen</title>
<!-- 
	<link type="text/css" rel="stylesheet" href="css/style.css" />
	-->
</head>

<body>

	<div id="wrapper">
		<div id="headder">
			<h2>Veranstaltungen</h2>
		</div>
	</div>
	
	
	<%!
		private String userId = "0";
	%>
	<%
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie tempCookie : cookies) {
				if ("JSP.userId".equals(tempCookie.getName())){
					userId = tempCookie.getValue();
					break;
				}
			}
		}
	%>

	<c:set var="userId" value="<%= userId %>" />
		
	<div id="container">
		<div id="content">
			<form action="MeetingUserConnectionControllerServlet" method=GET>
				<input type="hidden" name="command" value="SIGNUPUSERFORMEETING" />
				<a href="UserLogin/user-login.jsp">Logout</a>

				<table border="1">
					<tr>
						<th>ID</th>
						<th width=700>Name</th>
						<th>Datum</th>
						<th>Uhrzeit</th>
						<th>teilnehmen</th>
					</tr>
					<c:set var="counter" value="${1}" />
					<c:forEach var="tempMeeting" items="${MEETING_LIST}">
						<tr>
							<c:if test="${tempMeeting.isDisplay()}">
								<td>${counter}</td>
								<td>${tempMeeting.getName()}</td>
								<td>${tempMeeting.getDate().substring(8)}.${tempMeeting.getDate().substring(5, 7)}.${tempMeeting.getDate().substring(0, 4)}</td>
								<td>${tempMeeting.getTime().substring(0, 5)} Uhr</td>
								<td align="center">
								<c:set var="signedUp" value="false" />
									<c:forEach var="tempMeetingId" items="${MEETINGS_SIGNED_UP}">
										<c:if test="${tempMeetingId == tempMeeting.getId()}">
											<c:set var="signedUp" value="true" />
										</c:if>
										<c:if test="${tempMeetingId != tempMeeting.getId()}">
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
							</c:if>
						</tr>
					</c:forEach>
					<input type="hidden" name="end" value="${counter}" />
				</table>
				<input type="submit" value="Zu Veranstaltungen ein-/austragen" name="Save" class="save" />
			</form>
		</div>
	</div>

</body>
</html>