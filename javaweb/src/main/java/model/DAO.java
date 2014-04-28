package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import admin.MakeExcel;
import admin.NyamList;

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
			e.printStackTrace();
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
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH) + 1;
		int year = cal.get(cal.YEAR);
		
		ArrayList<StampHistory> stampList = new ArrayList<StampHistory>();
		String firstDay = year + "-" + month + "-01 00:00:00";
		String lastDay = year + "-" + month + "-31 23:59:59";
		String query = "select * from stamp_history where users_id = ? and regdate > ? and regdate < ?";
		try {
			PreparedStatement selHistory;
			ResultSet rs;
			selHistory = con.prepareStatement(query);
			selHistory.setString(1, users_id);
			selHistory.setString(2, firstDay);
			selHistory.setString(3, lastDay);
			rs = selHistory.executeQuery();
			while (rs.next()) {
				String id = rs.getString("users_id");
				String regdate = rs.getString("regDate");
				int restaurant = rs.getInt("restaurant_no");
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
	 * 
	 * @param users_id
	 * @param qrDate
	 * @param restaurant
	 */
	public boolean insertHistory(String users_id, String qrDate, int restaurant) {
		if (!checkRestaurant(qrDate, restaurant)) {
			System.out.println("해당 정보 없음... date : " + qrDate
					+ "   restaurant : " + restaurant);
			return false;
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sdf.format(cal.getTime());
		String insertQuery = "INSERT INTO stamp_history(users_id, regdate, restaurant_no) VALUES (?, ?, ?)";
	
		try {
			PreparedStatement insertHistory;
			insertHistory = con.prepareStatement(insertQuery);
			insertHistory.setString(1, users_id);
			insertHistory.setString(2, nowDate);
			insertHistory.setInt(3, restaurant);
			insertHistory.execute();
			insertHistory.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 레스토랑 큐알코드 갱신일자가 같은지 확인하는 함수.
	 * 
	 * @param qrDate
	 * @param restaurant
	 * @return boolean
	 */
	private boolean checkRestaurant(String qrDate, int restaurant) {
		/*
		 * 레스토랑 테이블에서 레스토랑 넘버를 가지고 업데이트 날짜를 받아온다. 업데이트 날짜랑 큐알데이트가 다르면 return
		 * false; 업데이트 날짜랑 같으면 return true;
		 */
		String checkQuery = "select * from restaurant where no = ? and renew = ?;";
		try {
			PreparedStatement statement = con.prepareStatement(checkQuery);
			statement.setInt(1, restaurant);
			statement.setString(2, qrDate);
			ResultSet rs = statement.executeQuery();
			int count = 0;
			
			while (rs.next()) {
				count++;
			}
			if (count == 1)
				return true;
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 유저를 가져옴.
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
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

	/**
	 * 관리자 웹페이지의 전체 학생 식사기록을 확인하는 함수. 
	 * 
	 * @return ArrayList<NyamList>
	 */
	public ArrayList<NyamList> adminNyamHistory() {
		String userQuery = "SELECT * FROM users";
		ArrayList<NyamList> nyamList = new ArrayList<NyamList>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(userQuery);
			while (rs.next()) {
				String users_id = rs.getString("id");
				String users_name = rs.getString("name");
				int sum = selectMonthHistory(users_id).size();
				nyamList.add(new NyamList(users_id, users_name, sum));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nyamList;
	}

	/**
	 * 식당 별 식사기록을 확인
	 * 
	 * @return
	 */
	public ArrayList<Restaurant> restaurantHistory() {
		ArrayList<Restaurant> restaurant = new ArrayList<Restaurant>();
		try {
			String countQuery = "SELECT r.no, r.name, count(s.restaurant_no) FROM restaurant "
					+ "r LEFT JOIN stamp_history s ON s.restaurant_no = r.no group by r.no;";
			Statement rStatement = con.createStatement();
			ResultSet rs = rStatement.executeQuery(countQuery);
			while (rs.next()) {
				int rId = rs.getInt("no");
				String rName = rs.getString("name");
				int num = rs.getInt("count(s.restaurant_no)");
				restaurant.add(new Restaurant(rId, rName, num));
			}
			rs.close();
			rStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(restaurant);
		return restaurant;
	}

	/**
	 * 엑셀을 파일로 내보내는 함수.
	 * 
	 * @param path
	 */
	public void exportExcel(String path) {
		System.out.println("exportExcel");
		MakeExcel me = new MakeExcel();
		HSSFWorkbook book = me.fillExcel(adminNyamHistory());
		FileOutputStream fout = me.makeFile(path);
		try {
			book.write(fout);
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 관리자 식당 관리 페이지 
	 * @return ArrayList<Restaurant> 
	 */
	public ArrayList<Restaurant> manageRestaurant() {
		String query = "SELECT * FROM restaurant";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		try {
			ResultSet rs;
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			int no;
			String name, desc, location, renew= "";
			while (rs.next()) {
				no = rs.getInt("no");
				name = rs.getString("name");
				desc = rs.getString("description");
				location = rs.getString("location");
				renew = rs.getString("renew");
				//renew.replace(" ", "%20");//요거 없어도 되는데? 왜 넣어놨지..
				Restaurant rest = new Restaurant(no, name, desc, location, renew);
				restList.add(rest);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restList;
	}

	/**
	 * qr코드 등록일자 갱신 하는 함수. 
	 * @return
	 */
	public void renewQrcode(String restaurantNo) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ymd = date.format(cal.getTime());
		String query = "update restaurant set renew = ? where no = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, ymd);
			pst.setInt(2, Integer.parseInt(restaurantNo));
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ;
	}

	/**
	 * 레스토랑 일자별로 식사횟수 확인 
	 * @param id
	 * @return
	 */
	public HashMap<String, Integer> checkEachRestaurant(String id) {
		int restaurantNo = Integer.parseInt(id);
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		String query = "select count(*) as num, substring(regdate, 1, 10) as stamp_date  "
				+ "from stamp_history where restaurant_no = ? group by substring(regdate, 1, 10); ";
		try {
			PreparedStatement pst;
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			pst.setInt(1, restaurantNo);
			while(rs.next()){
				String stamp_date = rs.getString("stamp_date");
				int num = rs.getInt("num");
				hash.put(stamp_date, num);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}
	

	public HashMap<String, Integer> arrangeNyamHistory(ArrayList<StampHistory> stampList){

		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		for (int i = 0; i < stampList.size(); i++) {
			String date = stampList.get(i).getRegdate();
			String day= (String)date.subSequence(8, 10);
			
			if(hash.containsKey(day)){
				int count= hash.get(day) + 1;
				hash.put(day, count);
			} else {
				hash.put(day, 1);				
			}
		}
		
		for(Map.Entry<String, Integer> e : hash.entrySet()){
			String key = e.getKey();
			int val = e.getValue();
			System.out.println(key + "일 : " + val+"회 ");
		}
		
		return hash;
	}
	
	public DateInfo setDate(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);//이번달 숫자-1을 찾아놓는다. 
		int year = cal.get(Calendar.YEAR);
		/* 		year = 2013;
		 month= 11; */
		cal.set(year, month, 1);

		int dayOfMonth = cal.getActualMaximum(cal.DAY_OF_MONTH);//이번달 총 일자 찾기.
		int yoil = cal.get(cal.DAY_OF_WEEK);//일요일부터 1.
		int week = cal.getActualMaximum(cal.WEEK_OF_MONTH);//한달에 몇주 있는지 확인.
		
		DateInfo date = new DateInfo(year, month, dayOfMonth, week, yoil);

		return date;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
