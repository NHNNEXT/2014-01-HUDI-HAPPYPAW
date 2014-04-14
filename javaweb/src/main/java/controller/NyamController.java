package controller;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.StampHistory;
import model.User;
import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class NyamController {
	@RequestMapping("/nyamHistory")
	public String showNyamHistory(HttpServletRequest request, HttpSession session) {
		DAO db = DAO.getInstance();
		//세션에서 아이디를 못찾으면 로그인 페이지로 퉁 
		String id =(String) session.getAttribute("users_id");
		if(id ==null || id==""){
			return "redirect:/nyam/app/login";
		}
		//아이디가 있을 때는 월별 히스토리를 검색해서 결과를 보여준다. 
		ArrayList<StampHistory> stampList = db.selectMonthHistory(id);
		request.setAttribute("record", stampList);

		return "nyamHistory.jsp";
	}

}
