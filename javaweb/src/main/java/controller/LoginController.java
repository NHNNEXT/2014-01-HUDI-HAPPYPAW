package controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import annotation.Controller;
import annotation.RequestMapping;
import database.DAO;

@Controller
public class LoginController extends DefaultController{
	
	@RequestMapping("/login")
	public String loginPage(HttpServletRequest request, HttpSession session){
		String id = (String) session.getAttribute("users_id");
		String error = (String) session.getAttribute("error");
		if(error == null)
			error = "";
		
		if(error != "")
				session.removeAttribute("error");
			
		if(id == null || id.equals("")){
			request.setAttribute("error", error);
			return "/user/login.jsp";
		} else {
			//http://localhost/nyam/app/nyamHistory
			return "redirect:./ranking";
		}
		
	}
	
	@RequestMapping("/logout")
	public String logoutPage(HttpSession session){
		if(session != null){
			session.invalidate();
		}
		return goLoginPage();
	}
	
	@RequestMapping("/login_check")
	public String loginCheck(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		DAO db = DAO.getInstance();
		String users_id = (String) session.getAttribute("users_id");
		if(users_id == null || users_id.equals("")){
			//로그인 과정 
			String jspId = request.getParameter("id");//jsp에서 보낸 id
			String jspPs = request.getParameter("password");
			User user = db.getUser(jspId);
			
			if(user == null) {
			
				//request.getRequestDispatcher("").
				//response.setStatus(302); //this makes the redirection keep your requesting method as is.
				//response.addHeader("Location", "http://address.to/redirect");
				session.setAttribute("error", "아이디가 없거나 비밀번호가 틀렸습니다.");
				return goLoginPage();
			} else if(user.checkPs(jspPs)){
				session.setAttribute("users_id", jspId);
				
				System.out.println("login complete :"+ jspId);
				return "redirect:/nyam/ranking";
			} else {
				return "redirect:/nyam/ranking";
			}
		} else {
			return "redirect:/nyam/ranking";
		}
	}

	
}
