<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign-UP</title>

</head>
<body>

<div class="form-box">
    <h2>Sign-UP FORM</h2>

    <!-- Show success or error -->
    <div>
        <% if ("true".equals(request.getParameter("success"))) { %>
            <p style="color:green;">Registration successful!</p>
        <% } else if ("true".equals(request.getParameter("error"))) { %>
            <p style="color:red;">Registration failed. Try again.</p>
        <% } %>
    </div>

    <!-- Form -->
    <form action="register" method="post">
    	<div>
    		<label for="name">Name: </label>
    		<input type = "name" name = "name" placeholder= "Enter Name" required><br> 
    	</div>
    	<div>
    		<label for="Email">E-Mail: </label>
       		 <input type="email" name="email" placeholder="Enter Email" required><br>
    	</div>
    	<div>
    		<label for="Password">Password: </label>
        	<input type="password" name="password" placeholder="Password" min="4" maxlength="8" required><br>
    	</div>
        <input type="submit" value="Register">
    </form>

    <p>Already have an account? <a href="login">Login here</a></p>
</div>

<%
    String msg = (String) request.getAttribute("msg");
    if ("success".equals(msg)) {
%>
    <p>Registration successful!</p>
<%
    } else if ("error".equals(msg)) {
%>
    <p>Registration failed. Try again.</p>
<%
    }
%>


</body>
</html>

