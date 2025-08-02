<%@ page language="java" %>
<%
    String error = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

<div class="form-box">
    <h2>Login</h2>

    <% if ("true".equals(error)) { %>
        <p class="error">Invalid email or password.</p>
    <% } %>

    <form action="LoginServlet" method="post">
        <input type="email" name="email" placeholder="Email" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <input type="submit" value="Login">
    </form>

    <p>Don't have an account? <a href="register.jsp">Register</a></p>
    
</div>

</body>
</html>

