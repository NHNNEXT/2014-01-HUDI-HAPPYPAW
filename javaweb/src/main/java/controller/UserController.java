package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DateInfo;
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
public class UserController {
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
		@RequestMapping("/")
	public String showIndex(HttpServletRequest request, HttpSession session) {
		return showNyamHistory(request, session);
	}
	
	@RequestMapping("/nyamHistory")
	public String showNyamHistory(HttpServletRequest request,
			HttpSession session) {
		DAO db = DAO.getInstance();

		// 로그인 유무 확인
		String id = (String) session.getAttribute("users_id");

		if (id == null || id == "") {
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
		int currentMonth = cal.get(Calendar.MONTH);// 이번달 숫자-1을 찾아놓는다.
		int currentYear = cal.get(Calendar.YEAR);
		// 이전 달 & 다음달
		if (request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
			year = Integer.parseInt(request.getParameter("year"));

			month = month - 1;
			if (month < 0) {
				year -= 1;
				month = 11;
			} else if (month >= 12) {
				year += 1;
				month = 0;
			}
			// 다음달을 눌러도 현재 달 다음달로 가려고 하면 안되게 막아버림. 더 좋은 코드가 있을 듯. 연규느님.
			if (month > currentMonth && year >= currentYear) {
				year = currentYear;
				month = currentMonth;
			}
		} else {
			month = currentMonth;
			year = currentYear;
		}

		info = db.setDate(year, month);

		ArrayList<StampHistory> stampList = db.selectMonthHistory(id, year,
				month);
		HashMap<String, Integer> map = db.arrangeNyamHistory(stampList);

		request.setAttribute("nyamPerDay", map);
		request.setAttribute("dateinfo", info);


		return "/user/userNyamHistory.jsp";
	}

	

	@RequestMapping("/historyPeriod")
	// 위에 있는 함수랑 똑같은데...
	public String rankingHistoryPeriod(HttpServletRequest request,
			HttpSession session) {
		DAO dao = DAO.getInstance();
		String id = (String) session.getAttribute("users_id");
		ArrayList<HashMap<String, Object>> history = dao.getHistory(id);
		request.setAttribute("history", history);
		return "/user/historyPeriod.jsp";
	}

	@RequestMapping(value = "/rankingHistory", method = Method.GET)
	public String showRankingHistory(HttpServletRequest request)
			throws SQLException {
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
			month = calendar.get(Calendar.MONTH);
		}

		ArrayList<HashMap<String, String>> nyamRanking = dao.rankingHistory(
				year, month);// 서버단에서는 month -1안하고 전부다 처리함. 최연규 때문에 에러남. ㅋㅋ

		request.setAttribute("nyamRanking", nyamRanking);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		logger.info("" + nyamRanking);
		return "/user/rankingHistory.jsp";
	}

	@RequestMapping(value = "/ranking", method = Method.GET)
	public String showRanking(HttpServletRequest request, HttpSession session)
			throws SQLException {
		DAO dao = DAO.getInstance();
		int year, month;
		// 로그인 유무 확인
		String id = (String) session.getAttribute("users_id");

		if (id == null || id == "") {
			return "redirect:/nyam/app/login";
		}

		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		}

		ArrayList<HashMap<String, String>> nyamRanking = dao.rankingHistory(
				year, month - 1);

		request.setAttribute("nyamRanking", nyamRanking);
		request.setAttribute("id", id);

		return "/user/ranking.jsp";
	}

	@RequestMapping("/restaurantList")
	public String restaurantList(HttpServletRequest request)
			throws SQLException {
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restList = dao.manageRestaurant();
		request.setAttribute("restList", restList);
		return "/user/restaurantList.jsp";
	}

	@RequestMapping("/individual")
	public String checkUserIndividual(HttpServletRequest request,
			HttpSession session) {
		DAO dao = DAO.getInstance();
		if (!dao.checkLogin(session))
			return "redirect:/nyam/login";

		String users_id = request.getParameter("studentId");

		User user = dao.getUser(users_id);
		String name = user.getName();
		request.setAttribute("id", users_id);
		request.setAttribute("name", name);

		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		ArrayList<StampHistory> stampList = dao.selectMonthHistory(users_id,
				year, month);
		HashMap<String, Integer> map = dao.arrangeNyamHistory(stampList);
		request.setAttribute("nyamPerDay", map);

		DateInfo info = dao.setDate(year, month);
		request.setAttribute("dayOfMonth", info.getDayOfMonth());
		request.setAttribute("month", info.getMonth());
		request.setAttribute("week", info.getWeek());
		request.setAttribute("year", info.getYear());
		request.setAttribute("yoil", info.getYoil());
		logger.info(map.toString());
		return "/user/individual.jsp";

	}

}
