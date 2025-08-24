package com.rahul.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;

import com.rahul.dao.MyDAO;
import com.rahul.model.LoginData;


@WebServlet("/Controller")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	    maxFileSize = 1024 * 1024 * 10,      // 10 MB
	    maxRequestSize = 1024 * 1024 * 15    // 15 MB
	)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		InputStream inputStream = null;
		
		Part filePart = request.getPart("pro_pic");
		if (filePart != null) {
			//System.out.println("In Controler chec if");
	        inputStream = filePart.getInputStream();
	        
	    }
		
		
		try {
			HttpSession session = request.getSession();
			int id = (int) session.getAttribute("id");
			//System.out.println(id);
			int row = MyDAO.profilePic(id, inputStream);
			
			if(row != 0)
				System.out.println("pro_pic updated");
			else
				System.out.println("Not Updated");
			
			request.getRequestDispatcher("/WEB-INF/view/welcome.jsp").forward(request, response);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Came");
		}
	}
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		        throws IOException {
		        
		 LoginData ld = (LoginData) request.getSession().getAttribute("UserInfo");

	        if (ld != null && ld.getProPic() != null) {
	            byte[] imageData = ld.getProPic();
		        
		        if (imageData != null) {
		            response.setContentType("image/jpeg"); // or image/png
		            response.setContentLength(imageData.length);
		            response.getOutputStream().write(imageData);
		        }
		    }

	 }
}	
