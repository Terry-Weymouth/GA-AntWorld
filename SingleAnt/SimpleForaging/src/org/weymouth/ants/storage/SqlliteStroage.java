package org.weymouth.ants.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.weymouth.ants.core.Network;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public class SqlliteStroage {
	
	private final boolean OK;
	private final String TABLE = "network";
	private final String TABLE_CREATE = "create table network "
			+ "(id integer AUTOINCREMENT, epoc_time integer, score real, string network_json)";
	private final String INSERT_QUERY = "insert into network"
			+ "(epoc_time, score real, network_json) values (?, ?, ?)";
	private Connection connection;
	
	public SqlliteStroage() {
		// code fragments from sample code: https://bitbucket.org/xerial/sqlite-jdbc
		boolean setupFlag = false;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);  // in seconds
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean foundTable = false;
		    while (rs.next()) {
		    	if (TABLE == rs.getString("TABLE_NAME"))
		    		foundTable = true;
		    }
		    if (!foundTable) {
		    	statement.executeUpdate(TABLE_CREATE);
		    }
			setupFlag = true;
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			setupFlag = false;
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
			setupFlag = false;
		}
		finally
		{
			try
			{
				if(connection != null)
					connection.close();
				setupFlag = true;
			}
			catch(SQLException e)
			{
				// connection close failed.
				System.err.println(e);
				setupFlag = false;
			}
		}
		this.OK = setupFlag;
	}
	
	public int storeNetwork(Network network) throws StorageException {
		if (!OK) {
			throw new StorageException("Network Storage not properly set up.");
		}
		int ret = -1;
		try {
			long epoch_time = System.currentTimeMillis();
			PreparedStatement pStatement = connection.prepareStatement(INSERT_QUERY);
			pStatement.setLong(1, epoch_time);
			pStatement.setDouble(2, network.getScore());
			pStatement.setString(3, network.toJson());
			pStatement.executeUpdate();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ROWID()");
			rs.next();
			ret = rs.getInt(0);
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}
		return ret;
	}
}
