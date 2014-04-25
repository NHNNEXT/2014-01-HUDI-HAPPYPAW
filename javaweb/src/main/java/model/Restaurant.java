package model;

public class Restaurant {
	int no;
	String name;
	int nyamNum;
	String desc;
	String location;
	String renew;

	public Restaurant(int no, String name, int nyamNum) {
		this.no = no;
		this.name = name;
		this.nyamNum = nyamNum;
	}
	
	public Restaurant(int no, String name, String desc, String location, String renew) {
		super();
		this.no = no;
		this.name = name;
		this.desc = desc;
		this.location = location;
		this.renew = renew;
	}

	
	public String getRenew() {
		return renew;
	}
	
	public void setRenew(String renew) {
		this.renew = renew;
	}
	
	public int getNo() {
		return no;
	}



	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	
	@Override
	public String toString() {
		return "[no=" + no + ", name=" + name + ", nyamNum="
				+ nyamNum + "]";
	}
	
}
