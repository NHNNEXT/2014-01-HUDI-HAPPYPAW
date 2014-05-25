package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Board;

import com.oreilly.servlet.MultipartRequest;

import database.DAO;
import annotation.Controller;
import annotation.RequestMapping;

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
		logger.debug("realPath: " + realPath);
		
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

		dao.insertBoard(board);//보드에 정보입력.
		//파일 업로드는 어디에서 시키나?

		return "redirect:/nyam/ranking";
	}
	
	@RequestMapping("/board/boardList")
	public String showBoardList(HttpServletRequest request){
		logger.debug("board 진");
		DAO dao = DAO.getInstance();
		ArrayList<Board> boardList = dao.getWritingList();

		request.setAttribute("boardList", boardList);
		
		return "/board/boardList.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
