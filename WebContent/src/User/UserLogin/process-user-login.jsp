<html>

<head>
	<title>User Eingabe</title>
</head>

<body>

	<form name="userLogin" action="../../../UserControllerServlet" method=GET>
		<input type="hidden" name="command" value="LOGINUSER" />
		<input type="hidden" name="username" value="${param.username}" />
		<input type="hidden" name="password" value="${param.password}" />
	</form>
	
	<script>
	    document.userLogin.submit();
	</script>
	
</body>

</html>