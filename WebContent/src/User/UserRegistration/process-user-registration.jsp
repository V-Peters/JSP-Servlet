<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>User Eingabe</title>
</head>

<body>
	
	<c:if test="${param.password == param.passwordCheck}">
		<form action="../../../UserControllerServlet" method=GET>
			<input type="hidden" name="command" value="ADDUSER" />
			Der Benutzer hat sich mit folgenden Daten registriert:
			<table>
				<tbody>
					<tr>
						<td><br></td>
					</tr>
					<tr>
						<td> Benutzername:</td>
						<td>${param.username}</td>
						<input type="hidden" name="username" value="${param.username}" />
					</tr>
					<tr>
						<td>
							<br>
							<input type="hidden" name="password" value="${param.password}" />
						</td>
					</tr>
					<tr>
						<td> Vorname:</td>
						<td>${param.firstname}</td>
						<input type="hidden" name="firstname" value="${param.firstname}" />
					</tr>
					<tr>
						<td> Nachname:</td>
						<td>${param.lastname}</td>
						<input type="hidden" name="lastname" value="${param.lastname}" />
					</tr>
					<tr>
						<td> E-Mail:</td>
						<td>${param.email}</td>
						<input type="hidden" name="email" value="${param.email}" />
					</tr>
					<tr>
						<td> Firma/Unternehmen:</td>
						<td>${param.company}</td>
						<input type="hidden" name="company" value="${param.company}" />
					</tr>
				</tbody>
			</table>
			<br>
			Sind diese Angaben richtig?
			<input type="submit" value="Ja" name="Save" class="save" />
			<input type="button" value="Nein" onclick="window.location.href='user-registration.html'; return false;" />
		</form>
	</c:if>
	
	
	<c:if test="${param.password != param.passwordCheck}">
		<h1>Die Passwörter stimmen nicht überein!</h1>
		Automatische Weiterleitung eingeleitet.
		<meta http-equiv="refresh" content="2; URL=user-registration.html">
	</c:if>

</body>

</html>