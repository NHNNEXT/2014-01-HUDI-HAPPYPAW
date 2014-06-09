package model;

public class Message {
	public static String getDefaultMessage(int code) {
		switch(code) {
		case 500:
			return "로그인을 해주세요";
		}
		return null;
	}
	public static String getStampMessage(int code) {
		String message = getDefaultMessage(code);
		if(message != null)
			return message;
			
		switch(code) {
		case 200:
			return "등록되었습니다.";
		case -1:
			return "QR코드가 이상합니다.";
		case -200:
			return "등록 실패";
		case -400:
			return "정보가 일치하지 않습니다.";
		case -500:
			return "등록 횟수를 초과하였습니다.";
		case -600:
			return "이미 등록되었습니다.";
		}
		return "-_- 관리자에게 문의하시기를 바랍니다.";
	}
}
