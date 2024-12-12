package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class JobDao {
	protected ConnectionManager connectionManager;
	private static JobDao instance = null;
	
	protected JobDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static JobDao getInstance() {
		if (instance == null) {
			instance = new JobDao();
		}
		return instance;
	}
	
	public Job create(Job job) throws SQLException {
		String insertJob = "INSERT INTO Job(jobName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;

		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertJob);
			insertStmt.setString(1, job.getJobName());
			insertStmt.executeUpdate();

			return job;
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
	
	public Job getJobByJobName(String jobName) throws SQLException {
		String selectJob =
				"SELECT jobName " +
				"FROM Job " +
				"WHERE jobName=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectJob);
				selectStmt.setString(1, jobName);
				results = selectStmt.executeQuery();
				if(results.next()) {
					String resultJobName = results.getString("jobName");
					
					Job job = new Job(resultJobName);
					
					return job;
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
	
	public Job delete(Job job) throws SQLException {
		String deleteJob = "DELETE FROM Job WHERE JobName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteJob);
			deleteStmt.setString(1, job.getJobName());
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
