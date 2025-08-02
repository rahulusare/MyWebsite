<%@ page language="java" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Expires", "0");

String[] userInfo = (String[]) session.getAttribute("Info");

if (userInfo == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <link rel="stylesheet" href="welcome.css">
    
</head>
<body>

<div class="welcome-box">
    <h2>Welcome, <%= userInfo[0] %>!</h2>
    <p>Your role: <%=  userInfo[1] %></p>
    <a href="logout.jsp" class="logout-btn">Logout</a><br>
    <a href="Videos.jsp" class= "Videos-btn">Videos</a>
</div>

</body>
</html>
