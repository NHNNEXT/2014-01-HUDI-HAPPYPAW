package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.NyamList;
import model.Restaurant;
//import model.Restaurant;
import model.StampHistory;
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
	
	@RequestMapping("/admin/nyamHistory")
	public String showNyamList(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<NyamList> nyamList = dao.adminNyamHistory();
		
		
		//istream.
		
		request.setAttribute("nyamList", nyamList);
		return "/admin/nyamHistory.jsp";//admin 폴더안에  있는데 이렇게 주소 쓰는거 맞나?
	}
	
	@RequestMapping("/admin/restaurantHistory")
	public String showRestaurantList(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restaurant = dao.restaurantHistory();
		request.setAttribute("restList", restaurant);
		return "/admin/restaurantHistory.jsp";
	}
	
	@RequestMapping("/admin/exportExcel")
	public String exportFile(HttpServlet servlet){
		DAO dao = DAO.getInstance();
		System.out.println("requestmapping ok");
		dao.exportExcel(servlet.getServletContext().getRealPath(""));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "export_" + date.format(cal.getTime()) + ".xls";
		return "redirect:/nyam/" + fileName;
	}
	
	@RequestMapping("/admin/manageRest")
	public String manageRest(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restList = dao.manageRest();
		request.setAttribute("restList", restList);
		String address = "https://chart.googleapis.com/chart?cht=qr&chs=150x150&chl=";
		request.setAttribute("address", address);
		
		return "/admin/manageRest.jsp";
	}
	
	@RequestMapping("/admin/renewQr")
	public String renewQr(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String no = request.getParameter("restaurantNo");
 		dao.renewQrcode(no);
		return "redirect:/nyam/admin/manageRest";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
