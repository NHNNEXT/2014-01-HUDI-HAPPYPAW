package model;

public class Restaurant {
	int no;
	String name;
	int nyamNum;
	
	
	public Restaurant(int no, String name, int nyamNum) {
		this.no = no;
		this.name = name;
		this.nyamNum = nyamNum;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNyamNum() {
		return nyamNum;
	}
	public void setNyamNum(int nyamNum) {
		this.nyamNum = nyamNum;
	}
	
	
}
