package com.rahul.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.*;

public class MyDAO {
	
	    private static final String DB_URL = "jdbc:mysql://localhost/collage";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "Rahul";
	    private Connection con;
	
	   private Connection getConnection() throws Exception {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	    }

/*	    public void addStudent(StudentPOJO s) throws Exception {
	    	String query = "INSERT INTO student (name, age, email) VALUES (?, ?, ?)";
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, s.getName());
	        ps.setInt(2, s.getAge());
	        ps.setString(3, s.getEmail());
	        ps.executeUpdate();
	        con.close();
	    }

		public List<StudentPOJO> getAllStudents() throws Exception {
			String query = "SELECT * FROM student";
			List<StudentPOJO> list = new ArrayList<>();
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {				
				list.add(new StudentPOJO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));				
			}
			con.close();
			return list;
		}*/

		public int registerNewUser( String name, String email, String password) throws Exception {
			Connection con = getConnection();
			 String sql = "INSERT INTO login_users (name, email, password, role) VALUES (?, ?, ?, ?)";
			 PreparedStatement stmt = con.prepareStatement(sql);
			 	stmt.setString(1, name);
	            stmt.setString(2, email);
	            stmt.setString(3, password); 
	            stmt.setString(4, "user");   // default role
	            
	            int rows = stmt.executeUpdate();
	            stmt.close();
	            con.close();
	            return rows;
	        

			
		}

		public String[] verifyLogin(String email, String password, String ipAddress) throws Exception {
			Connection con = getConnection();
			
			   PreparedStatement ps = con.prepareStatement(
		                "SELECT * FROM login_users WHERE email = ? AND password = ?"
		            );
			   
			    ps.setString(1, email);
	            ps.setString(2, password);
	            
	            ResultSet rs = ps.executeQuery();
	            
	            if (rs.next()) {
	   	                PreparedStatement update = con.prepareStatement(
	   	                	"UPDATE login_users SET last_login = NOW(), ip_address = ? WHERE email = ?"
	   	                		
	                );
	   	            // System.out.println("in DAO");
	   	    
	   	            update.setString(1, ipAddress);
	   	            update.setString(2, email);
	   	            update.executeUpdate();
	   	        
	                String name = rs.getString("name");
	                String role = rs.getString("role");	                
	                return new String[] {name, role};
	                
	            }
	            con.close();
	            return null;
		}

		public List<LoginData> getLoginData() throws Exception {
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
	
	
}
	
	

	
	


