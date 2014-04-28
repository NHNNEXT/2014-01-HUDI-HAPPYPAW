package model;

public class NyamList {
	String id;
	String name;
	int nyamNum;
	
	public NyamList(String id, String name, int nyamNum){
		this.id = id;
		this.name = name;
		this.nyamNum = nyamNum;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "NyamList4Admin [id=" + id + ", name=" + name + ", nyamNum="
				+ nyamNum + "]";
	}

	public void setId(String id) {
		this.id = id;
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
