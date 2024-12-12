package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.*;

public class CurrencyDao {
	protected ConnectionManager connectionManager;
	private static CurrencyDao instance = null;
	
	protected CurrencyDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static CurrencyDao getInstance() {
		if (instance == null) {
			instance = new CurrencyDao();
		}
		return instance;
	}
	
	public Currency create(Currency currency) throws SQLException {
		String insertCurrency = "INSERT INTO Currency(Name,Cap,WeeklyCap) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;

		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCurrency);
			insertStmt.setString(1, currency.getName());
			insertStmt.setInt(2, currency.getCap());
			insertStmt.setInt(3, currency.getWeeklyCap());
			insertStmt.executeUpdate();

			return currency;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	public Currency delete(Currency currency) throws SQLException {
		String deleteCurrency = "DELETE FROM Currency WHERE Name=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCurrency);
			deleteStmt.setString(1, currency.getName());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
