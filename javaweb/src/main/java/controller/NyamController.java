package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.DAO;
import model.DateInfo;
import model.NyamList;
import model.Restaurant;
//import model.Restaurant;
import model.StampHistory;
import model.User;
import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestMapping.Method;

@Controller
public class NyamController {
	private static Logger logger = LoggerFactory.getLogger(NyamController.class);
	
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

		
		DateInfo info;
		int month;
		int year;
		
		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);//이번달 숫자-1을 찾아놓는다. 
		int currentYear = cal.get(Calendar.YEAR);
		//이전 달 & 다음달 
		if(request.getParameter("month") != null ){
			month =Integer.parseInt( request.getParameter("month"));
			year = Integer.parseInt(request.getParameter("year"));
			
			if(month < 0){
				year -= 1;
				month = 11;
			} else if(month >11){
				year +=1;
				month = 0;
			}
			//다음달을 눌러도 현재 달 다음달로 가려고 하면 안되게 막아버림. 더 좋은 코드가 있을 듯. 연규느님.
			if(month > currentMonth){
				year = currentYear;
				month = currentMonth;
			}
		}else {

			month = currentMonth; 
			year = currentYear;
		}
		
		info = db.setDate(year, month);
		
		ArrayList<StampHistory> stampList = db.selectMonthHistory(id, year, month);
		HashMap<String, Integer> map = db.arrangeNyamHistory(stampList);
		
		request.setAttribute("nyamPerDay", map);
		request.setAttribute("dayOfMonth", info.getDayOfMonth());
		request.setAttribute("month",info.getMonth());
		request.setAttribute("week",info.getWeek());
		request.setAttribute("year",info.getYear());
		request.setAttribute("yoil",info.getYoil());
		
		return "nyamHistory.jsp";
	}
	
	@RequestMapping(value = "/admin/nyamHistory", method= Method.GET)
	public String showNyamList(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		int year, month;
		
		//original source
		//year = Integer.parseInt(request.getParameter("year"));
		//month = Integer.parseInt(request.getParameter("month"));
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		ArrayList<NyamList> nyamList = dao.adminNyamHistory(year, month-1);
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
		
		User user = dao.getUser(users_id);
		String name = user.getName();
		request.setAttribute("id", users_id);
		request.setAttribute("name", name);
		
		ArrayList<StampHistory> stampList =  dao.selectMonthHistory(users_id,2014, 11);//임의의 숫자를 넣었습니다. 고쳐야됨. 
		HashMap<String, Integer> map = dao.arrangeNyamHistory(stampList);
		request.setAttribute("nyamPerDay", map);
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.YEAR;
		DateInfo info = dao.setDate(year, month);
		request.setAttribute("dayOfMonth", info.getDayOfMonth());
		request.setAttribute("month",info.getMonth());
		request.setAttribute("week",info.getWeek());
		request.setAttribute("year",info.getYear());
		request.setAttribute("yoil",info.getYoil());
		
		return "/admin/individual.jsp";
	}
	
	@RequestMapping("/admin/eachRestaurant")
	public String checkRestaurant(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String id = request.getParameter("restaurantId");
		Restaurant rest = dao.getRestaurant(id);
		if(rest == null)
			return "redirect:/nyam/admin/restaurantHistory";
		HashMap<String, Integer> map = dao.checkEachRestaurant(id);
		request.setAttribute("map", map);
		request.setAttribute("restaurant", rest.getName());
		return "/admin/eachRestaurant.jsp";
	}
	
	@RequestMapping("/admin/historyList")
	public String showHistoryList(HttpServletRequest request, HttpSession session){
		DAO dao = DAO.getInstance();
		
		HashMap<String, ArrayList<String>> history = dao.getHistory("121001");
		
		request.setAttribute("history", history);
		return "/admin/historyList.jsp";
	}
	
	@RequestMapping("/historyPeriod")
	public String rankingHistoryPeriod(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		HashMap<String, ArrayList<String>> history = dao.getHistory("121001");
		request.setAttribute("history", history);
		return "historyPeriod.jsp";
	}
	
	@RequestMapping(value = "/rankingHistory", method= Method.GET)
	public String showRankingHistory(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		int year, month;
		//original source
		//year = Integer.parseInt(request.getParameter("year"));
		//month = Integer.parseInt(request.getParameter("month"));
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		
		ArrayList<HashMap<String, String>> nyamRanking= dao.rankingHistory(year, month-1);
		
		request.setAttribute("nyamRanking", nyamRanking);
		logger.info(""+nyamRanking);
		return "rankingHistory.jsp";
	}
	
	@RequestMapping(value = "/ranking", method= Method.GET)
	public String showRanking(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		int year, month;
		//original source
		//year = Integer.parseInt(request.getParameter("year"));
		//month = Integer.parseInt(request.getParameter("month"));
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		
		ArrayList<HashMap<String, String>> nyamRanking= dao.rankingHistory(year, month-1);
		
		request.setAttribute("nyamRanking", nyamRanking);
		
		return "ranking.jsp";
	}
	
	
	
	
	
	
	
	
	
}
