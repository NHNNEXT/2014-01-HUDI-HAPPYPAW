package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class NyamDb {
	private String url = "jdbc:mysql://10.73.45.131/happypaw";
	private String user = "dayoungles";
	private String pw = "ekdudrmf2";
	private Connection con;
	static NyamDb nyam;

	private NyamDb() {
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

	public static NyamDb getInstance() {
		if (nyam == null) {
			nyam = new NyamDb();
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
}
