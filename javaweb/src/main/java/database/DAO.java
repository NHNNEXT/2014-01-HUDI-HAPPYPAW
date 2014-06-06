package database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.Board;
import model.DateInfo;
import model.MakeExcel;
import model.NyamList;
import model.Restaurant;
import model.StampHistory;
import model.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DAO {
	// xml을 키밸류로 만들고. xml을 읽어서 자바 오브젝트로 만들어서 띄워놓으면 얘를 사용할 수 있는 라이브러리를 써야된다.

	static DAO nyam;

	private static Logger logger = LoggerFactory.getLogger(DAO.class);

	private DAO() {
		// connect();
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
		String datee = null;
		try {

			Calendar cal = Calendar.getInstance();

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

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
		} catch (Exception e) {
			e.printStackTrace();
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

		String firstDay = makePeriod("first", year, month);
		String lastDay = makePeriod("last", year, month);
		String query = "select * from stamp_history where users_id = ? and regdate > ? and regdate < ?";
		// PreparedStatement selHistory = null;
		// ResultSet rs = null;
		//
		// try {
		// connect();
		// selHistory = con.prepareStatement(query);
		//
		// selHistory.setString(1, users_id);
		// selHistory.setString(2, firstDay);
		// selHistory.setString(3, lastDay);
		//
		// rs = selHistory.executeQuery();
		// while (rs.next()) {
		// String id = rs.getString("users_id");
		// String regdate = rs.getString("regDate");
		// int restaurant = rs.getInt("restaurant_no");
		// stampList.add(new StampHistory(id, regdate, restaurant));
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// rs.close();
		// selHistory.close();
		// close();
		//
		// }
		ReadTemplate<ArrayList<StampHistory>> template = new ReadTemplate<ArrayList<StampHistory>>(
				query, users_id, firstDay, lastDay) {

			@Override
			public ArrayList<StampHistory> read(ResultSet rs)
					throws SQLException {
				ArrayList<StampHistory> stampList = new ArrayList<StampHistory>();
				while (rs.next()) {
					String id = rs.getString("users_id");
					String regdate = rs.getString("regDate");
					int restaurant = rs.getInt("restaurant_no");
					stampList.add(new StampHistory(id, regdate, restaurant));
				}
				return stampList;
			}

		};
		return (ArrayList<StampHistory>) template.execute();
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
		// try {
		// connect();
		// PreparedStatement insertHistory;
		// insertHistory = con.prepareStatement(insertQuery);
		// insertHistory.setString(1, users_id);
		// insertHistory.setString(2, nowDate);
		// insertHistory.setInt(3, restaurant);
		// insertHistory.execute();
		// insertHistory.close();
		//
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return false;
		// } finally{
		// close();
		// }
		//
		//

		return (boolean) QueryTemplate.executeQuery(insertQuery, users_id,
				nowDate, restaurant);
	}

	/**
	 * 레스토랑 큐알코드 갱신일자가 같은지 확인하는 함수.
	 * 
	 * @param qrDate
	 * @param restaurant
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean checkRestaurant(String qrDate, int restaurant) {
		/*
		 * 레스토랑 테이블에서 레스토랑 넘버를 가지고 업데이트 날짜를 받아온다. 업데이트 날짜랑 큐알데이트가 다르면 return
		 * false; 업데이트 날짜랑 같으면 return true;
		 */
		String checkQuery = "select count(*) as c from restaurant where no = ? and renew = ?;";
		ReadTemplate<Boolean> template = new ReadTemplate<Boolean>(checkQuery,
				restaurant, qrDate) {
			@Override
			public Boolean read(ResultSet rs) throws SQLException {
				rs.next();
				int count = rs.getInt("c");
				return count == 1;
			}
		};
		return template.execute();
	}

	/**
	 * 유저를 가져옴.
	 * 
	 * @param id
	 * @return user
	 */
	public User getUser(String id) {
		String query = "select * from users where id = ?";
		ReadTemplate<User> template = new ReadTemplate<User>(query, id) {
			@Override
			public User read(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String users_id = rs.getString("id");
					String ps = "next";
					String name = rs.getString("name");

					User user = new User(users_id, ps, name);
					return user;
				}
				return null;
			}
		};
		return template.execute();
	}

	/**
	 * 관리자 웹페이지의 전체 학생 식사기록을 확인하는 함수.
	 * 
	 * @param month
	 * @param year
	 * 
	 * @return ArrayList<NyamList>
	 */
	public ArrayList<NyamList> adminNyamHistory(final int year, final int month) {
		String userQuery = "SELECT * FROM users";
		ReadTemplate<ArrayList<NyamList>> template = new ReadTemplate<ArrayList<NyamList>>(
				userQuery) {
			@Override
			public ArrayList<NyamList> read(ResultSet rs) throws SQLException {
				ArrayList<NyamList> nyamList = new ArrayList<NyamList>();
				while (rs.next()) {
					String users_id = rs.getString("id");
					String users_name = rs.getString("name");
					int sum = selectMonthHistory(users_id, year, month).size();
					nyamList.add(new NyamList(users_id, users_name, sum));
				}
				return nyamList;
			}
		};
		return template.execute();
	}

	/**
	 * 식당 별 식사기록을 확인
	 * 
	 * @return
	 */
	public ArrayList<Restaurant> restaurantHistory() {
		String countQuery = "SELECT r.no, r.name, count(s.restaurant_no) FROM restaurant "
				+ "r LEFT JOIN stamp_history s ON s.restaurant_no = r.no group by r.no;";
		ReadTemplate<ArrayList<Restaurant>> template = new ReadTemplate<ArrayList<Restaurant>>(
				countQuery) {
			@Override
			public ArrayList<Restaurant> read(ResultSet rs) throws SQLException {
				ArrayList<Restaurant> restaurant = new ArrayList<Restaurant>();
				while (rs.next()) {
					int rId = rs.getInt("no");
					String rName = rs.getString("name");
					int num = rs.getInt("count(s.restaurant_no)");
					restaurant.add(new Restaurant(rId, rName, num));
				}
				return restaurant;
			}
		};
		return template.execute();
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
	public ArrayList<Restaurant> manageRestaurant() {
		String query = "SELECT * FROM restaurant";
		ReadTemplate<ArrayList<Restaurant>> template = new ReadTemplate<ArrayList<Restaurant>>(
				query) {
			@Override
			public ArrayList<Restaurant> read(ResultSet rs) throws SQLException {
				String name, desc, location, renew = "";
				int no;
				ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
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
				return restList;
			}
		};
		ArrayList<Restaurant> restList = template.execute();
		return restList;
	}

	/**
	 * qr코드 등록일자 갱신 하는 함수.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public void renewQrcode(String restaurantNo) {
		// Calendar cal = Calendar.getInstance();
		// SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String ymd = date.format(cal.getTime());
		// String query = "update restaurant set renew = ? where no = ?";
		// PreparedStatement pst = null;
		// try {
		// pst = con.prepareStatement(query);
		// pst.setString(1, ymd);
		// pst.setInt(2, Integer.parseInt(restaurantNo));
		// pst.executeUpdate();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// pst.close();
		// }
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ymd = date.format(cal.getTime());
		String query = "update restaurant set renew = ? where no = ?";
		QueryTemplate.executeQuery(query, ymd, Integer.parseInt(restaurantNo));

		return;
	}

	/**
	 * 레스토랑 일자별로 식사횟수 확인
	 * 
	 * @return
	 * @param id
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, String>> checkEachRestaurant(String id,
			final String month, final String year) {
		int restaurantNo = Integer.parseInt(id);
		String query = "select count(*) as num, substring(regdate, 1, 10) as stamp_date  "
				+ "from stamp_history where restaurant_no = ? group by substring(regdate, 1, 10); ";

		ReadTemplate<ArrayList<HashMap<String, String>>> template = new ReadTemplate<ArrayList<HashMap<String, String>>>(
				query, restaurantNo) {
			@Override
			public ArrayList<HashMap<String, String>> read(ResultSet rs)
					throws SQLException {
				ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
				while (rs.next()) {
					HashMap<String, String> hash = new HashMap<String, String>();
					String stampDate = rs.getString("stamp_date");
					String dbMonth = stampDate.substring(5, 7);
					String dbYear = stampDate.substring(0,4);

					if (dbMonth.startsWith("0")) {
						dbMonth = dbMonth.substring(1);
					}
					
					if (dbYear.equals(year) && dbMonth.equals(month)) {
							int num = rs.getInt("num");
							hash.put("nyamNum", Integer.toString(num));
							hash.put("stampDate", stampDate);
							array.add(hash);
					}

				}
				return array;
			}
		};

		return template.execute();
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

	public Restaurant getRestaurant(String id) {
		String query = "SELECT * FROM restaurant where no = ?";

		ReadTemplate<Restaurant> template = new ReadTemplate<Restaurant>(query,
				id) {
			@Override
			public Restaurant read(ResultSet rs) throws SQLException {
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
					return rest;
				}
				return null;
			}
		};

		return template.execute();
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

		String idYear = id.substring(0, 2);// 입학년도부터 시작
		int initYear = Integer.parseInt("20" + idYear);
		int initMonth = 2;
		ArrayList<HashMap<String, Object>> historyList = new ArrayList<HashMap<String, Object>>();

		if (id.equals("123456")) {
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
				for (int j = 0; j <= presentMonth; j++) {
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

	public ArrayList<HashMap<String, String>> rankingHistory(int year, int month) {
		// original source
		// String rankingQuery =
		// "select s.users_id, users.name,  count(*) as c from stamp_history s "
		// +
		// "inner join users on s.users_id = users.id  where regdate > ? and regdate < ? group by users_id order by c desc";

		String rankingQuery = "SELECT user.id, user.name, count(history.regdate) as c FROM users user left join stamp_history history"
				+ " on history.regdate > ? and history.regdate <? and user.id = history.users_id group by user.id order by c desc";

		String firstDay = makePeriod("first", year, month);
		String lastDay = makePeriod("last", year, month);

		ReadTemplate<ArrayList<HashMap<String, String>>> template = new ReadTemplate<ArrayList<HashMap<String, String>>>(
				rankingQuery, firstDay, lastDay) {
			@Override
			public ArrayList<HashMap<String, String>> read(ResultSet rs)
					throws SQLException {
				ArrayList<HashMap<String, String>> rankingList = new ArrayList<HashMap<String, String>>();
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
				return rankingList;
			}
		};
		return template.execute();
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
		String query;
		if (board.getFileName() == null) {
			query = "INSERT INTO request_board (title, contents, users_id) values (?,?,?)";
			QueryTemplate template = new QueryTemplate(query, board.getTitle(),
					board.getContent(), board.getUserId()) {
			};
			template.execute();
		} else {
			query = "INSERT INTO request_board (title, contents, users_id, file_name) values (?,?,?,?)";
			QueryTemplate template = new QueryTemplate(query, board.getTitle(),
					board.getContent(), board.getUserId(), board.getFileName()) {
			};
			template.execute();
		}
		insertRecommendNo();// foreign keyㅋㅋ연동!

	}

	/**
	 * 글 하나를 생성하면 글 no가 생기는데, 그 no를 찾아서 추천 테이블에 입력해주는 것. 그런데 이 상태로라면 문제가 생길 수
	 * 있다.title이랑 전부다 매치하는게 효율적으로는 떨어지지만 정확한데, 어떤게 더 좋은 잘 모르겠음.
	 */
	public void insertRecommendNo() {

		final String insertRecommend = "INSERT INTO recommend(no) values (?)";
		String selectNo = "SELECT (no) FROM request_board ORDER BY no DESC LIMIT 1";

		ReadTemplate<Object> template = new ReadTemplate<Object>(selectNo) {

			@Override
			public Object read(ResultSet rs) throws SQLException {
				int no = 0;
				while (rs.next()) {
					no = rs.getInt("no");

					QueryTemplate.executeQuery(insertRecommend, no);
				}
				return null;
			}
		};
		template.execute();
	}

	/**
	 * boardList페이지 함수. 글을 15개 찾아서되돌려준다.
	 * 
	 * @return 어레이 리스트<hashmap>ㅋ
	 */
	public ArrayList<HashMap<String, String>> getBoardList() {

		String query = "select * from request_board b inner join recommend r on b.no = r.no order by r.no desc limit 15;";
		ReadTemplate<ArrayList<HashMap<String, String>>> template = new ReadTemplate<ArrayList<HashMap<String, String>>>(
				query) {

			@Override
			public ArrayList<HashMap<String, String>> read(ResultSet rs)
					throws SQLException {
				ArrayList<HashMap<String, String>> arrBoard = new ArrayList<HashMap<String, String>>();
				while (rs.next()) {
					HashMap<String, String> map = new HashMap<String, String>();
					String writer = rs.getString("users_id");
					User user = getUser(writer);

					map.put("no", rs.getString("r.no"));
					map.put("userId", rs.getString("users_id"));
					map.put("contents", rs.getString("contents"));
					map.put("fileName", rs.getString("file_name"));
					map.put("title", rs.getString("title"));
					map.put("date", rs.getString("date"));
					map.put("recommend", rs.getString("recommend"));
					map.put("notRecommend", rs.getString("not_recommend"));

					arrBoard.add(map);

				}
				return arrBoard;
			}

		};

		return template.execute();
	}

	public void plusRecommend(String id) {
		String plusRecommend = "UPDATE recommend SET recommend = recommend+1 where no=?";
		QueryTemplate.executeQuery(plusRecommend, id);
	}

	public Board getBoard(final int no) {
		String selectBoard = "SELECT * from request_board where no = ?";
		ReadTemplate<Board> template = new ReadTemplate<Board>(selectBoard, no) {

			@Override
			public Board read(ResultSet rs) throws SQLException {
				Board board;
				while (rs.next()) {
					String title = rs.getString("title");
					String contents = rs.getString("contents");
					String usersId = Integer.toString(rs.getInt("users_id"));
					String fileName = rs.getString("file_name");
					String date = rs.getString("date");

					board = new Board(title, contents, usersId, no + "", date);
					board.setFileName(fileName);
					return board;
				}
				return null;
			}
		};
		return template.execute();
	}

	public void minusRecommend(String id) {
		String minusRecommend = "UPDATE recommend SET not_recommend = not_recommend + 1 where no=?";
		QueryTemplate.executeQuery(minusRecommend, id);
	}

	/**
	 * pageview에서 추천 비추천 확인하도록.
	 * 
	 * @param id
	 * @return
	 */
	public HashMap<String, Integer> getRecommend(int id) {

		String query = "SELECT * FROM recommend where no = ?";
		ReadTemplate<HashMap<String, Integer>> template = new ReadTemplate<HashMap<String, Integer>>(
				query, id) {

			@Override
			public HashMap<String, Integer> read(ResultSet rs)
					throws SQLException {
				HashMap<String, Integer> recommendTable = new HashMap<String, Integer>();
				while (rs.next()) {

					int recommend = rs.getInt("recommend");
					int notRecommend = rs.getInt("not_recommend");

					recommendTable.put("recommend", recommend);
					recommendTable.put("notRecommend", notRecommend);

				}
				return recommendTable;
			}

		};
		return template.execute();

	}

	public void deleteWriting(String no) {
		String requestBoardQuery = "delete from request_board where no=?";
		String recommendQuery = "delete from recommend where no = ?";
		QueryTemplate.executeQuery(requestBoardQuery, no);
		QueryTemplate.executeQuery(recommendQuery, no);

	}

	public ArrayList<HashMap<String, Integer>> getRecommendList() {
		String query = "SELECT * FROM recommend ORDER BY no DESC LIMIT 15";
		ReadTemplate<ArrayList<HashMap<String, Integer>>> template = new ReadTemplate<ArrayList<HashMap<String, Integer>>>(
				query) {

			@Override
			public ArrayList<HashMap<String, Integer>> read(ResultSet rs)
					throws SQLException {
				ArrayList<HashMap<String, Integer>> arrRecommend = new ArrayList<HashMap<String, Integer>>();
				while (rs.next()) {
					String no = rs.getString("no");
					String recommend = rs.getString("recommend");
					String notRecommend = rs.getString("not_recommend");
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					map.put("no", Integer.parseInt(no));
					map.put("recommend", Integer.parseInt(recommend));
					map.put("notRecommend", Integer.parseInt(notRecommend));

					arrRecommend.add(map);
				}
				return arrRecommend;
			}

		};
		return template.execute();
	}

	public void insertRest(String name, String desc, String location) {
		String query = "INSERT INTO restaurant(name, description, location) VALUES(?, ?, ?)";
		QueryTemplate.executeQuery(query, name, desc, location);

	}
}
