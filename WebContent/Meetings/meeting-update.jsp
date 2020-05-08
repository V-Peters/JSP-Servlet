<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<title>Veranstaltung bearbeiten</title>
<!-- 
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
	-->
</head>

<body>

	<div id="wrapper">
		<div id="headder">
			<h2>Veranstaltungen</h2>
		</div>
	</div>

	<div id="container">
		<h3>Veranstaltung bearbeiten</h3>

		<form action="MeetingAdminControllerServlet" method=GET>

			<input type="hidden" name="command" value="UPDATEMEETING" /> <input
				type="hidden" name="meetingId" value="${MEETING.id}" />

			<table>
				<tbody>
					<tr>
						<td><label>Name:</label></td>
						<td><input type="text" name="name"
							value="${MEETING.name}" required maxlength="100" /></td>
					</tr>
					<tr>
						<!-- TODO auswahlmöglichkeit -->
						<td><label>Datum:</label></td>
						<td>
							<table>
								<tbody>
									<tr>
										<td>Tag:</td>
										<td>Monat:</td>
										<td>Jahr:</td>
									</tr>
									<tr>
										<td><select name="day">
												<option>${MEETING.date.substring(8, 10)}</option>
												<% for (int i = 1; i <= 31; i++) out.print("<option>" + i + "</option>"); %>
										</select></td>
										<td><select name="month">
												<option>${MEETING.date.substring(5, 7)}</option>
												<% for (int i = 1; i <= 12; i++) out.print("<option>" + i + "</option>"); %>
										</select></td>
										<td><select name="year">
												<option>${MEETING.date.substring(0, 4)}</option>
												<% for (int i = 2020; i <= 2100; i++) out.print("<option>" + i + "</option>"); %>
										</select></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<!-- TODO auswahlmöglichkeit -->
						<td><label>Uhrzeit:</label></td>
						<td>
							<table>
								<tbody>
									<tr>
										<td>Stunde:</td>
										<td>Minute:</td>
									</tr>
									<tr>
										<td><select name="hour">
												<option>${MEETING.time.substring(0, 2)}</option>
												<% for (int i = 0; i <= 23; i++) out.print("<option>" + i + "</option>"); %>
										</select></td>
										<td><select name="minute">
												<option>${MEETING.time.substring(3, 5)}</option>
												<% for (int i = 0; i <= 55; i+= 5) out.print("<option>" + i + "</option>"); %>
										</select></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>Anzeigen:</td>
						<td><c:if test="${MEETING.isDisplay()}">
								<input type="checkbox" name="display" value="display" checked />
							</c:if> <c:if test="${not MEETING.isDisplay()}">
								<input type="checkbox" name="display" value="display" />
							</c:if></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Anderungen speichern"
							name="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>

		</form>

		<div style="" :both;"></div>

		<p>
			<a href="MeetingAdminControllerServlet">Zurück zur Veranstaltungsliste</a>
		</p>

	</div>

</body>
</html>