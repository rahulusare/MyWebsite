<%@ page language="java" %>
<%

String[] s = (String[]) session.getAttribute("Info");

if (s == null || !"admin".equals(s[1])) {
    response.sendRedirect("accessDenied.jsp");
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
    <h2>Welcome Admin: <%= s[0] %></h2>
    <p>Your role: <strong><%= s[1] %></strong></p>

    <div class="button-group">
        <a href="logout.jsp" class="btn logout"> Logout</a><br>
        <a href="Videos.jsp" class= "Videos-btn">Videos</a>
        <a href="welcome.jsp">Welcome Page</a>
    </div>
</div>

		<form action="LoginDataServlet" method="get">
  				  <button type="submit">Show Login Data</button>
     	</form>
		

</body>
</html>