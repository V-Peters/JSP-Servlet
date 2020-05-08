<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>Admin Eingabe</title>
</head>

<body>

	<!-- Der Admin hat folgende Anmeldedaten: Benutzername: admin, Passwort: admin	-->

	<c:if test="${param.username == 'admin' and param.password == 'admin'}">
		<form name="adminLogin" action="../MeetingAdminControllerServlet" method=GET>
			<input type="hidden" name="command" value="LISTMEETINGSADMIN" />
		</form>
		<script>
		    document.adminLogin.submit();
		</script>
	</c:if>

	<c:if test="${param.username != 'admin' or param.password != 'admin'}">
		<h1>FALSCHER BENUTZERNAME ODER FALSCHES PASSWORT!</h1>
		Automatische Weiterleitung eingeleitet.
		<meta http-equiv="refresh" content="2; URL=admin-login.html">
	</c:if>

</body>

</html>