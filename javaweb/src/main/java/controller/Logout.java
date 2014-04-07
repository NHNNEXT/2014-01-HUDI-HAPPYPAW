package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.User;

public class Logout {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO db = DAO.getInstance();
		
		
		HttpSession session = request.getSession();
		String users_id = (String) session.getAttribute("users_id");
		if(users_id == null || users_id.equals("")){
			//로그인 과정 
			String jspId = request.getParameter("id");//jsp에서 보낸 id
			String jspPs = request.getParameter("password");
			User user = db.getUser(jspId);
			if(user.checkPs(jspPs)){
				session.setAttribute("users_id", jspId);
				response.sendRedirect("./nyamHistory");
				System.out.println("login complete :"+ jspId);
			} else {
				response.sendRedirect("./login");
			}
		} else {
			response.sendRedirect("./nyamHistory");
		}
		

	}
}
