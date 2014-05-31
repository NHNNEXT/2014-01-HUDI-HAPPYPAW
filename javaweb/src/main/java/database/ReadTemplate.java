package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ReadTemplate extends DAOTemplate{
	ReadTemplate(String query, Object... objects) {
		super(query, objects);
		// TODO Auto-generated constructor stub
	}



	public abstract Object read(ResultSet rs) throws SQLException;

	public Object execute() {
		ResultSet rs = null;
		Object returnValue = null;
		try {

			rs = statement.executeQuery();
			returnValue = read(rs);
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return returnValue;
	}
}
