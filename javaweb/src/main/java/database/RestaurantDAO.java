package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

public class RestaurantDAO {
	static RestaurantDAO nyam;

	private static Logger logger = LoggerFactory.getLogger(RestaurantDAO.class);

	private RestaurantDAO() {
	}

	public HashMap<String, String> getRestaurant(String no) {
		String query = "select * from restaurant where no = ?";
		ReadTemplate<HashMap<String, String>> template = new ReadTemplate<HashMap<String, String>>(
				query, no) {

			@Override
			public HashMap<String, String> read(ResultSet rs)
					throws SQLException {
				HashMap<String, String> hash = new HashMap<String, String>();
				while(rs.next()) {
					hash.put("no", rs.getString("no"));
					hash.put("name", rs.getString("name"));
					hash.put("desc", rs.getString("description"));
					hash.put("location", rs.getString("location"));
					return hash;
				}
				return null;
			}
		};
		
		return template.execute();
	}

	public static RestaurantDAO getInstance() {
		if (nyam == null) {
			nyam = new RestaurantDAO();
		}
		return nyam;
	}
}
