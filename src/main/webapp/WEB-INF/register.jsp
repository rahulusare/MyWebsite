<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>

<div class="form-box">
    <h2>Register</h2>

    <!-- Show success or error -->
    <div>
        <% if ("true".equals(request.getParameter("success"))) { %>
            <p style="color:green;">Registration successful!</p>
        <% } else if ("true".equals(request.getParameter("error"))) { %>
            <p style="color:red;">Registration failed. Try again.</p>
        <% } %>
    </div>

    <!-- Form -->
    <form action="RegisterServlet" method="post">
    	<input type = "name" name = "name" placeholder= "Name" required><br> 
        <input type="email" name="email" placeholder="Email" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <input type="submit" value="Register">
    </form>

    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</div>

</body>
</html>

