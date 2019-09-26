package mainPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DBEntries {

	protected Connection con;

	public DBEntries(Connection con) {
		super();
		this.con = con;
	}

	protected abstract String getQuery();

	public ArrayList<String> allWordsFromEntries() {

		String word;
		ArrayList<String> dictionary = new ArrayList<String>();

		try {
			PreparedStatement ps = con.prepareStatement(getQuery());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				word = rs.getString(1);
				word = word.toLowerCase();
				dictionary.add(word);
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dictionary;

	}
}
