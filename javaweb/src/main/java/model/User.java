package model;

public class User {
	private String id;
	private String ps;
	private String name;
	
	User (String id, String ps, String name){
		this.id = id;
		this.ps = ps;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean checkPs(String inputPs){
		return this.ps.equals(inputPs);//happy zzangzzang puppy coding dog
	}
	
}
