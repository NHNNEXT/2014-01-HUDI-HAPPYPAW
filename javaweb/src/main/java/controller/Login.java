package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO;

public class Login extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("users_id");
		if(id == null || id.equals("")){
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("./nyamHistory");
		}
		

	}
}
