<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	display: flex;
	align-items: center;
}

fieldsett {
	background: #fff;
	border-radius: 15px;
}

.formbox h2 {
	text-align: center;
	margin-bottom: 20px;
	color: #333;
}

label {
	display: block;
	margin: 10px 0 5px;
	font-weight: bold;
	color: #555;
}

#inp {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
}

.error {
	color: red;
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<body>
	<fieldset>
		<div class="formbox">
			<h2>Login</h2>

			<%
			String errorMsg = (String) request.getAttribute("error");
			if ("true".equals(errorMsg)) {
			%>
			<p class="error">Invalid email or password.</p>
			<%
			}
			%>


			<form action="login" method="post">
				<label for="username">E-mail:</label> 
				<input type="email" name="email" placeholder="Email" id="inp" required> 
				<label for="password">Password:</label>
				<input type="password" name="password" placeholder="Password" id="inp" required>
				<input type="submit" value="Login">
			</form>

			<p>
				Don't have an account? <a href="register">Sign-Up</a>
			</p>
		</div>
	</fieldset>
</body>
</html>
