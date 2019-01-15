package sionea.sourcesearch.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;
import sionea.sourcesearch.SearchResult;


public class ConnectionSingleton {
	private ConnectionSingleton() {

	}

	private Connection dbConnection;

	private static class SingletonHelper {
		private static final ConnectionSingleton INSTANCE = new ConnectionSingleton();
	}

	public static ConnectionSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public void connect(String database, String user, String password) {
		dbConnection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", password);

		try {
			
			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL("jdbc:oracle:thin:"+user+"/"+password+"@//"+database);
			dbConnection = ds.getConnection(user,password); 
			dbConnection.setAutoCommit(false);
			// dbConnection = DriverManager.getConnection(
			// "jdbc:oracle:thin:@ldbap://oid1.xxx.net:389" + database +
			// ",cn=OracleContext,dc=arizon,dc=net");
	        // Create a statement
	        
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public ArrayList<SearchResult> getResult(String query) {
		
		try {
			ArrayList<SearchResult> list = new ArrayList<SearchResult>();
			PreparedStatement stmt = dbConnection.prepareStatement("select source_id, source_name, source_type, source_owner_id, count from sionea.search(?, '-') order by source_name, source_type");
			stmt.setString(1, query);
			ResultSet rset = stmt.executeQuery();

	        // Dump the result
	        while (rset.next ()) {
	        	System.out.println(rset.getString(1) + "\n");
	        	list.add(new SearchResult(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getInt(4), rset.getInt(5)));
	        }
	        return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
		return null;
	}
	
	public String getText(int sourceId) {
		try {
			PreparedStatement stmt = dbConnection.prepareStatement("select text from sionea.search(null, '+', ?)");
			stmt.setInt(1, sourceId);
			ResultSet rset = stmt.executeQuery();
			String result = "";

	        // Dump the result
	        while (rset.next ()) {
	        	result = rset.getString(1);
	        	break;
	        }

	        return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
