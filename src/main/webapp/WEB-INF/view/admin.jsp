<%@ page language="java"%>
<%@ page import="com.rahul.models.LoginData"%>
<%@ page language="java" import="java.util.*, java.util.Base64"%>

<%
String userName = (String)session.getAttribute("username");
String userId = (String)session.getAttribute("userId");

if (userName == null) {
	 request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page</title>

</head>
<body>
	<div class="dashboard">
		<h2>
			Welcome Admin:
			<%= userName%></h2>
		<p>
			Your userId: <strong><%=userId%></strong>
		</p>

		<div>
			<form action="home" method="get">
				<input type="hidden" name="page" value="logout">
				<button type="submit">Logout</button>
			</form>
			<form action="home" method="get">
				<input type="hidden" name="page" , value="lobby">
				<button type="submit">Welcome page</button>
			</form>

			<form action="home" method="get">
				<input type="hidden" name="page" value="lobby">
				<button type="submit">Lobby</button>
			</form>
		</div>
	</div>

	<form action="LoginDataServlet" method="get">
		<button type="submit">Show Login Data</button>
	</form>


</body>
</html>