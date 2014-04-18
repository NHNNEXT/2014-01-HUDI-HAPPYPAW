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
	
	/*
	 * 로그인 세션체크
	 * 세션 가지고 와서 확인하고 없으면. 로그인 페이지로
	 * 있으면 로그인 후 메인화면
	 * 
	 * 로그아웃 세션 제거 
	 */
	@RequestMapping("/m_login")
	public String m_login(HttpSession session ){
		String id = (String) session.getAttribute("users_id");
		if(id == null || id.equals("")){
			return "m_login.jsp";
		} else {
			//http://localhost/nyam/app/nyamHistory
			return "redirect:./m_nyamHistory";
		}
		
	}
	
	@RequestMapping("/m_logout")
	public String m_logoutPage(HttpSession session){
		if(session != null){
			session.invalidate();
		}
		return "redirect:./m_login";
	}
	
	@RequestMapping("/m_login_check")
	public String m_login_check(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		DAO db = DAO.getInstance();
		String users_id = (String) session.getAttribute("users_id");
		if(users_id == null || users_id.equals("")){
			//로그인 과정 
			String jspId = request.getParameter("id");//jsp에서 보낸 id
			String jspPs = request.getParameter("password");
			User user = db.getUser(jspId);
			if(user == null) {
				System.out.println("login error No User");
				return "text:false";
			} else if(user.checkPs(jspPs)){
				session.setAttribute("users_id", jspId);
				System.out.println("login complete :"+ jspId);
				return "text:true";
			} else {
				return "text:false";
			}
		} else {
			return "text:true";
		}
		
	}
}
