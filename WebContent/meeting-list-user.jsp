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

	<div id="container">
		<div id="content">
			<form action="UserControllerServlet" method=GET>
				<input type="hidden" name="command" value="SIGNUPUSERFORMEETING" />
				<a href="user-login.jsp">Logout</a>

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
									<input type="checkbox" name="display${counter}" value=0 />
									<input type="hidden" name="display${counter}hidden" value="${tempMeeting.id}" />
								</td>
								<c:set var="counter" value="${counter+1}" />
							</c:if>
						</tr>
					</c:forEach>
					<input type="hidden" name="end" value="${counter}" />
				</table>
				<input type="submit" value="Zu Veranstaltungen eintragen" name="Save" class="save" />
			</form>
		</div>
	</div>

</body>
</html>