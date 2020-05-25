<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>Logout</title>
</head>

<body>



	<%!
	private String userId = "0";
	private String firstname = "Nie";
	private String lastname = "mand";
//	private String userName = firstname + lastname;;
	%>
	<%
		Cookie[] cookies1 = request.getCookies();
		if (cookies1 != null) {
			for (Cookie tempCookie : cookies1) {
				if ("JSP.userId".equals(tempCookie.getName())){
					userId = tempCookie.getValue();
				} else if ("JSP.userFirstname".equals(tempCookie.getName())){
					firstname = tempCookie.getValue();
				} else if ("JSP.userLastname".equals(tempCookie.getName())){
					lastname = tempCookie.getValue();
				}
			}
		} else {
			System.out.println("die Cookies sind leer");
		}
		String userName = firstname + " " + lastname;
		System.out.println("Vor Logout: userId: " + userId);
		System.out.println("Vor Logout: userName: " + userName);
	%>

	<%
		Cookie deleteUserIdCookie = new Cookie("JSP.userId", null);
		Cookie deleteUserFirstnameCookie = new Cookie("JSP.userFirstname", null);
		Cookie deleteUserLastnameCookie = new Cookie("JSP.userLastname", null);
		System.out.println("Namen erstellt");
		deleteUserIdCookie.setMaxAge(0);
		deleteUserFirstnameCookie.setMaxAge(0);
		deleteUserLastnameCookie.setMaxAge(0);
		System.out.println("timer auf 0 gesetzt");
		deleteUserIdCookie.setPath("/");
		deleteUserFirstnameCookie.setPath("/");
		deleteUserLastnameCookie.setPath("/");
		System.out.println("path auf / gesetzt");
		response.addCookie(deleteUserIdCookie);
		response.addCookie(deleteUserFirstnameCookie);
		response.addCookie(deleteUserLastnameCookie);
		System.out.println("gelöscht");
	%>
	
	
	
	<%
		Cookie[] cookies2 = request.getCookies();
		if (cookies2 != null) {
			for (Cookie tempCookie : cookies2) {
				if ("JSP.userId".equals(tempCookie.getName())){
					userId = tempCookie.getValue();
				} else if ("JSP.userFirstname".equals(tempCookie.getName())){
					firstname = tempCookie.getValue();
				} else if ("JSP.userLastname".equals(tempCookie.getName())){
					lastname = tempCookie.getValue();
				}
			}
		} else {
			System.out.println("die Cookies sind leer");
		}
		userName = firstname + " " + lastname;
		System.out.println("Nach Logout: userId: " + userId);
		System.out.println("Nach Logout: userName: " + userName);
	%>

	<meta http-equiv="refresh" content="0; URL=../index.html">

</body>

</html>