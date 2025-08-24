package com.rahul.login;

import java.io.IOException;
import java.sql.SQLException;

import com.rahul.dao.MyDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name     = request.getParameter("name");
        String email    = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            int rows = MyDAO.registerNewUser(name, email, password);

            if (rows > 0) {
                response.sendRedirect("register?msg=success"); 
            } else {
                response.sendRedirect("register?msg=error");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("register?msg=error");
        } catch (Exception e) {
        	response.sendRedirect("register?msg=error");
			e.printStackTrace();
		}

    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	  String msg = request.getParameter("msg"); 
    	  request.setAttribute("msg", msg);
    	
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/register.jsp");
        rd.forward(request, response);
    }
}

