package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;


public class PlayerAccountDao {
	protected ConnectionManager connectionManager;
	
	private static PlayerAccountDao instance = null;
	protected PlayerAccountDao() {
		connectionManager = new ConnectionManager();
	}
	public static PlayerAccountDao getInstance() {
		if(instance == null) {
			instance = new PlayerAccountDao();
		}
		return instance;
	}
	
	public PlayerAccount create(PlayerAccount playerAccount) throws SQLException {
		String insertPlayerAccount = "INSERT INTO PlayerAccount(email,name,activeStatus) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPlayerAccount);

			insertStmt.setString(1, playerAccount.getEmail());
			insertStmt.setString(2, playerAccount.getName());
			insertStmt.setBoolean(3, playerAccount.getActiveStatus());

			insertStmt.executeUpdate();
			
			return playerAccount;
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
	
	public PlayerAccount getPlayerAccountByEmail(String email) throws SQLException {
		String selectPlayerAccount = "SELECT name,email,activeStatus FROM PlayerAccount WHERE email=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPlayerAccount);
			selectStmt.setString(1, email);

			results = selectStmt.executeQuery();

			if(results.next()) {
				String name = results.getString("name");
				Boolean activeStatus = results.getBoolean("activeStatus");
				PlayerAccount playerAccount = new PlayerAccount(email, name, activeStatus);
				return playerAccount;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
}
