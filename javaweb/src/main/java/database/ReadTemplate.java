package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ReadTemplate<T> extends DAOTemplate{
	ReadTemplate(String query, Object... objects) {
		super(query, objects);
		// TODO Auto-generated constructor stub
	}



	public abstract T read(ResultSet rs) throws SQLException;

	public T execute() {
		ResultSet rs = null;
		T returnValue = null;
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
