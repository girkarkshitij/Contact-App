package com.techlab.contact;

import java.sql.*;

public class Contact {
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet res = null;

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/swabhav techlabs";
	private static final String USER = "root";
	private static final String PASS = "root";

	public void connect() throws ClassNotFoundException {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public void display() throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist.");
		}
		pstmt = conn.prepareStatement("SELECT * FROM contacts ORDER BY fname, lname;");
		res = pstmt.executeQuery();
		System.out.println("First Name" + "\t" + "Last Name" + "\t" + "Number" + "\t\t\t" + "Email");
		while (res.next()) {
			System.out.println(res.getString(1) + "\t\t" + res.getString(2) + "\t\t" + res.getString(3) + "\t\t"
					+ res.getString(4));
		}
		res.close();
	}

	public void add(String fname, String lname, String number, String email) throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist.");
		}
		pstmt = conn.prepareStatement("INSERT INTO contacts VALUES(?,?,?,?);");
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		pstmt.setString(3, number);
		pstmt.setString(4, email);
		int rows = pstmt.executeUpdate();
		System.out.println("Inserted !");
	}

	public void delete(String fname, String lname) throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist.");
		}
		pstmt = conn.prepareStatement("DELETE FROM contacts WHERE fname=? AND lname=?;");
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		int rows = pstmt.executeUpdate();
		System.out.println("Deleted!");
	}

	public boolean contactExists(String fname, String lname) throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist");
		}
		res = stmt.executeQuery("SELECT * FROM contacts WHERE fname='" + fname + "' AND lname='" + lname + "'");
		boolean result = res.next();
		res.close();
		return result;
	}

	public boolean numberExists(String number) throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist");
		}
		res = stmt.executeQuery("SELECT * FROM contacts WHERE number='" + number + "'");
		boolean result = res.next();
		res.close();
		return result;
	}

	public boolean emailExists(String email) throws SQLException {
		if (stmt == null) {
			System.err.println("Connection does not exist");
		}
		res = stmt.executeQuery("SELECT * FROM contacts WHERE email='" + email + "'");
		boolean result = res.next();
		res.close();
		return result;
	}
}
