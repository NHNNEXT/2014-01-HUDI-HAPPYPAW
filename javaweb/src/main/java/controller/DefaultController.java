package controller;

import javax.servlet.http.HttpSession;

import model.User;
import database.DAO;

public class DefaultController {
	public String goLoginPage() {
		return "redirect:/nyam/login";
	}
	public User getLoginuser(HttpSession session) {
		DAO db = DAO.getInstance();
		String id = (String) session.getAttribute("users_id");
		if(id == null || id.equals(""))
			return null;
		User user = db.getUser(id);
		
		return user;
		
	}
}
