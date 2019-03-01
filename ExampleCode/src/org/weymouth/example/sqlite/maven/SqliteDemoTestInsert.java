package org.weymouth.example.sqlite.maven;

/* ***********************************************
 * This code copied/changed from code sample on this page:
 *   https://bitbucket.org/xerial/sqlite-jdbc
 * ***********************************************
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDemoTestInsert {

	private static final String TABLE = "person"; 
	private static final String TABLE_CREATE = "create table person (id INTEGER PRIMARY KEY, name string)";

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10); // in seconds
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean foundTable = false;
			while (rs.next()) {
				String name = rs.getString("TABLE_NAME");
				if (TABLE.equals(name)) {
					foundTable = true;
				}
			}
			if (!foundTable) {
				statement.executeUpdate(TABLE_CREATE);
			}
			statement.executeUpdate("insert into person(name) values('Able')");
			statement.executeUpdate("insert into person(name) values('Baker')");
			rs = statement.executeQuery("select * from person");
			while (rs.next()) {
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
			}
			// sleep to enable enable an interrupt of the running code
			Thread.sleep(100*1000);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}
}