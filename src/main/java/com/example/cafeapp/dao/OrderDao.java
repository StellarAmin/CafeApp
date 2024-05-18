package com.example.cafeapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.cafeapp.Customer;
import com.example.cafeapp.Database;
import com.example.cafeapp.MenuItem;
import com.example.cafeapp.ShoppingCart;

public class OrderDao {

	private static final Logger logger = Logger.getLogger(CustomerDao.class.getName());
	public static Customer customer = null;

	public static int getLastOrderId() throws SQLException {
		Connection connection = Database.dbConnection;
		PreparedStatement statement = null;
		int id = 0;

		try {
			connection.setAutoCommit(false);
			String query = "INSERT INTO `order`(customer_id) VALUES(?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int counter = 1;
			statement.setInt(counter++, Customer.getId());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
			if (connection != null) {
				connection.rollback();
			}
		} finally {
			if (statement != null) {
				statement.close();
			}

		}

		try {
			connection.setAutoCommit(false);
			String query = "SELECT id FROM `order` ORDER BY id DESC LIMIT 1";
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}

			return id;
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return id;
	}

	public static void addOrder() throws SQLException {
		List<MenuItem> items = ShoppingCart.getItems();
		Connection connection = Database.dbConnection;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int orderId = 0;
		try {
			orderId = getLastOrderId();
			connection.setAutoCommit(false);
			String query = "INSERT INTO order_menu(order_id, menu_id) VALUES(?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			for (MenuItem item : items) {
				statement.setInt(1, orderId);
				statement.setInt(2, item.getId());
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			resultSet = statement.getGeneratedKeys();
		} catch (SQLException exception) {
			exception.printStackTrace();
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
	}

}
