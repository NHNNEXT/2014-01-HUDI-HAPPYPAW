package model;

public class InfoMessage {
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public InfoMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public static InfoMessage getMessage(int code, String message) {
		return new InfoMessage(code, message);
	}
}
