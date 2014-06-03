package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.User;
import annotation.Controller;
import annotation.RequestMapping;
import database.DAO;


@Controller
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
	public String getError(String message) {
		
		return "redirect:/nyam/error?message=" + message;
	}
	@RequestMapping("/error")
	public String showError(HttpServletRequest request) {
		String message = request.getParameter("message");
		if(message == null || message == "")
			message = "잘못됬습니다.";
		
		String link = request.getParameter("link");
		if(link == null || link == "")
			link = "-1";
		
		request.setAttribute("messsage", message);
		request.setAttribute("link", link);
		
		return "error.jsp";
	}
	
}
