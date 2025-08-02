package com.rahul.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rahul.models.MyDAO;
import com.rahul.models.StudentPOJO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	MyDAO sdao = new MyDAO();
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();

		String action =  request.getParameter("action");	
		
		System.out.println("Action = " + action);
 
		try {
			if("add".equals(action)) {
				
				String name = request.getParameter("name");
				int age =   Integer.parseInt(request.getParameter("Age"));
				String email = request.getParameter("email");
				
				sdao.addStudent(new StudentPOJO(name, age, email));
				response.sendRedirect("Index.html");
				
			}else if("view".equals(action)) {
				List<StudentPOJO> list = sdao.getAllStudents();
				request.setAttribute("studentList", list);
				request.getRequestDispatcher("StudentView.jsp").forward(request, response);

			
			}
		} catch (Exception e) {
			e.printStackTrace();
			 response.getWriter().println("Exception: " + e.getMessage());
		}
			
		
	}
}
