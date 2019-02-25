package org.weymouth.ants.storage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.weymouth.ants.core.NetworkPojo;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public class SqlliteStorage {
	
	private final String TABLE = "network";
	private final String TABLE_CREATE = "create table network "
			+ "(id INTEGER PRIMARY KEY, epoc_time integer, score real, network_json string)";
	private final String INSERT_QUERY = "insert into network"
			+ "(epoc_time, score, network_json) values (?, ?, ?)";
	private final String SELECT_QUERY = "select * from network where id=?";
	private Connection connection;
	
	public SqlliteStorage() throws ClassNotFoundException, SQLException {
		// code fragments from sample code: https://bitbucket.org/xerial/sqlite-jdbc
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:network.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(10);  // in seconds
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
	}
	
	public void close() throws SQLException {
		if(connection != null)
			connection.close();
	}
	
	public int storeNetwork(NetworkPojo networkPojo) throws SQLException, JsonProcessingException {
		int ret = -1;
		long epoch_time = System.currentTimeMillis();
		PreparedStatement pStatement = connection.prepareStatement(INSERT_QUERY);
		pStatement.setLong(1, epoch_time);
		pStatement.setDouble(2, networkPojo.getScore());
		pStatement.setString(3, networkPojo.toJson());
		pStatement.executeUpdate();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ROWID()");
		rs.next();
		ret = rs.getInt(1);
		return ret;
	}
	
	public NetworkPojo recoverNetwork(int id) throws SQLException, IOException {
		PreparedStatement pStatement = connection.prepareStatement(SELECT_QUERY);
		pStatement.setInt(1, id);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		return NetworkPojo.compose(rs.getString(4));
	}

	public NetworkPojo[] getTop(int n) throws SQLException, IOException {
		String query = "select * from network order by score limit ?";
		PreparedStatement pStatement = connection.prepareStatement(query);
		pStatement.setInt(1,n);
		ResultSet rs = pStatement.executeQuery();
		ArrayList<NetworkPojo> holder = new ArrayList<NetworkPojo>();
		while(rs.next()) {
			holder.add(NetworkPojo.compose(rs.getString(4)));
		}
		NetworkPojo[] ret = holder.toArray(new NetworkPojo[holder.size()]);
		return ret;
	}
}
