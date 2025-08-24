package com.rahul.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/game")
public class GameController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gameId = request.getParameter("gameId");
		String mode = request.getParameter("mode");
		request.setAttribute("gameId", gameId);
		if("ttt".equals(mode)) {
			request.getRequestDispatcher("/WEB-INF/games/tictactoe.jsp").forward(request, response);
			
		}else if("indianludo".equals(mode)) {
			request.getRequestDispatcher("/WEB-INF/games/IndianLudo.jsp").forward(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
