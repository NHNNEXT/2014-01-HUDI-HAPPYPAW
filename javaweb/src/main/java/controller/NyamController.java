package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.DateInfo;
import model.NyamList;
import model.Restaurant;
//import model.Restaurant;
import model.StampHistory;
import model.User;
import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class NyamController {
	
	@RequestMapping("/nyamHistory")
	public String showNyamHistory(HttpServletRequest request, HttpSession session) {
		DAO db = DAO.getInstance();
		String id =(String) session.getAttribute("users_id");
		if(id ==null || id==""){
			return "redirect:/nyam/app/login";
		}
		
		User user = db.getUser(id);
		String name = user.getName();
		request.setAttribute("session", id);
		request.setAttribute("name", name);
		
		ArrayList<StampHistory> stampList = db.selectMonthHistory(id);
		HashMap<String, Integer> map = db.arrangeNyamHistory(stampList);
		request.setAttribute("nyamPerDay", map);
		
		DateInfo info = db.setDate();
		request.setAttribute("dayOfMonth", info.getDayOfMonth());
		request.setAttribute("month",info.getMonth());
		request.setAttribute("week",info.getWeek());
		request.setAttribute("year",info.getYear());
		request.setAttribute("yoil",info.getYoil());

		return "nyamHistory.jsp";
	}
	
	@RequestMapping("/admin/nyamHistory")
	public String showNyamList(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<NyamList> nyamList = dao.adminNyamHistory();
		request.setAttribute("nyamList", nyamList);
		return "/admin/nyamHistory.jsp";
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
		dao.exportExcel(servlet.getServletContext().getRealPath(""));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "export_" + date.format(cal.getTime()) + ".xls";
		return "redirect:/nyam/" + fileName;
	}
	
	@RequestMapping("/admin/manageRest")
	public String manageRest(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restList = dao.manageRestaurant();
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
	
	@RequestMapping("/admin/individual")
	public String checkIndividual(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String users_id = request.getParameter("studentId");
		ArrayList<StampHistory> history =  dao.selectMonthHistory(users_id);
		request.setAttribute("individualHistory", history);
		return "/admin/individual.jsp";
	}
	
	@RequestMapping("/admin/eachRestaurant")
	public String checkRestaurant(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String id = request.getParameter("restaurantId");
		HashMap<String, Integer> map = dao.checkEachRestaurant(id);
		request.setAttribute("map", map);

		return "/admin/eachRestaurant.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
