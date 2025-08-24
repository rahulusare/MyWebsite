package com.rahul.model;


import java.sql.Timestamp;

public class LoginData {
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	private int id;
	private String userId;
	private String name;
	private String email;
	private String password;
	private String role;
	private Timestamp loginTime;
	private Timestamp createdAt;
	private String ipAddress;
	private byte[] proPic;
	
	public byte[] getProPic() {
		return proPic;
	}



	public void setProPic(byte[] proPic) {
		this.proPic = proPic;
	}



	public LoginData(int id, String name, String role, String userId){
		this.id = id;
		this.name = name;
		this.role = role;
		this.userId = userId;
	}


	
	public LoginData() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}



	public String getUserID() {
		return userId;
	}



	public void setUserID(String userID) {
		this.userId = userID;
	}
	
}
