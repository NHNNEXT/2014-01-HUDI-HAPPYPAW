package model;

public class Board {
	String title;
	String content;
	String fileName;
	String userId;
	String writingNo;
	private String date;
	
	
	public Board(String title, String content, String fileName, String userId,
			String writingNo, String date) {
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.userId = userId;
		this.writingNo = writingNo;
		this.date = date;
	}

	public Board(String title, String content, String fileName, String userId,
			String writingNo) {
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.userId = userId;
		this.writingNo = writingNo;
	}

	public Board(String title, String content, String userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}



	public Board(String title, String content, String fileName, String userId) {
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.userId = userId;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getWritingNo() {
		return writingNo;
	}



	public void setWritingNo(String writingNo) {
		this.writingNo = writingNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Board [title=" + title + ", content=" + content + ", fileName="
				+ fileName + ", userId=" + userId + ", writingNo=" + writingNo
				+ ", date=" + date + "]";
	}


}
