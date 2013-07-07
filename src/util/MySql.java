package util;

import java.sql.*;

public class MySql {
	static String dbUrl = "jdbc:mysql://localhost:3306/schoolmate";
	static String dbClass = "com.mysql.jdbc.Driver";
	static String user = "schoolmate";
	static String password = "schoolmate";
//	static String query = "Select * FROM courses";

	 public static void main(String args[]) {
		 executeUpdate("UPDATE courses SET coursename = 'Music<a href>mal</a>' WHERE courseid = '1'");
	 }

	public static void executeQuery(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con;
		Statement stmt;
		ResultSet rs;
		try {
			con = DriverManager.getConnection(dbUrl, user, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(4));
			} // end while

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeUpdate(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con;
		Statement stmt;
		try {
			con = DriverManager.getConnection(dbUrl, user, password);
			stmt = con.createStatement();
			stmt.executeUpdate(query);

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

} // end class