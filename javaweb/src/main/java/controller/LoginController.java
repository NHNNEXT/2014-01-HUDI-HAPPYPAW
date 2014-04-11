package controller;

import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String loginPage(){
		return "login.jsp";
	}
	
	@RequestMapping
	public String logoutPage(){
		return "redirect:/login";
	}
	
}
