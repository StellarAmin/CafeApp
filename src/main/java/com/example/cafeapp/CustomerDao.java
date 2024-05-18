package com.example.cafeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class CustomerDao {

	private static final Logger logger = Logger.getLogger(CustomerDao.class.getName());
	public static Customer customer = null;

	public static boolean searchCustomer() throws SQLException {
		Connection connection = Database.dbConnection;
		PreparedStatement statement = null;
		boolean flag = false;

		try {
			connection.setAutoCommit(false);
			String query = "SELECT id, name, email FROM customer WHERE email = ?";
			statement = connection.prepareStatement(query);
			int counter = 1;
			statement.setString(counter++, Customer.getEmail());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				flag = true;
				Customer.setId(resultSet.getInt(1));
				Customer.setName(resultSet.getString(2));
				Customer.setEmail(resultSet.getString(3));
			}

			return flag;
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return flag;

	}

	public static int saveCustomer() throws SQLException {
		Connection connection = Database.dbConnection;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection.setAutoCommit(false);
			String query = "INSERT INTO Customer(name, email, password) VALUES(?, ?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setString(counter++, Customer.getName());
			statement.setString(counter++, Customer.getEmail());
			statement.setString(counter++, Customer.getPasword());
			statement.executeUpdate();
			connection.commit();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				searchCustomer();
				return resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
			if (connection != null) {
				connection.rollback();
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		}

		return 0;
	}

}
