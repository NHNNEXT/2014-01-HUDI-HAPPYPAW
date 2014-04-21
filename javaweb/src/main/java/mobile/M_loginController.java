package mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.User;
import annotation.Controller;
import annotation.RequestMapping;
@Controller
public class M_loginController {
	
	@RequestMapping("/m_login")
	public String m_login(HttpSession session ){
		String id = (String) session.getAttribute("users_id");
		if(id == null || id.equals("")){
			return "text:true";
		} else {
			//http://localhost/nyam/app/nyamHistory
			return "text:false";
		}
		
	}
	
	@RequestMapping("/m_logout")
	public String m_logoutPage(HttpSession session){
		if(session != null){
			session.invalidate();
			return "text:true";
		}
		return "text:true";
	}
	
	@RequestMapping("/m_login_check")
	public String m_login_check(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		DAO db = DAO.getInstance();
		String users_id = (String) session.getAttribute("users_id");
		
		//입력없이  로그인해도 메인으로 들어간다는 에러가 있음; >>해결 @main.html 연규 체크
		if(users_id == null || users_id.equals("")){
			//로그인 과정 
			String m_id = request.getParameter("id");//phonegap에서 보낸 id
			String m_ps = request.getParameter("password");
			User user = db.getUser(m_id);
			
			if(user == null) {
				System.out.println("login error No User");
				return "text:false";
			} else if(user.checkPs(m_ps)){
				session.setAttribute("users_id", m_id);
				System.out.println("login complete :"+ m_id);
				return "text:true";
			} else {
				return "text:false";
			}
		} else {
			return "text:true";
		}
		
	}
	
	
	@RequestMapping("/m_getLoginUser")
	public String getLoginUser(HttpSession session){
		DAO dao = DAO.getInstance();
		String users_id = (String)session.getAttribute("users_id");
		
		User user = dao.getUser(users_id);
		//user가 널일때 테스트 하기 바람.
		String userJSON = JSON.makeJSON(user);
		System.out.println(userJSON);
		return "text:" + userJSON;
	}
}
