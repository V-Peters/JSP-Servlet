<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>Admin Eingabe</title>
</head>

<body>

	<!-- Der Admin hat folgende Anmeldedaten: Benutzrname: admin, Passwort: admin	-->

	<c:if test="${param.username == 'admin' and param.password == 'admin'}">
		<meta http-equiv="refresh" content="0; URL=MeetingControllerServlet">
	</c:if>

	<c:if test="${param.username != 'admin' or param.password != 'admin'}">
		<h1>FALSCHER BENUTZERNAME ODER FALSCHES PASSWORT!</h1>
		Automatische Weiterleitung eingeleitet.
		<meta http-equiv="refresh" content="2; URL=admin-login.html">
	</c:if>

</body>

</html>