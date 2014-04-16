package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import annotation.Controller;
import annotation.RequestMapping;

@Controller
public class MobileController {
	/*
	 * @Controller 스탬프를 추가하는 함수를 만든다. 큐알코드에서 보낸 주소를 리퀘스트 매핑을 시킨다. 리퀘스트.겟 파라미터를
	 * 해서 data 를 가져온다. 리퀘스트 겟 파라미터 id도 한다. 
	 * data가 "날짜날짤날짜&숫자"로 돼 있으니까 &를 기준으로
	 * 문자열을 나눠서 변수에 저장한다. 다오에 insertHistory를 써서, 정보 넣는다. 콘솔에 찍는다.
	 */
	@RequestMapping(value = "/addHistory", method = RequestMapping.Method.POST)
	public String addHistory(HttpServletRequest request) {
		DAO dao = DAO.getInstance();
		
		String qrcode = request.getParameter("qrcode");
		if(qrcode == null)
				return "text:false";
		// 임시로
		String id = request.getParameter("id");
		String[] data = qrcode.split("&");
		String date = data[0];
		int restaurant_no = Integer.parseInt(data[1]);
		boolean is_insert = dao.insertHistory(id, date, restaurant_no);
		System.out.println(dao.selectMonthHistory("121001"));
		return "text:" + is_insert;
	}
}
