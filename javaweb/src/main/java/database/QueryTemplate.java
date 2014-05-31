
package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class QueryTemplate extends DAOTemplate{
	QueryTemplate(String query, Object... objects) {
		super(query, objects);
		// TODO Auto-generated constructor stub
	}



	public Object execute() {
		boolean returnValue = false;
		try {
			returnValue = statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			close();
			return false;
		} finally {
			close();
		}
		return returnValue;
	}
	static boolean executeQuery(String query, Object...objects) {
		QueryTemplate template = new QueryTemplate(query, objects) {
		};
		return (boolean)template.execute();
	}
}