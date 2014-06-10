package controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Board;
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
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		
		Restaurant rest = dao.getRestaurant(id);
		if (rest == null)
			return "redirect:/nyam/admin/restaurantHistory";
		int intMonth = Integer.parseInt(month)+1;
		
		ArrayList<HashMap<String, String>> array= dao.checkEachRestaurant(id, Integer.toString(intMonth), year);
		request.setAttribute("array", array);
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
	
	@RequestMapping("/admin/addRestaurant")
	public String addRest(){
		return "/admin/addRest.jsp";
	}
	
	@RequestMapping("/admin/sendRestInfo")
	public String insertRestInfo(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String name = request.getParameter("restName");
		String desc = request.getParameter("desc");
		String location = request.getParameter("location");
		
		dao.insertRest(name, desc, location);
		return "redirect:/nyam/admin/manageRest";
	}
	
	@RequestMapping("/admin/restPeriod")
	public String showPeriod(HttpSession session, HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String id = (String) session.getAttribute("users_id");
		System.out.println(id);
		String restId = request.getParameter("restaurantId");
		ArrayList<HashMap<String, Object>> list = dao.getHistory(id);
		logger.debug(list.toString());
		request.setAttribute("list", list);
		request.setAttribute("restId", restId);
		return "/admin/restPeriod.jsp";
	}
	
	@RequestMapping("/admin/boardList")
	public String showBoardList(HttpServletRequest request, HttpSession session){
		DAO dao = DAO.getInstance();
		
		String sPage = request.getParameter("page");
		int page = 1;
		try {
			if(sPage != null && !sPage.equals(""))
				page = Integer.parseInt(sPage);

			if (page < 1)
				page = 1;
			int totalCount = dao.boardCount();
			if (totalCount <= (page - 1) * 15)
				page = (int) (totalCount / 15) + 1;
			
			request.setAttribute("page", page);
			request.setAttribute("totalCount", totalCount);
			
		} catch (Exception e) {
			session.setAttribute("error", "페이지가 잘못되었습니다.");
			return "redirect:/nyam/board/boardList";
		}
		
		ArrayList<HashMap<String, String>> boardList = dao.getBoardList(page);
		request.setAttribute("boardList", boardList);
		return "/admin/boardList.jsp";
	}
	
	@RequestMapping(value = "/admin/boardView", method= Method.GET)
	public String showView(HttpServletRequest request, HttpSession session){
		DAO dao = DAO.getInstance();
		String no = (String)request.getParameter("no");
		Board board = dao.getBoard(Integer.parseInt(no));
		HashMap<String, Integer> map = dao.getRecommend(Integer.parseInt(no));
		request.setAttribute("board", board);
		request.setAttribute("recommendInfo", map);
		
		String userId = (String)session.getAttribute("users_id");
		request.setAttribute("userId", userId);
		return "/admin/boardView.jsp";
	}
	
	@RequestMapping(value="/admin/delete", method=Method.GET)
	public String deleteWriting(HttpServletRequest request, HttpSession session){
		//함수 추출 가능할 듯. 교수님 조언대로. 
		DAO dao = DAO.getInstance();
		String no = (String) request.getParameter("no");
		Board board = dao.getBoard(Integer.parseInt(no));
		
		dao.deleteWriting(no);
		
		return "redirect:/nyam/admin/boardList";
	}
	
	@RequestMapping("/admin/rankingPeriod")
	public String rankingHistoryPeriod(HttpServletRequest request,
			HttpSession session) {

		DAO dao = DAO.getInstance();

		ArrayList<HashMap<String, Object>> history = dao.getHistory("123456");
		request.setAttribute("history", history);
		logger.debug(history.toString());
		return "/admin/rankingPeriod.jsp";
	}
	
	@RequestMapping(value = "/admin/rankingHistory", method = Method.GET)
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
		return "/admin/rankingHistory.jsp";
	}
	
	@RequestMapping("/admin/restModify")
	public String modifyRestInfo(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String no= request.getParameter("no");
		Restaurant restaurant = dao.getRestaurant(no);
		request.setAttribute("restaurant", restaurant);
		return "/admin/addRest.jsp";//수정하는 부분 찾아볼 것 
	}
	
	@RequestMapping("/admin/restDelete")
	public String deleteRestInfo(HttpServletRequest request){
		String restNo = request.getParameter("no");
		DAO dao = DAO.getInstance();
		dao.deleteRest(restNo);
		return "redirect:/nyam/admin/manageRest";
	}

	
}
