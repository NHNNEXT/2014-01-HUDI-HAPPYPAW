package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAOTemplate {
//	protected String url = "jdbc:mysql://10.73.45.131/happypaw";
//	protected String user = "dayoungles";
//	protected String pw = "ekdudrmf2";
	protected String url = "jdbc:mysql://localhost/happypaw";
	protected String user = "itoolsg";
	protected String pw = "ccccc1";
	protected Connection con;
	protected String query;
	protected PreparedStatement statement;

	
	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("driver load error:");
			return null;
		}
		//System.out.println("driver loading success");
		try {
			con = DriverManager.getConnection(url, user, pw);

		} catch (SQLException e) {
			System.err.println("connect fail:" + e.getMessage());
			return null;
		}
		//System.out.println("connection success");
		return con;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	DAOTemplate(String query, Object... objects) {
		try {
			connect();
			this.query = query;
			statement = con.prepareStatement(query);
			for (int i = 0; i < objects.length; ++i) {
				statement.setObject(i + 1, objects[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}

	}
	
	public Object execute() {
		return null;
	}
}
