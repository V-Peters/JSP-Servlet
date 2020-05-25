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

	<h2>Veranstaltungen</h2>
	

	<input type="button" value="zurück zur Übersicht" onclick="window.location.href='MeetingControllerServlet'; return false;" />
	
	<c:set var="meeting" value="${MEETING}" />

	<table>
		<tr>
			<td align="center">
				<table>
					<tr>
						<th>${meeting.getName()}</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th>${meeting.getDate().substring(8)}.${meeting.getDate().substring(5, 7)}.${meeting.getDate().substring(0, 4)}</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<th>${meeting.getTime().substring(0, 5)} Uhr</th>
					</tr>
				</table>
			</td>
		</tr>
		<c:if test="${ISEMPTY}" >
			<tr>
				<td>
					Zu dieser Veranstaltung haben sich noch keine User angebemdet.
				</td>
			</tr>
		</c:if>
		<c:if test="${not ISEMPTY}" >
			<tr>
				<td>
					<table border="1">
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>E-mail</th>
							<th>Firma</th>
						</tr>
						<c:set var="counter" value="${1}" />
						<c:forEach var="tempUser" items="${USERFORMEETING}">
							<tr>
								<td>${counter}</td>
								<td>${tempUser.getFirstname()} ${tempUser.getLastname()}</td>
								<td>${tempUser.getEmail()}</td>
								<td>${tempUser.getCompany()}</td>
								<c:set var="counter" value="${counter+1}" />
							</tr>
						</c:forEach>
						<input type="hidden" name="end" value="${counter}" />
					</table>
				</td>
			</tr>
		</c:if>
	</table>

</body>
</html>