<html>

<head>
	<title>richtige Eingabe</title>
</head>

<body>

	<form name="correctUser" action="../../MeetingControllerServlet" method=GET>
		<input type="hidden" name="command" value="LISTMEETINGS" />
	</form>
	<script>
	    document.correctUser.submit();
	</script>
	
</body>

</html>