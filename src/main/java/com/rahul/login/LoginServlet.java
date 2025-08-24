package com.rahul.login;

import java.io.IOException;

import com.rahul.model.LoginData;
import com.rahul.dao.MyDAO;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

               
        try {
        	LoginData userData = MyDAO.verifyLogin(email, password, ipAddress);
        	HttpSession session = request.getSession();
        	
            if (userData == null) {
            	//System.out.println("In Login if");
            	
            	request.setAttribute("error", "true");
            	 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
            	rd.forward(request, response);
               

            } else if(userData.getRole().equals("admin")) {
            	
            	 //System.out.println("Role"+ s[1]);            	
            	session.setAttribute("UserInfo",userData );
            	session.setAttribute("userId", userData.getUserID());
            	session.setAttribute("username", userData.getName());
            	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/admin.jsp");
            	rd.forward(request, response);
            	//response.sendRedirect("admin.jsp");
            }else if(userData.getRole().equals("user")){
            	session.setAttribute("id", userData.getId());
            	session.setAttribute("UserInfo", userData);
            	session.setAttribute("userId", userData.getUserID());
            	session.setAttribute("username", userData.getName());
            	
            	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/welcome.jsp");
            	rd.forward(request, response);
 
            	//response.sendRedirect("home?page=welcome");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
	    rd.forward(request, response);
	}

}

