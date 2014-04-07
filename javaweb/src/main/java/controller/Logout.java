package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.User;

public class Logout {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO db = DAO.getInstance();
		
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}

	}
}
