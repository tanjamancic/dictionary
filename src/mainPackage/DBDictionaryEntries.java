package mainPackage;

import java.sql.Connection;

public class DBDictionaryEntries extends DBEntries {

	public DBDictionaryEntries(Connection con) {
		super(con);
	}

	@Override
	protected String getQuery() {

		return "SELECT word FROM entries";
	}
}
