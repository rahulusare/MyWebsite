package com.rahul.servlets;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.rahul.models.MyDAO;

import java.sql.*;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        //String  ipAddress = request.getRemoteAddr();
        //System.out.println("in servlet l");
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
           // System.out.println("in IP");
        }

               
        MyDAO dao = new MyDAO();
        try {
        	String[] s = dao.verifyLogin(email, password, ipAddress);
        	HttpSession session = request.getSession();
        	
            if (s ==null) {
            	
            	request.setAttribute("errorMsg", "true");
            	RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            	rd.forward(request, response);
               

            } else if(s[1].equals("admin")) {
            	
            	 //System.out.println("Role"+ s[1]);            	
            	session.setAttribute("Info", s);
            	
            	response.sendRedirect("admin.jsp");
            }else if(s[1].equals("user")) {
            	session.setAttribute("Info", s);
 
            	response.sendRedirect("welcome.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

