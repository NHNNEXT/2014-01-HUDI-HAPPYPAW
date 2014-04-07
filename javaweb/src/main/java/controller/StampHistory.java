package controller;

public class StampHistory {
	private String users_id;
	private String regdate;
	private int restaurant;
	
	public StampHistory(){
		
	}
	public StampHistory(String users_id, String regdate, int restaurant){
		this.users_id =users_id;
		this.regdate = regdate;
		this.restaurant = restaurant;
	}
	
	@Override
	public String toString() {
		return "StampHistory [users_id=" + users_id + ", regdate=" + regdate
				+ ", restaurant=" + restaurant + "]";
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}
	
	
}
