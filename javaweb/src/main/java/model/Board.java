package model;

public class Board {
	String title;
	String content;
	String fileName;
	String userId;
	
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
	
	@Override
	public String toString() {
		return "Board [title=" + title + ", content=" + content + ", fileName="
				+ fileName + ", userId=" + userId + "]";
	}
}
