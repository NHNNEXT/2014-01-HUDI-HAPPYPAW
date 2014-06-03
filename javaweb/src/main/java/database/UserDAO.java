package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

public class UserDAO {
	static UserDAO nyam;

	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		if (nyam == null) {
			nyam = new UserDAO();
		}
		return nyam;
	}

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

	public ArrayList<HashMap<String, String>> getMyRanking(User user) {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String firstDay = makePeriod("first", year, month);
		String lastDay = makePeriod("last", year, month);

		int count = user.getNum();
		String rankingPrevQuery = "SELECT user.id, user.name, count(history.regdate) as c FROM users user left join stamp_history history"
				+ " on history.regdate > ? and history.regdate <? and user.id = history.users_id and c >= ? group by user.id order by c asc limit 0, 7";
		
		String rankingNextQuery = "SELECT user.id, user.name, count(history.regdate) as c FROM users user left join stamp_history history"
				+ " on history.regdate > ? and history.regdate <? and user.id = history.users_id and c <= ? group by user.id order by c desc limit 0, 7";
		ReadTemplate<ArrayList<HashMap<String, String>>> template = new ReadTemplate<ArrayList<HashMap<String, String>>>(
				rankingPrevQuery, firstDay, lastDay, count) {
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

}
