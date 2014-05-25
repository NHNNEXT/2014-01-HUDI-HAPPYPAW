package database;

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
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.Board;
import model.DateInfo;
import model.MakeExcel;
import model.NyamList;
import model.Restaurant;
import model.StampHistory;
import model.User;

import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAO {
	// xml을 키밸류로 만들고. xml을 읽어서 자바 오브젝트로 만들어서 띄워놓으면 얘를 사용할 수 있는 라이브러리를 써야된다.

	// private String url = "jdbc:mysql://10.73.45.131/happypaw";
	// private String user = "dayoungles";
	// private String pw = "ekdudrmf2";
	private String url = "jdbc:mysql://127.0.0.1/happypaw";
	private String user = "dayg";
	private String pw = "ekdudrmf2";

	private Connection con;
	static DAO nyam;

	private static Logger logger = LoggerFactory.getLogger(DAO.class);

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

	/**
	 * selectMonthHistory에서 시작, 끝 날짜 설정하는 함수
	 * 
	 * @param day
	 * @return
	 */
	private String makePeriod(String day, int year, int month) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String datee = null;

		if (day.equals("first")) {
			cal.set(year, month, 1);
			datee = date.format(cal.getTime());
			datee += " 00:00:00";

		} else {
			cal.set(year, month, 1);
			int dayOfMonth = cal.getActualMaximum(cal.DAY_OF_MONTH);
			cal.set(year, month, dayOfMonth);
			datee = date.format(cal.getTime());
			datee += " 23:59:59";
		}

		return datee;
	}

	public ArrayList<StampHistory> selectMonthHistory(String users_id) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		return this.selectMonthHistory(users_id, year, month);
	}

	public ArrayList<StampHistory> selectMonthHistory(String users_id,
			int year, int month) {

		ArrayList<StampHistory> stampList = new ArrayList<StampHistory>();
		String firstDay = makePeriod("first", year, month);
		String lastDay = makePeriod("last", year, month);
		String query = "select * from stamp_history where users_id = ? and regdate > ? and regdate < ?";
		PreparedStatement selHistory;
		try {
			selHistory = con.prepareStatement(query);

			selHistory.setString(1, users_id);
			selHistory.setString(2, firstDay);
			selHistory.setString(3, lastDay);

			ResultSet rs = selHistory.executeQuery();
			while (rs.next()) {
				String id = rs.getString("users_id");
				String regdate = rs.getString("regDate");
				int restaurant = rs.getInt("restaurant_no");
				stampList.add(new StampHistory(id, regdate, restaurant));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// rs.close();
			// selHistory.close();
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
	 * @param month
	 * @param year
	 * 
	 * @return ArrayList<NyamList>
	 */
	public ArrayList<NyamList> adminNyamHistory(int year, int month) {
		String userQuery = "SELECT * FROM users";
		ArrayList<NyamList> nyamList = new ArrayList<NyamList>();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(userQuery);
			while (rs.next()) {

				String users_id = rs.getString("id");
				String users_name = rs.getString("name");
				int sum = selectMonthHistory(users_id, year, month).size();
				nyamList.add(new NyamList(users_id, users_name, sum));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
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
		int year = 2013;// 임의 지정 수정해야합니다.
		int month = 11;
		HSSFWorkbook book = me.fillExcel(adminNyamHistory(year, month));
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
	 * 
	 * @return ArrayList<Restaurant>
	 * @throws SQLException
	 */
	public ArrayList<Restaurant> manageRestaurant() throws SQLException {
		String query = "SELECT * FROM restaurant";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		ResultSet rs = null;
		Statement st = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			int no;
			String name, desc, location, renew = "";
			while (rs.next()) {
				no = rs.getInt("no");
				name = rs.getString("name");
				desc = rs.getString("description");
				location = rs.getString("location");
				renew = rs.getString("renew");
				// renew.replace(" ", "%20");//요거 없어도 되는데? 왜 넣어놨지..
				Restaurant rest = new Restaurant(no, name, desc, location,
						renew);
				restList.add(rest);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs.close();
			st.close();
		}
		logger.debug(restList.toString());
		return restList;
	}

	/**
	 * qr코드 등록일자 갱신 하는 함수.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public void renewQrcode(String restaurantNo) throws SQLException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ymd = date.format(cal.getTime());
		String query = "update restaurant set renew = ? where no = ?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, ymd);
			pst.setInt(2, Integer.parseInt(restaurantNo));
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pst.close();
		}

		return;
	}

	/**
	 * 레스토랑 일자별로 식사횟수 확인
	 * 
	 * @return
	 * @param id
	 * @throws SQLException
	 */
	public HashMap<String, Integer> checkEachRestaurant(String id)
			throws SQLException {
		int restaurantNo = Integer.parseInt(id);
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		String query = "select count(*) as num, substring(regdate, 1, 10) as stamp_date  "
				+ "from stamp_history where restaurant_no = ? group by substring(regdate, 1, 10); ";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(query);
			pst.setInt(1, restaurantNo);

			rs = pst.executeQuery();
			while (rs.next()) {
				String stamp_date = rs.getString("stamp_date");
				int num = rs.getInt("num");
				hash.put(stamp_date, num);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pst.close();
		}
		return hash;
	}

	public HashMap<String, Integer> arrangeNyamHistory(
			ArrayList<StampHistory> stampList) {

		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		for (int i = 0; i < stampList.size(); i++) {
			String date = stampList.get(i).getRegdate();
			String day = (String) date.subSequence(8, 10);

			if (hash.containsKey(day)) {
				int count = hash.get(day) + 1;
				hash.put(day, count);
			} else {
				hash.put(day, 1);
			}
		}

		for (Map.Entry<String, Integer> e : hash.entrySet()) {
			String key = e.getKey();
			int val = e.getValue();
			System.out.println(key + "일 : " + val + "회 ");
		}

		return hash;
	}

	public DateInfo setDate(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// int month = cal.get(Calendar.MONTH);//이번달 숫자-1을 찾아놓는다.
		// int year = cal.get(Calendar.YEAR);
		/*
		 * year = 2013; month= 11;
		 */
		cal.set(year, month, 1);

		int dayOfMonth = cal.getActualMaximum(cal.DAY_OF_MONTH);// 이번달 총 일자 찾기.
		int yoil = cal.get(cal.DAY_OF_WEEK);// 일요일부터 1.
		int week = cal.getActualMaximum(cal.WEEK_OF_MONTH);// 한달에 몇주 있는지 확인.

		DateInfo date = new DateInfo(year, month, dayOfMonth, week, yoil);

		return date;
	}

	public Restaurant getRestaurant(String id) throws SQLException {
		String query = "SELECT * FROM restaurant where no = ?";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.setString(1, id);
			rs = st.executeQuery();
			int no;
			String name, desc, location, renew = "";
			while (rs.next()) {
				no = rs.getInt("no");
				name = rs.getString("name");
				desc = rs.getString("description");
				location = rs.getString("location");
				renew = rs.getString("renew");
				// renew.replace(" ", "%20");//요거 없어도 되는데? 왜 넣어놨지..
				Restaurant rest = new Restaurant(no, name, desc, location,
						renew);
				rs.close();
				st.close();
				return rest;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs.close();
			st.close();

		}
		return null;
	}

	/**
	 * 자신의 이전달 기록을 nyamHistory페이지에서 화살표로 넘겨서 볼 수 있도록 하는 함수.
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getHistory(String id) {
		Calendar cal = Calendar.getInstance();

		int presentMonth = cal.get(Calendar.MONTH);
		int presentYear = cal.get(Calendar.YEAR);

		int initYear = Integer.parseInt(("20" + id.substring(0, 2)));
		int initMonth = 2;
		ArrayList<HashMap<String, Object>> historyList = new ArrayList<HashMap<String, Object>>();

		if (id.startsWith("11")) {// 아이디가 관리자라면 줄 조건. 관리자 아이디를 따로 만들어야 할 듯.
			initYear = 2013;
		}

		for (int i = initYear; i <= presentYear; i++) {
			HashMap<String, Object> history = new HashMap<String, Object>();
			// HashMap<String, Object> year = new HashMap<String, Object>();
			ArrayList<String> months = new ArrayList<String>();
			// 여기 코드 리뷰 해줘야 할 듯. 연규느님 .

			if (i == initYear) {
				if (i == presentYear) {// 첫 해인데, 그 해가 올해일 경우. 올해의 이번달까지만 보여줘야
					for (int j = initMonth; j <= presentMonth; j++) {
						months.add(Integer.toString(j));
					}
				} else {
					for (int j = initMonth; j < 12; j++) {
						months.add(Integer.toString(j));
					}
				}
			} else if (i == presentYear) {
				for (int j = initMonth; j <= presentMonth; j++) {
					months.add(Integer.toString(j));
				}
			} else {
				for (int j = 0; j < 12; j++) {
					months.add(Integer.toString(j));
				}
			}
			history.put("year", i);
			history.put("month", months);
			historyList.add(history);
		}
		return historyList;
	}

	public ArrayList<HashMap<String, String>> rankingHistory(int year, int month)
			throws SQLException {
		// original source
		// String rankingQuery =
		// "select s.users_id, users.name,  count(*) as c from stamp_history s "
		// +
		// "inner join users on s.users_id = users.id  where regdate > ? and regdate < ? group by users_id order by c desc";

		String rankingQuery = "SELECT user.id, user.name, count(history.regdate) as c FROM users user left join stamp_history history"
				+ " on history.regdate > ? and history.regdate <? and user.id = history.users_id group by user.id order by c desc";
		ArrayList<HashMap<String, String>> rankingList = new ArrayList<HashMap<String, String>>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = con.prepareStatement(rankingQuery);
			String firstDay = makePeriod("first", year, month);
			String lastDay = makePeriod("last", year, month);
			statement.setString(1, firstDay);
			statement.setString(2, lastDay);

			rs = statement.executeQuery();

			while (rs.next()) {
				HashMap<String, String> nyamRanking = new HashMap<String, String>();
				String id = rs.getString("id");
				String nyamNum = Integer.toString(rs.getInt("c"));
				String name = rs.getString("name");

				nyamRanking.put("id", id);
				nyamRanking.put("name", name);
				nyamRanking.put("nyamNum", nyamNum);
				rankingList.add(nyamRanking);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
		}
		return rankingList;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 로그인 돼있는지 boolean으로 확인
	 * 
	 * @param session
	 * @return
	 */
	public static boolean checkLogin(HttpSession session) {

		String id = (String) session.getId();

		if (id == null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * board를 DB에 넣는 함수.
	 * 
	 * @param board
	 */
	public void insertBoard(Board board) {
		// 일단 파일이 없는걸로 테스트해보자.
		PreparedStatement ps;
		String insertQuery;
		if (board.getFileName() == null) {
			 insertQuery = "INSERT INTO request_board (title, contents, users_id) values (?,?,?)";

			try {
				ps = con.prepareStatement(insertQuery);
				ps.setString(1, board.getTitle());
				ps.setString(2, board.getContent());
				ps.setString(3, board.getUserId());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			 insertQuery = "INSERT INTO request_board (title, contents, users_id, file_name) values (?,?,?,?)";
			try {
				ps = con.prepareStatement(insertQuery);
				ps.setString(1, board.getTitle());
				ps.setString(2, board.getContent());
				ps.setString(3, board.getUserId());
				ps.setString(4, board.getFileName());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public ArrayList<Board> getWritingList() {
		Statement state;
		ResultSet rs;
		ArrayList<Board> arrBoard = new ArrayList<Board>();
		String query= "SELECT * FROM request_board ORDER BY no DESC LIMIT 20";
		try {
			state = con.createStatement();
			rs= state.executeQuery(query);
			while(rs.next()){
				String no = rs.getString("no");
				String writer = rs.getString("users_id");
				String contents = rs.getString("contents");
				String fileName = rs.getString("file_name");
				String title = rs.getString("title");
				
				Board board = new Board(title, contents, fileName, writer, no);
				logger.debug(board.toString());
				arrBoard.add(board);
						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrBoard;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
