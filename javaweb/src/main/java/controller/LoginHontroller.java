package controller;

import annotation.Hontroller;
import annotation.RequestHappying;

@Hontroller
public class LoginHontroller {
	
	@RequestHappying("/login")
	public String loginPage(){
		return "login.jsp";
	}
	
	@RequestHappying
	public String logoutPage(){
		return "redirect:/login";
	}
	
}
