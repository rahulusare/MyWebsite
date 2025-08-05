<%@ page language="java" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Expires", "0");

String[] userInfo = (String[]) session.getAttribute("Info");

if (userInfo == null) {
	response.sendRedirect("index.jsp");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <!--<link rel="stylesheet" href="welcome.css">  -->
    
</head>
<body>

<div class="welcome-box">
    <h2>Welcome, <%= userInfo[0] %>!</h2>
    <p>Your role: <%=  userInfo[1] %></p>
    <a href= "logout.jsp" class= "logout-btn">Logout</a><br>
    <a href= "Videos.jsp" class= "Videos-btn">Videos</a>
    <!-- My Linkedln -->
    <a href= "https://www.linkedin.com/in/rahul-usare/" target="_blank">Linkedin</a>
    <!-- GitHub -->
    <a href= "https://github.com/rahulusare" target= "_blank">GitHub</a>
    <!-- Call Me -->
    <a href= "tel:7823885887" class= "call-btn">CallMe</a>
    <!-- Download My Resume -->
    <a href= "resources/resume/RahulUsare.pdf">MyResume</a>
    <!-- mail me -->
    <a href= "mailto:rahulusare110@gmail.com">MailHere</a>
    <!-- Whatsaap Me -->
    <a href= "https://wa.me/+917823885887?text=Hi%20Rahul">Whatsaap</a>
    
</div>

</body>
</html>
