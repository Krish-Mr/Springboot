package com.bookmaster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class SQLConnection {
	private static Connection con;
	public SQLConnection() {}
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static Connection createConnection(String url, String userName, String pass) {
		try{
			Connection con = DriverManager.getConnection(url, userName, pass);
			System.out.println("Connection successfull");
			return con;
		}catch(SQLException sq) {
			sq.printStackTrace();
			return null;
		}
	}

	public static Connection getDBConection(String url, String userName, String pass) {
		if(con==null) {
			con = createConnection(url, userName, pass);
		}
		return con;
	}
	
	public static void CloseConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
