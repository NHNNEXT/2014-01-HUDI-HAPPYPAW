package mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Restaurant;
import model.StampHistory;
import annotation.Controller;
import annotation.RequestMapping;
import database.DAO;
import database.RestaurantDAO;

@Controller
public class MobileController {
	/*
	 * @Controller 스탬프를 추가하는 함수를 만든다. 큐알코드에서 보낸 주소를 리퀘스트 매핑을 시킨다. 리퀘스트.겟 파라미터를
	 * 해서 data 를 가져온다. 리퀘스트 겟 파라미터 id도 한다. data가 "날짜날짤날짜&숫자"로 돼 있으니까 &를 기준으로
	 * 문자열을 나눠서 변수에 저장한다. 다오에 insertHistory를 써서, 정보 넣는다. 콘솔에 찍는다.
	 */
	@RequestMapping(value = "/addHistory", method = RequestMapping.Method.POST)
	public String addHistory(HttpServletRequest request, HttpSession session) {
		try {

			DAO dao = DAO.getInstance();
			String users_id = (String) session.getAttribute("users_id");

			String qrcode = request.getParameter("qrcode");
			System.out.println("METHOD :  " + request.getMethod());
			if (qrcode == null) {
				System.out.println("QRCode is Null");
				return "text:false";
			}
			System.out.println("QRCode is " + qrcode);

			String[] data = qrcode.split("@");

			if (data.length != 2)
				return "text:false";

			String date = data[0];
			int restaurant_no = Integer.parseInt(data[1]);
			boolean is_insert = dao
					.insertHistory(users_id, date, restaurant_no);
			System.out.println(dao.selectMonthHistory(users_id, 2014, 11));// 날짜
																			// 설정하는거;;;
			return "text:" + is_insert;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "text:false";
	}

	@RequestMapping("/m_nyamHistory")
	public String m_nyamHistory(HttpSession session, HttpServletRequest request) {
		DAO db = DAO.getInstance();

		// 세션에서 아이디를 못찾으면 로그인 페이지로 퉁
		String id = (String) session.getAttribute("users_id");
		if (id == null || id == "") {
			return "text:false";
		}
		// 아이디가 있을 때는 월별 히스토리를 검색해서 결과를 보여준다.
		ArrayList<StampHistory> stampList = db.selectMonthHistory(id);
		request.setAttribute("record", stampList);

		// json으로 만든걸 쉼표로 모두 연결?
		// String jsonString = "[";
		// for (int i = 0; i < stampList.size(); i++) {
		// if (i != 0)
		// jsonString += ",";
		// jsonString += JSON.makeJSON(stampList.get(i));
		// }
		// jsonString += "]";
		String jsonString = JSON.makeJSON(stampList);
		// 얘네 json 어떻게 html로 보내주지?
		return "text:" + jsonString;
	}

	@RequestMapping("/m/restaurant")
	public String restaurant(HttpSession session, HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		ArrayList<Restaurant> restList = dao.manageRestaurant();
		return "text:" + JSON.makeJSON(restList);
	}

	@RequestMapping("/m/restaurant/view")
	public String restaurantView(HttpSession session, HttpServletRequest request) {
		String no = request.getParameter("no");
		RestaurantDAO dao = RestaurantDAO.getInstance();
		HashMap<String, String> hash = dao.getRestaurant(no);
		if (hash == null)
			return "text:";
		else
			return "text:" + JSON.makeJSON(hash);
	}

	@RequestMapping("/m/requestBoard")
	public String requestBoard(HttpSession session, HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		ArrayList<HashMap<String, String>> boardList = dao.getBoardList();
		return "text:" + JSON.makeJSON(boardList);
	}

	@RequestMapping("/m/ranking")
	public String showRankingAll(HttpSession session, HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		ArrayList<HashMap<String, String>> nyamRanking = dao.rankingHistory(
				year, month - 1);

		return "text:" + JSON.makeJSON(nyamRanking);
	}
	@RequestMapping("/m/myranking")
	public String showMyRanking(HttpSession session, HttpServletRequest request) {
		DAO dao = DAO.getInstance();

		//ArrayList<HashMap<String, String>> nyamRanking = 

		return "text:" + "";
	}

}
