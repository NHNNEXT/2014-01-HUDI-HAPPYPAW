package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DAO {
	private String url = "jdbc:mysql://10.73.45.131/happypaw";
	private String user = "dayoungles";
	private String pw = "ekdudrmf2";
	private Connection con;
	static DAO nyam;

	private DAO() {
		connect();
	}

	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("driver load error:");
			return null;
		}
		System.out.println("driver loading success");
		try {
			con = DriverManager.getConnection(url, user, pw);

		} catch (SQLException e) {
			System.err.println("connect fail:" + e.getMessage());
			return null;
		}
		System.out.println("connection success");
		return con;
	}

	public static DAO getInstance() {
		if (nyam == null) {
			nyam = new DAO();
		}
		return nyam;
	}

	public ArrayList<StampHistory> selectMonthHistory(String users_id) {
		PreparedStatement selHistory;
		Calendar cal = Calendar.getInstance();
		ResultSet rs;
		int month = cal.get(cal.MONTH) + 1;
		int nextMonth = month + 1;
		int nextYear;
		int year = cal.get(cal.YEAR);
		ArrayList<StampHistory> stampList = new ArrayList<StampHistory>();
		String firstDay = year + "-" + month + "-01 00:00:00";
		String lastDay = year + "-" + month + "-31 23:59:59";
		String query = "select * from stamp_history where users_id = ? and regdate > ? and regdate < ?";
		try {
			selHistory = con.prepareStatement(query);
			selHistory.setString(1, users_id);
			selHistory.setString(2, firstDay);
			selHistory.setString(3, lastDay);
			rs = selHistory.executeQuery();
			while (rs.next()) {
				String id = rs.getString("users_id");
				String regdate = rs.getString("regDate");
				int restaurant = rs.getInt("restaurant_no");
				System.out.println(id + "   " + regdate + "   " + restaurant);
				stampList.add(new StampHistory(id, regdate, restaurant));
			}
			rs.close();
			selHistory.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stampList;
	}
	
	/**
	 * 정보 받으면 db에 insert하는 함수.
	 * @param users_id
	 * @param qrDate
	 * @param restaurant
	 */
	public boolean insertHistory(String users_id, String qrDate, int restaurant){
		if(!checkRestaurant(qrDate, restaurant)){
			return false;
		}
		
		PreparedStatement insertHistory;
		ResultSet rs;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sdf.format(cal.getTime());
		
		String insertQuery = "INSERT INTO stamp_history(users_id, regdate, restaurant) VALUES ?, ?, ?";
		try {
			insertHistory = con.prepareStatement(insertQuery);
			insertHistory.setString(1, users_id);
			insertHistory.setString(2, nowDate);
			insertHistory.setInt(3, restaurant);
			insertHistory.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean checkRestaurant(String qrDate, int restaurant) {
		/*
		 * 레스토랑 테이블에서 레스토랑 넘버를 가지고 업데이트 날짜를 받아온다. 
		 * 업데이트 날짜랑 큐알데이트가 다르면 return false; 
		 * 업데이트 날짜랑 같으면 return true;
		 */
		String checkQuery="select * from restaurant where no = ? and update = ?";
		try {
			PreparedStatement statement=con.prepareStatement(checkQuery);
			statement.setInt(1, restaurant);
			statement.setString(2, qrDate);
			ResultSet rs = statement.executeQuery();
			if(rs.getFetchSize()==1){
				return true;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}

	public User getUser(String id){
		String query = "select * from users where id = ?";
		
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String users_id = rs.getString("id");
				String ps = "next";
				String name = rs.getString("name");
				
				User user = new User(users_id, ps, name);
				rs.close();
				statement.close();
				return user;
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
