package dal;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDao {
	protected ConnectionManager connectionManager;
	private static InventoryDao instance = null;
	
	protected InventoryDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static InventoryDao getInstance() {
		if (instance == null) {
			instance = new InventoryDao();
		}
		return instance;
	}
	
	public Inventory create(Inventory inventory) throws SQLException {
        String insertInventory = "INSERT INTO Inventory(`character`, locationIndex, item, quantity) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertInventory);
            insertStmt.setInt(1, inventory.getCharacter().getCharacterID());
            insertStmt.setInt(2, inventory.getLocationIndex());
            insertStmt.setInt(3, inventory.getItem().getItemID());
            insertStmt.setInt(4, inventory.getQuantity());

            insertStmt.executeUpdate();
            return inventory;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }
	
	public Inventory getInventoryByCharacterAndLocation(int characterID, int locationIndex) throws SQLException {
        String selectInventory = "SELECT `character`, locationIndex, item, quantity FROM Inventory WHERE `character` = ? AND locationIndex = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInventory);
            selectStmt.setInt(1, characterID);
            selectStmt.setInt(2, locationIndex);

            results = selectStmt.executeQuery();
            CharacterClassDao characterClassDao = CharacterClassDao.getInstance();
            ItemDao itemDao = ItemDao.getInstance();

            if (results.next()) {
                int resultCharacterID = results.getInt("character");
                int resultLocationIndex = results.getInt("locationIndex");
                int itemID = results.getInt("item");
                int quantity = results.getInt("quantity");

                CharacterClass characterClass = characterClassDao.getCharacterByID(resultCharacterID);
                Item item = itemDao.getItemByItemID(itemID);

                return new Inventory(characterClass, resultLocationIndex, item, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }
	
	public Inventory delete(Inventory inventory) throws SQLException {
        String deleteInventory = "DELETE FROM Inventory WHERE `character` = ? AND locationIndex = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteInventory);
            deleteStmt.setInt(1, inventory.getCharacter().getCharacterID());
            deleteStmt.setInt(2, inventory.getLocationIndex());

            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
