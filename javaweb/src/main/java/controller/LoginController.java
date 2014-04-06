package controller;

import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(url="/login")
	public String showLoginPage() {
		System.out.println("loginPage");
		return "/login.jsp";
	}
}
