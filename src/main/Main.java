package main;

import java.sql.SQLException;

public class Main {
	
	/* Starts up program */
	public static void main(String[] args) throws SQLException {
		SQLOps sqlOps = new SQLOps();
		Window window = new Window(sqlOps,sqlOps.getConnection());
		window.sendInfo();
	}
}
