<%@ page language="java"%>
<%@ page import="com.rahul.model.LoginData"%>
<%@ page language="java" import="java.util.*, java.util.Base64"%>
<%
String userName = (String)session.getAttribute("username");
String userId = (String)session.getAttribute("userId");


if (userName == null) {
	//System.out.println("In IF");
	 request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome Page</title>

<style type="text/css">
	body{
		background-color: aqua;
		align-items: center;
	}
	
	
</style>


</head>
<body>
	<form action="Controller" method="post" enctype="multipart/form-data">
		<input type="file" name="pro_pic" accept="image/*" required>
		<button type="submit">Upload</button>
	</form>
	<div class="welcome-box">
		<h2>Welcome, <%=userName%>!</h2>
		<p>
			Your userId
			<%=userId%></p>
		<form action="home" method="get">
			<input type="hidden" name="page" value="logout">
			<button type="submit">Logout</button>
		</form>
		<form action="home" method="get">
    		<input type="hidden" name="page" value="lobby">
   			 <button type="submit">Lobby</button>
		</form>
	</div>
		
</body>
</html>
