package mobile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Board;
import model.InfoMessage;
import model.Restaurant;
import model.StampHistory;
import model.User;
import annotation.Controller;
import annotation.RequestMapping;

import com.oreilly.servlet.MultipartRequest;

import controller.DefaultController;
import controller.UserController;
import database.DAO;
import database.RestaurantDAO;

@Controller
public class MobileController extends DefaultController{
	/*
	 * @Controller 스탬프를 추가하는 함수를 만든다. 큐알코드에서 보낸 주소를 리퀘스트 매핑을 시킨다. 리퀘스트.겟 파라미터를
	 * 해서 data 를 가져온다. 리퀘스트 겟 파라미터 id도 한다. data가 "날짜날짤날짜&숫자"로 돼 있으니까 &를 기준으로
	 * 문자열을 나눠서 변수에 저장한다. 다오에 insertHistory를 써서, 정보 넣는다. 콘솔에 찍는다.
	 */
	
	private static Logger logger = LoggerFactory
			.getLogger(MobileController.class);
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
//		String jsonString = "[";
//		for (int i = 0; i < stampList.size(); i++) {
//			if (i != 0)
//				jsonString += ",";
//			jsonString += JSON.makeJSON(stampList.get(i));
//		}
//		jsonString += "]";
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
		if(hash == null)
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
	@RequestMapping("/m/requestBoard/view")
	public String requestBoardView(HttpSession session, HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		String no = request.getParameter("no");
		try {
			System.out.println("NO" + no);
			int iNo  = Integer.parseInt(no);
			Board board = dao.getBoard(iNo);
			return "text:" + JSON.makeJSON(board);
		}catch(Exception e) {
			e.printStackTrace();
			return "text:";
		}
	}
	@RequestMapping("/m/requestBoard/write")
	public String requestBoardWrite(HttpSession session, HttpServletRequest request) {
		User user = getLoginuser(session);
		DAO dao = DAO.getInstance();
		String originalFileName, uploadPath;
		int size = 10 * 1024 * 1024;
		MultipartRequest multipart = null;
		//uploadPath = "/Users/dayoungle/Documents/fileUpload";
		Board board;
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		realPath += "../images/";
		
		
		try {
			multipart = new MultipartRequest(request, realPath, size,
					"UTF-8", new controller.MyFileRenamePolicy());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String title = multipart.getParameter("title");
		String content = multipart.getParameter("content");
		String usersId = user.getId();


		if (multipart.getOriginalFileName("file") == null) {
			board = new Board(title, content, usersId);
			
		} else {
			Enumeration files = multipart.getFileNames();
			String name1 = (String) files.nextElement(); 
		
			originalFileName = multipart.getOriginalFileName("file");
			String filesystemName = multipart.getFilesystemName("file");
			
			
			File uf = multipart.getFile(name1);			
			File f = new File(realPath + filesystemName); 
			board = new Board(title, content, usersId);
			board.setFileName(filesystemName);
		}
		logger.debug(board.toString());
		dao.insertBoard(board);//보드에 정보입력.
		return "text:" + JSON.makeJSON(InfoMessage.getMessage(200, "OK"));
	}

}
