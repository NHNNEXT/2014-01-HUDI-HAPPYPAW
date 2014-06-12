package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Board;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestMapping.Method;

import com.oreilly.servlet.MultipartRequest;

import database.DAO;

@Controller
public class BoardController extends DefaultController {
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping("/board/writing")
	public String writing(HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		return "/board/writing.jsp";
	}

	public void modifyContent(Board board) {

	}

	@RequestMapping("/board/sendContent")
	public String storeContent(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		String originalFileName, uploadPath;
		int size = 10 * 1024 * 1024;
		MultipartRequest multipart = null;
		// uploadPath = "/Users/dayoungle/Documents/fileUpload";
		Board board;

		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		realPath += "../images/";

		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			multipart = new MultipartRequest(request, realPath, size, "UTF-8",
					new controller.MyFileRenamePolicy());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String title = multipart.getParameter("title");
		String content = multipart.getParameter("content");
		String no = multipart.getParameter("no");
		String usersId = user.getId();

		if(title == null || title.equals(""))
			return goBack("제목을 입력해주세요.");
		
		if(content == null || content.equals(""))
			return goBack("내용을 입력해주세요.");
		
		
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
		if (no == "" || no == null) {
			dao.insertBoard(board);// 보드에 정보입력.
		} else {
			Board originalBoard = dao.getBoard(Integer.parseInt(no));
			if (!originalBoard.getUserId().equals(board.getUserId()))
				return "redirect:/nyam/board/boardList";

			dao.modifyBoard(no, board);
		}
		return "redirect:/nyam/board/boardList";
	}

	@RequestMapping("/address")
	public String showAddress(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		return "text:" + realPath;
	}

	@RequestMapping("/board/boardList")
	public String showBoardList(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		
		int page = 1;
		String sPage = request.getParameter("page");
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

		return "/board/boardList.jsp";
	}

	@RequestMapping(value = "/board/recommend", method = Method.GET)
	public String recommend(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		String no = (String) request.getParameter("no");
		dao.plusRecommend(no);
		return "redirect:/nyam/board/view?no=" + no;
	}

	@RequestMapping(value = "/board/notRecommend", method = Method.GET)
	public String notRecommend(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		String no = (String) request.getParameter("no");
		dao.minusRecommend(no);
		return "redirect:/nyam/board/view?no=" + no;
	}

	@RequestMapping(value = "/board/view", method = Method.GET)
	public String showView(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		String no = (String) request.getParameter("no");
		Board board = dao.getBoard(Integer.parseInt(no));
		HashMap<String, Integer> map = dao.getRecommend(Integer.parseInt(no));
		request.setAttribute("board", board);
		request.setAttribute("recommendInfo", map);

		String userId = (String) session.getAttribute("users_id");
		request.setAttribute("userId", userId);
		return "./pageView.jsp";
	}

	@RequestMapping(value = "/board/delete", method = Method.GET)
	public String deleteWriting(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		// 함수 추출 가능할 듯. 교수님 조언대로.
		DAO dao = DAO.getInstance();
		String currentUserId = (String) session.getAttribute("users_id");
		String no = (String) request.getParameter("no");
		Board board = dao.getBoard(Integer.parseInt(no));
		String writer = board.getUserId();

		if (currentUserId.equals(writer)) {
			dao.deleteWriting(no);
		}

		return "redirect:/nyam/board/boardList";
	}

	@RequestMapping(value = "/board/modify", method = Method.GET)
	public String modifyWriting(HttpServletRequest request, HttpSession session) {
		User user = getLoginuser(session);
		if (user == null)
			return goLoginPage();

		DAO dao = DAO.getInstance();
		int no = Integer.parseInt(request.getParameter("no"));
		Board board = dao.getBoard(no);
		request.setAttribute("board", board);

		return "/board/writing.jsp";
	}

}
