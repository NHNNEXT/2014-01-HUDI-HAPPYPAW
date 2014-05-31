package controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DateInfo;
import model.NyamList;
import model.Restaurant;
import model.StampHistory;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestMapping.Method;
import database.DAO;

@Controller
public class AdminController {
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	@RequestMapping(value = "/admin/nyamHistory", method = Method.GET)
	public String showNyamList(HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		int year, month;

		// original source
		// year = Integer.parseInt(request.getParameter("year"));
		// month = Integer.parseInt(request.getParameter("month"));
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}
		ArrayList<NyamList> nyamList = dao.adminNyamHistory(year, month - 1);
		request.setAttribute("nyamList", nyamList);
		return "/admin/nyamHistory.jsp";
	}

	@RequestMapping("/admin/restaurantHistory")
	public String showRestaurantList(HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restaurant = dao.restaurantHistory();
		request.setAttribute("restList", restaurant);
		return "/admin/restaurantHistory.jsp";
	}

	@RequestMapping("/admin/exportExcel")
	public String exportFile(HttpServlet servlet) {
		DAO dao = DAO.getInstance();
		dao.exportExcel(servlet.getServletContext().getRealPath(""));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "export_" + date.format(cal.getTime()) + ".xls";
		return "redirect:/nyam/" + fileName;
	}

	@RequestMapping("/admin/manageRest")
	public String manageRest(HttpServletRequest request) throws SQLException {
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restList = dao.manageRestaurant();
		request.setAttribute("restList", restList);
		String address = "https://chart.googleapis.com/chart?cht=qr&chs=150x150&chl=";
		request.setAttribute("address", address);

		return "/admin/manageRest.jsp";
	}

	@RequestMapping("/admin/renewQr")
	public String renewQr(HttpServletRequest request) throws SQLException {
		DAO dao = DAO.getInstance();
		String no = request.getParameter("restaurantNo");
		dao.renewQrcode(no);
		return "redirect:/nyam/admin/manageRest";
	}

	@RequestMapping("/admin/individual")
	public String checkIndividual(HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		String users_id = request.getParameter("studentId");
		if(users_id == "" || users_id == null)
			return "redirect:/nyam/admin/nyamHistory";
		
		User user = dao.getUser(users_id);
		String name = user.getName();
		request.setAttribute("id", users_id);
		request.setAttribute("name", name);

		ArrayList<StampHistory> stampList = dao.selectMonthHistory(users_id,
				2014, 11);// 임의의 숫자를 넣었습니다. 고쳐야됨.
		HashMap<String, Integer> map = dao.arrangeNyamHistory(stampList);
		request.setAttribute("nyamPerDay", map);

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(cal.YEAR);
		DateInfo info = dao.setDate(year, month);
		request.setAttribute("dateInfo", info);


		return "/admin/individual.jsp";
	}

	@RequestMapping("/admin/eachRestaurant")
	public String checkRestaurant(HttpServletRequest request)
			throws SQLException {
		DAO dao = DAO.getInstance();
		String id = request.getParameter("restaurantId");
		Restaurant rest = dao.getRestaurant(id);
		if (rest == null)
			return "redirect:/nyam/admin/restaurantHistory";
		HashMap<String, Integer> map = dao.checkEachRestaurant(id);
		request.setAttribute("map", map);
		request.setAttribute("restaurant", rest.getName());
		return "/admin/eachRestaurant.jsp";
	}

	@RequestMapping(value = "/admin/historyList")
	// 으악........
	public String showHistoryList(HttpServletRequest request,
			HttpSession session) {
		DAO dao = DAO.getInstance();
		String id = (String) session.getAttribute("users_id");
		ArrayList<HashMap<String, Object>> history = dao.getHistory(id);
		request.setAttribute("history", history);
		return "/admin/historyList.jsp";
	}
}
