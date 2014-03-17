package test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Join_email_list extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String first = req.getParameter("first");
		String last = req.getParameter("last");
		String email = req.getParameter("email");
		
		
		req.setAttribute("first", first);
		req.setAttribute("last", last);
		req.setAttribute("email", email);
		//req.removeAttribute("email");//이건 왜 안 지워지지 
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("join_email_list.jsp");
		dispatcher.forward(req, resp);
	}
}
