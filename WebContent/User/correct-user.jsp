<html>

<head>
	<title>richtige Eingabe</title>
</head>

<body>

	<form name="correctUser" action="../MeetingUserControllerServlet" method=GET>
		<input type="hidden" name="command" value="LISTMEETINGSUSER" />
	</form>
	<script>
	    document.correctUser.submit();
	</script>
	
</body>

</html>