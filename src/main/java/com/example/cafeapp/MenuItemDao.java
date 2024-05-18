package com.example.cafeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class MenuItemDao {

	private static final Logger logger = Logger.getLogger(CustomerDao.class.getName());

	public static List<MenuItem> getMenu() throws SQLException {
		Connection connection = Database.dbConnection;
		List<MenuItem> menuItems = new ArrayList<>();
		PreparedStatement statement = null;

		try {
			connection.setAutoCommit(false);
			String query = "SELECT id, name, price, type FROM menu";
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				double price = Double.parseDouble(resultSet.getString(3));
				String type = resultSet.getString(4);
				menuItems.add(new MenuItem(id, name, price, type));
			}
			return menuItems;
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return menuItems;
	}

}
