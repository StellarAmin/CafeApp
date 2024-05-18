package com.example.cafeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
	
	  private static final Logger logger = Logger.getLogger(Database.class.getName());
		private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:55555/cafeappdb";
		private static final String DB_USER = "root";
		private static final String DB_PASSWORD = "";
		public static Connection dbConnection;
		
		private Database() {
			
		}
		
		public static void getDBConnection() throws SQLException {
			Connection connection = null;

			try {
				Class.forName(DB_DRIVER);
			} catch (ClassNotFoundException exception) {
				logger.log(Level.SEVERE, exception.getMessage());
			}

			try {
				connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
				dbConnection = connection;
			} catch (SQLException exception) {
				logger.log(Level.SEVERE, exception.getMessage());
			}

			dbConnection = connection;
		}
		
	}