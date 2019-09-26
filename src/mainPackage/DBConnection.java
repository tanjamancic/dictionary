package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	protected String connectionString;
	protected Connection con;
	protected static DBConnection db;

	private DBConnection(String conStr) {
		connectionString = conStr;
		db = this;
	}

	public static DBConnection getConnection(String conStr) {
		if (db == null) {
			db = new DBConnection(conStr);
		}
		return db;
	}

	public Connection connect() {
		try {
			con = DriverManager.getConnection(connectionString);
			System.out.println("Connection to base opened.\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	public void disconnect() {
		try {
			if (con != null && !con.isClosed()) {
				System.out.println("Connection to base closed.\n");
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
