package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Item;

public class ItemDao {
	
	protected ConnectionManager connectionManager;
	private static ItemDao instance = null;
	
	protected ItemDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static ItemDao getInstance() {
		if (instance == null) {
			instance = new ItemDao();
		}
		return instance;
	}
	
	public Item create(Item item) throws SQLException {
		String insertItem = "INSERT INTO Item(itemName, maxStackSize, vendorPrice, itemLevel) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, item.getItemName());
			insertStmt.setInt(2, item.getMaxStackSize());
			insertStmt.setInt(3, item.getVendorPrice());
			insertStmt.setInt(4, item.getItemLevel());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int itemID = -1;
			if (resultKey.next()) {
				itemID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			item.setItemID(itemID);
			return item;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public Item getItemByItemID(int itemID) throws SQLException {
		String selectItem = "SELECT itemID, itemName, maxStackSize, vendorPrice, itemLevel "
				+ "FROM Item "
				+ "WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectItem);
			selectStmt.setInt(1, itemID);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int resultItemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				int vendorPrice = results.getInt("vendorPrice");
				int itemLevel = results.getInt("itemLevel");
				Item item = new Item(resultItemID, itemName, maxStackSize, vendorPrice, itemLevel);
				return item;
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
	
	public List<Item> getAllItems() throws SQLException {
		List<Item> items = new ArrayList<Item>();
		String selectAllItems = "SELECT itemID, itemName, maxStackSize, vendorPrice, itemLevel "
				+ "FROM Item;";
        Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAllItems);
			results = selectStmt.executeQuery();
			while (results.next()) {
                Item item = new Item(
                        results.getInt("itemID"),
                        results.getString("itemName"),
                        results.getInt("maxStackSize"),
                        results.getInt("vendorPrice"),
                        results.getInt("itemLevel")
                    );
                items.add(item);
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
        return items;
	}
	
	public Item delete(Item item) throws SQLException {
		String deleteItem = "DELETE FROM Item WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteItem);
			deleteStmt.setInt(1, item.getItemID());
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
