package dal;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class AttributeDao {
	
protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static AttributeDao instance = null;
	protected AttributeDao() {
		connectionManager = new ConnectionManager();
	}
	public static AttributeDao getInstance() {
		if(instance == null) {
			instance = new AttributeDao();
		}
		return instance;
	}
	
	public Attribute create(Attribute attribute) throws SQLException {
		String insertAttribute = "INSERT INTO Attribute(name) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;

		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAttribute);
			insertStmt.setString(1, attribute.getName());
			insertStmt.executeUpdate();
			return attribute;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (insertStmt != null) {
				insertStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
	}
	public Attribute getAttributeByName(String name) throws SQLException {
		String selectAttribute = "SELECT name FROM Attribute WHERE name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAttribute);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();

			if (results.next()) {
				String resultName = results.getString("name");
				return new Attribute(resultName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (results != null) {
				results.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}
	public Attribute delete(String name) throws SQLException {
		String deleteAttribute = "DELETE FROM Attribute WHERE name=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;

		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAttribute);
			deleteStmt.setString(1, name);
			int affectedRows = deleteStmt.executeUpdate();

		 
			if (affectedRows > 0) {
				return new Attribute(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (deleteStmt != null) {
				deleteStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}
}


