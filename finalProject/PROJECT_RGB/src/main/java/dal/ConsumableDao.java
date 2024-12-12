package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Consumable;
import model.Item;

public class ConsumableDao extends ItemDao {

	private static ConsumableDao instance = null;
	
	protected ConsumableDao() {
		super();
	}
	
	public static ConsumableDao getInstance() {
		if (instance == null) {
			instance = new ConsumableDao();
		}
		return instance;
	}
	
	public Consumable create(Consumable consumable) throws SQLException {
		Item item = create(new Item(consumable.getItemName(), consumable.getMaxStackSize(), consumable.getVendorPrice(), consumable.getItemLevel()));
		
		String insertConsumable = "INSERT INTO Consumable(consumableID, description) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertConsumable);
			insertStmt.setInt(1, item.getItemID());
			insertStmt.setString(2, consumable.getDescription());
			insertStmt.executeUpdate();
			
			consumable.setItemID(item.getItemID());
			return consumable;
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
	
	public Consumable getConsumableByConsumableID(int consumableID) throws SQLException {
		String selectConsumable = "SELECT Consumable.consumableID AS consumableID, itemName, maxStackSize, vendorPrice, itemLevel, description "
				+ "FROM Consumable INNER JOIN Item "
				+ "ON Consumable.consumableID = Item.itemID "
				+ "WHERE Consumable.consumableID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectConsumable);
			selectStmt.setInt(1, consumableID);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultItemName = results.getString("itemName");
				int resultMaxStackSize = results.getInt("maxStackSize");
				int resultVendorPrice = results.getInt("vendorPrice");
				int resultItemLevel = results.getInt("itemLevel");
				String resultDescription = results.getString("description");
				Consumable consumable = new Consumable(consumableID, resultItemName, resultMaxStackSize, resultVendorPrice, resultItemLevel, resultDescription);
				return consumable;
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
	
	public Consumable updateDescription(Consumable consumable, String newDescription) throws SQLException {
		String updateDescription = "UPDATE Consumable SET description=? WHERE consumableID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateDescription);
			updateStmt.setString(1, newDescription);
			updateStmt.setInt(2, consumable.getItemID());
			updateStmt.executeUpdate();

			consumable.setDescription(newDescription);
			return consumable;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
}
