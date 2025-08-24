<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Login Data</title>
   </head>
   <body>
<%@ page import="java.util.*, com.rahul.models.LoginData" %>
<%
    List<LoginData> loginList = (List<LoginData>) request.getAttribute("loginList");

   LoginData userInfo = (LoginData) session.getAttribute("UserInfo");
  	
	if (userInfo == null || !"admin".equals(userInfo.getRole())) {
	    response.sendRedirect("accessDenied.jsp");
	    return;
	}

%>
<h2>All Login Records</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Role</th>
        <th>Login Time</th>
         <th>Created Time</th>
         <th>IP Address</th>
         <th>Pro_pic</th>
    </tr>
    <%
        if (loginList != null) {
            for (LoginData login : loginList) {
    %>
    <tr>
        <td><%= login.getId() %></td>
        <td><%= login.getName() %></td>
        <td><%= login.getEmail() %></td>
        <td><%= login.getPassword() %></td>
        <td><%= login.getRole() %></td>
        <td><%= login.getLoginTime() %></td>
        <td><%= login.getCreatedAt()%></td>
        <td><%= login.getIpAddress()%></td>
        <td><%= login.getProPic() %> </td>
    </tr>
    <%
            }
        }
    %>
</table>
	<a href="admin.jsp"><button>Go Back</button></a>

 </body>
</html>
