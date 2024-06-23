package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static final String DATABASE_URL = "jdbc:sqlserver://quanlv\\\\SQLEXPRESS:1433;databaseName=QLTKClient";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "12345678";
	private Connection connection;

	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("Kết nối thất bại!");
			}
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DBConnect db = new DBConnect();
		db.connect();

	}
}
