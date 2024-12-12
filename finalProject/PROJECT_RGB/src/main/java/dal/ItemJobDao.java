package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.*;

public class ItemJobDao {
	protected ConnectionManager connectionManager;
	private static ItemJobDao instance = null;
	
	protected ItemJobDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static ItemJobDao getInstance() {
		if (instance == null) {
			instance = new ItemJobDao();
		}
		return instance;
	}
	
	public ItemJob create(ItemJob itemJob) throws SQLException {
		String insertItemJob =
			"INSERT INTO ItemJob(ItemID,Job) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertItemJob);
			insertStmt.setInt(1, itemJob.getItem().getItemID());
			insertStmt.setString(2, itemJob.getJob().getJobName());
			insertStmt.executeUpdate();
			
			return itemJob;
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
	
	public ItemJob delete(ItemJob itemJob) throws SQLException {
		String deleteItemJob = "DELETE FROM ItemJob WHERE ItemId=? AND JobName =?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteItemJob);
			deleteStmt.setInt(1, itemJob.getItem().getItemID());
			deleteStmt.setString(2, itemJob.getJob().getJobName());
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
