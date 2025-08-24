package com.rahul.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = request.getParameter("page");
        

        if ("lobby".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/games/lobby.jsp").forward(request, response);
        } else if ("profile".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        } else if("welcome".equals(page)){
            request.getRequestDispatcher("/WEB-INF/view/welcome.jsp").forward(request, response);
        }else if("logout".equals(page)) {
        	request.getSession().invalidate();
        	request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
