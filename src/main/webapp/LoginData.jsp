<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
   <body>
<%@ page import="java.util.*, com.rahul.models.LoginData" %>
<%
    List<LoginData> loginList = (List<LoginData>) request.getAttribute("loginList");

   String[] s = (String[]) session.getAttribute("Info");
  	
	if (s == null || !"admin".equals(s[1])) {
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
    </tr>
    <%
            }
        }
    %>
</table>
 </body>
</html>
