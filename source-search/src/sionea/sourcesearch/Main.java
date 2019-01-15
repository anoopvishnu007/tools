package sionea.sourcesearch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;

public class Main {

	public static void main(String[] args) {
		try {
			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL("jdbc:oracle:thin:system/k@//localhost:1521/K");
			Connection conn = ds.getConnection("system", "k");
			// dbConnection = DriverManager.getConnection(
			// "jdbc:oracle:thin:@ldbap://oid1.xxx.net:389" + database +
			// ",cn=OracleContext,dc=arizon,dc=net");
			// dbConnection =
			// DriverManager.getConnection("jdbc:oracle:thin:system/k@//localhost:1521:K");
			// dbConnection =
			// DriverManager.getConnection("jdbc:oracle:thin:"+user+"/"+password+"@"+database+":1521:K");

			// Create a statement
			Statement stmt = conn.createStatement();

			// Execute the query
			String query = "select 'Hello JDBC: ' || sysdate from dual";
			System.out.println("Executing query " + query + "\n");
			ResultSet rset = stmt.executeQuery(query);

			// Dump the result
			while (rset.next())
				System.out.println(rset.getString(1) + "\n");

			// We're done
			System.out.println("done.\n");

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
