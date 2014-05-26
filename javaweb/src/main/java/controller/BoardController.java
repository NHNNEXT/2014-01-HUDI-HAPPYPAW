package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestMapping.Method;

import com.oreilly.servlet.MultipartRequest;

import database.DAO;

@Controller
public class BoardController {
	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	@RequestMapping("/board/writing")
	public String writing() {
		return "/board/writing.jsp";
	}

	@RequestMapping("/board/sendContent")
	public String storeContent(HttpServletRequest request, HttpSession session) {
		DAO dao = DAO.getInstance();
		String originalFileName, uploadPath;
		int size = 10 * 1024 * 1024;
		MultipartRequest multipart = null;
		uploadPath = "/Users/dayoungle/Documents/fileUpload";
		Board board;
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		realPath += "/uploadFiles";
		
		
		try {
			multipart = new MultipartRequest(request, realPath, size,
					"UTF-8", new controller.MyFileRenamePolicy());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String title = multipart.getParameter("title");
		String content = multipart.getParameter("content");
		String usersId = (String) session.getAttribute("users_id");


		if (multipart.getOriginalFileName("file") == null) {
			board = new Board(title, content, usersId);
			
		} else {
			Enumeration files = multipart.getFileNames();
			String name1 = (String) files.nextElement(); 
		
			originalFileName = multipart.getOriginalFileName("file");
			String filesystemName = multipart.getFilesystemName("file");
			
			
			File uf = multipart.getFile(name1);			
			File f = new File(uploadPath + filesystemName); 
			board = new Board(title, content, filesystemName, usersId);
		}
		logger.debug(board.toString());
		dao.insertBoard(board);//보드에 정보입력.

		return "redirect:/nyam/board/boardList";
	}
	
	@RequestMapping("/board/boardList")
	public String showBoardList(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		ArrayList<Board> boardList = dao.getWritingList();

		request.setAttribute("boardList", boardList);
		
		return "/board/boardList.jsp";
	}
	
	@RequestMapping(value="/board/recommend", method=Method.GET)
	public String recommend(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String no = (String)request.getParameter("no");
		dao.plusRecommend(no);
		return "redirect:/nyam/board/boardList";
	}
	
	@RequestMapping(value = "/board/view", method= Method.GET)
	public String showView(HttpServletRequest request){
		DAO dao = DAO.getInstance();
		String no = (String)request.getParameter("no");
		logger.debug("no:  " + no);
		Board board = dao.getBoard(Integer.parseInt(no));
		logger.debug(board.toString());
		request.setAttribute("board", board);
		return "./pageView.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
