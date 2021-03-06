<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<title>Veranstaltung hinzuf�gen</title>
</head>

<body>

	<h2>Veranstaltungen</h2>

	<h3>Veranstaltung hinzuf�gen</h3>

	<form action="../../MeetingControllerServlet" method=GET>
		<input type="hidden" name="command" value="ADDMEETING" />

		<table>
			<tbody>
				<tr>
					<td><label>Name:</label></td>
					<td>
						<table>
							<tbody>
								<tr>
									<td>�brige Zeichen:</td>
								</tr>
								<tr>
									<td><input type="text" required placeholder="Veranstaltungsname" name="name" maxlength="100" /></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>Datum:</td>
					<td>
						<table>
							<tbody>
								<tr>
									<td>Tag:</td>
									<td>Monat:</td>
									<td>Jahr:</td>
								</tr>
								<tr>
									<td>
										<select name="day">
											<% for (int i = 1; i <= 31; i++) out.print("<option>" + i + "</option>"); %>
										</select>
									</td>
									<td>
										<select name="month">
											<% for (int i = 1; i <= 12; i++) out.print("<option>" + i + "</option>"); %>
										</select>
									</td>
									<td>
										<select name="year">
											<% for (int i = 2020; i <= 2100; i++) out.print("<option>" + i + "</option>"); %>
										</select>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td><label>Uhrzeit:</label></td>
					<td>
						<table>
							<tbody>
								<tr>
									<td>Stunde:</td>
									<td>Minute:</td>
								</tr>
								<tr>
									<td>
										<select name="hour">
											<% for (int i = 0; i <= 23; i++) out.print("<option>" + i + "</option>"); %>
										</select>
									</td>
									<td>
										<select name="minute">
											<% for (int i = 0; i <= 55; i+= 5) out.print("<option>" + i + "</option>"); %>
										</select>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>Anzeigen:</td>
					<td><input type="checkbox" name="display" value="true" checked /></td>
				</tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Veranstaltung hinzuf�gen" name="Save" class="save" /></td>
				</tr>
			</tbody>
		</table>

	</form>

	<p>
		<a href="../../MeetingControllerServlet">Zur�ck zur Veranstaltungsliste</a>
	</p>

</body>

</html>