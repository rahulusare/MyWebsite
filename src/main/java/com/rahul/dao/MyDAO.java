package com.rahul.dao;

import java.util.ArrayList;
import java.util.List;

import com.rahul.model.LoginData;

import java.io.InputStream;
import java.sql.*;

public class MyDAO {
	
	    private static final String DB_URL = "jdbc:mysql://localhost/collage";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "Rahul";
	
	   private static Connection getConnection() throws Exception {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	    }
	   
	   public static int registerNewUser(String name, String email, String password) throws Exception {
		    Connection con = getConnection();

		  
		    String sql = "INSERT INTO login_users (username, email, password, role) VALUES (?, ?, ?, ?)";
		    PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    stmt.setString(1, name);
		    stmt.setString(2, email);
		    stmt.setString(3, password);
		    stmt.setString(4, "user");

		    int rows = stmt.executeUpdate();

		    int Id = -1;
		    if (rows > 0) {
		        ResultSet rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		            Id = rs.getInt(1);
		        }
		        rs.close();

		     
		        String userId = "USR" + String.format("%04d", Id);

		  
		        String sql2 = "INSERT INTO players_data (login_user_id, userid) VALUES (?, ?)";
		        PreparedStatement stmt2 = con.prepareStatement(sql2);
		        stmt2.setInt(1, Id);
		        stmt2.setString(2, userId);
		        stmt2.executeUpdate();
		        stmt2.close();
		    }

		    stmt.close();
		    con.close();

		    return rows;
		}
		
	   
	   public static LoginData verifyLogin(String email, String password, String ipAddress) throws Exception {
		    Connection con = getConnection();

		    String sql = "SELECT l.id, l.username, l.role, p.userid " +
		                 "FROM login_users l " +
		                 "LEFT JOIN players_data p ON l.id = p.login_user_id " +
		                 "WHERE l.email = ? AND l.password = ?";

		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, email);
		    ps.setString(2, password);

		    ResultSet rs = ps.executeQuery();

		    if (rs.next()) {
	
		        PreparedStatement update = con.prepareStatement(
		            "UPDATE login_users SET last_login = NOW(), ip_address = ? WHERE email = ?"
		        );
		        update.setString(1, ipAddress);
		        update.setString(2, email);
		        update.executeUpdate();
		        update.close();

		     
		        int id = rs.getInt("id");
		        String name = rs.getString("username");  
		        String role = rs.getString("role");
		        String userId = rs.getString("userid");
		        //int loginUserId = rs.getInt("login_user_id");

		        con.close();


		        return new LoginData(id, name, role, userId);
		    }

		    con.close();
		    return null;
		}


		public static List<LoginData> getLoginData() throws Exception {
			List<LoginData> loginList = new ArrayList<>();
			String query = "SELECT * FROM login_users";
			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				LoginData loginData = new LoginData();
				
				loginData.setId(rs.getInt("id"));
				loginData.setName(rs.getString("name"));
				loginData.setEmail(rs.getString("email"));
				loginData.setPassword(rs.getString("password"));
				loginData.setRole(rs.getString("role"));
				loginData.setLoginTime(rs.getTimestamp("last_login"));
				loginData.setCreatedAt(rs.getTimestamp("created_at"));
				loginData.setIpAddress(rs.getString("ip_address"));
				
				loginList.add(loginData);
			}
			con.close();
			ps.close();
			
			//System.out.println(loginList.toString());
				
			return loginList;
		}
		
		
		public static int profilePic(int id, InputStream inputStream) throws Exception {
			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(
					"UPDATE login_users SET pro_pic=? WHERE id=?"
					);
			
			
			ps.setBlob(1, inputStream);
			ps.setInt(2, id);
			//System.out.println("In MyDAO if");
			int row = ps.executeUpdate();
			return row;
		}
		
		
	
	
}
	
	

	
	


