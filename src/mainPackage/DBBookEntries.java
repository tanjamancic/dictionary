package mainPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBBookEntries extends DBEntries {

	public DBBookEntries(Connection con) {
		super(con);
	}

	@Override
	protected String getQuery() {

		return "SELECT word FROM newEntries";
	}

	private boolean newTableExists() {

		int numberOfTablesWithName = 0;

		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT count(name) FROM sqlite_master WHERE type='table' AND name='newEntries'");
			ResultSet rs = ps.executeQuery();
			numberOfTablesWithName = rs.getInt(1);

			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (numberOfTablesWithName == 1) {

			return true;
		}

		return false;

	}

	private void makeNewTable() {

		if (!newTableExists()) {

			try {
				Statement ps = con.createStatement();
				String upit = "CREATE TABLE \"newEntries\" (\r\n" + "	\"word\"	TEXT,\r\n"
						+ "	PRIMARY KEY(\"word\")\r\n" + ");";
				ps.execute(upit);
				ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addNewWords(ArrayList<String> newWords) {

		ArrayList<String> wordsFromNewEntries = new ArrayList<String>();

		if (!newTableExists()) {
			makeNewTable();
		} else {
			wordsFromNewEntries = allWordsFromEntries();
		}

		newWords.removeAll(wordsFromNewEntries);

		try {
			PreparedStatement ps = con.prepareStatement("insert into newEntries (word) values (?)");

			for (String s : newWords) {
				ps.setString(1, s);
				ps.execute();
			}
			System.out.println("New words, in the alphabetical order, added to the new table in dictionary.\n");
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
