package mobile;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.StampHistory;
import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class MobileController {
	/*주석이 별건가? 이 class가 하는 일을 코드만으로 이해시키기 어렵다면. 이렇게 서술형으로 작성해서 코드에 대한 이해를 돕는 거 정말 좋음 */
	/*
	 * @Controller 스탬프를 추가하는 함수를 만든다. 큐알코드에서 보낸 주소를 리퀘스트 매핑을 시킨다. 리퀘스트.겟 파라미터를
	 * 해서 data 를 가져온다. 리퀘스트 겟 파라미터 id도 한다. data가 "날짜날짤날짜&숫자"로 돼 있으니까 &를 기준으로
	 * 문자열을 나눠서 변수에 저장한다. 다오에 insertHistory를 써서, 정보 넣는다. 콘솔에 찍는다.
	 */
	@RequestMapping(value = "/addHistory", method = RequestMapping.Method.POST)
	public String addHistory(HttpServletRequest request, HttpSession session) {
		try {
			
			DAO dao = DAO.getInstance();
			String users_id = (String)session.getAttribute("users_id");
			
			String qrcode = request.getParameter("qrcode");
			System.out.println("METHOD :  " + request.getMethod());
			if (qrcode == null) {
				System.out.println("QRCode is Null");
				return "text:false";
			}
			System.out.println("QRCode is " + qrcode);
			
			String[] data = qrcode.split("@");
			
			if(data.length != 2)
				return "text:false";
			
			String date = data[0];
			int restaurant_no = Integer.parseInt(data[1]);
			boolean is_insert = dao.insertHistory(users_id, date, restaurant_no);
			System.out.println(dao.selectMonthHistory(users_id));
			return "text:" + is_insert;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "text:false";
	}
	
	@RequestMapping("/m_nyamHistory")
	public String m_nyamHistory(HttpSession session, HttpServletRequest request){
		DAO db = DAO.getInstance();
		String jsonString = "[";
		
		//세션에서 아이디를 못찾으면 로그인 페이지로 퉁 
		String id =(String) session.getAttribute("users_id");
		if(id ==null || id==""){
			return "text:false";
		}
		//아이디가 있을 때는 월별 히스토리를 검색해서 결과를 보여준다. 
		ArrayList<StampHistory> stampList = db.selectMonthHistory(id);
		request.setAttribute("record", stampList);
		
		//json으로 만든걸 쉼표로 모두 연결?
		for(int i = 0; i <stampList.size(); i++){
			if(i!=0)
				jsonString +=",";
			jsonString += JSON.makeJSON(stampList.get(i));
		}
		jsonString +="]";

		
		//얘네 json 어떻게 html로 보내주지?
		return "text:"+jsonString;
	
	}

}
