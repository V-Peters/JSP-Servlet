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
			<form action="MeetingControllerServlet" method=GET>
				<input type="hidden" name="command" value="REFRESH" />
				<input type="button" value="Veranstaltung hinzufügen" onclick="window.location.href='meeting-add.jsp'; return false;" />

				<table border="1">
					<tr>
						<th>ID</th>
						<th width=700>Name</th>
						<th>Datum</th>
						<th>Uhrzeit</th>
						<th>Aktion</th>
						<th>anzeigen?</th>
					</tr>
					<c:set var="counter" value="${1}" />
					<c:forEach var="tempMeeting" items="${MEETING_LIST}">

						<!-- create an own link for each meeting -->
						<c:url var="tempLink" value="MeetingControllerServlet">
							<c:param name="command" value="LOAD" />
							<c:param name="meetingId" value="${tempMeeting.id}" />
						</c:url>

						<!-- create an own link for each meeting -->
						<c:url var="deleteLink" value="MeetingControllerServlet">
							<c:param name="command" value="DELETE" />
							<c:param name="meetingId" value="${tempMeeting.id}" />
						</c:url>

						<tr>
							<td>${counter}</td>
							<td>${tempMeeting.getName()}</td>
							<td>
								${tempMeeting.getDate().substring(8)}.${tempMeeting.getDate().substring(5, 7)}.${tempMeeting.getDate().substring(0, 4)}
							</td>
							<td>${tempMeeting.getTime().substring(0, 5)} Uhr</td>
							<td><a href="${tempLink}">Bearbeiten</a> | <a
								href="${deleteLink}"
								onclick="if (!(confirm('Sind Sie sicher, dass Sie diese Veranstaltung löschen wollen?'))) return false">Löschen</a>
							</td>
							<td align="center">
								<c:if test="${tempMeeting.isDisplay()}">
<!-- 									<input type="checkbox" name="display${counter-1}" value=1	-->
									<input type="checkbox" name="display${counter}" value=1 checked />
								</c:if> <c:if test="${not tempMeeting.isDisplay()}">
<!-- 									<input type="checkbox" name="display${counter-1}" value=0 />	-->
									<input type="checkbox" name="display${counter}" value=0 />
								</c:if>
								<input type="hidden" name="display${counter}hidden" value="${tempMeeting.id}" />
							</td>
							<c:set var="counter" value="${counter+1}" />
						</tr>
					</c:forEach>
					<input type="hidden" name="end" value="${counter}" />
				</table>
				<input type="submit" value="Änderungen speichern" name="Save" class="save" />
			</form>
		</div>
	</div>

</body>
</html>