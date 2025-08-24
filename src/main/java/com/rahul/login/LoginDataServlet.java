package com.rahul.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

import com.rahul.model.LoginData;
import com.rahul.dao.MyDAO;

/**
 * Servlet implementation class LoginDataServlet
 */
@WebServlet("/LoginDataServlet")
public class LoginDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<LoginData> loginData = MyDAO.getLoginData();
			request.setAttribute("loginList", loginData);
			request.getRequestDispatcher("LoginData.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
