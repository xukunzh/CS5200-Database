package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

public class EquippedItemDao {
	protected ConnectionManager connectionManager;
	private static EquippedItemDao instance = null;
	
	protected EquippedItemDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static EquippedItemDao getInstance() {
		if (instance == null) {
			instance = new EquippedItemDao();
		}
		return instance;
	}
	
	public EquippedItem create(EquippedItem equippedItem) throws SQLException {
        String insertEquippedItem = "INSERT INTO EquippedItem(`character`, slotName, itemID) VALUES(?, ?, ?);";
        String checkItemIDQuery = "SELECT itemID FROM EquippedItem WHERE itemID = ?;";
        Connection connection = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            
            checkStmt = connection.prepareStatement(checkItemIDQuery);
            checkStmt.setInt(1, equippedItem.getEquipment().getItemID());
            results = checkStmt.executeQuery();

            if (results.next()) {
                throw new SQLException("Item with this itemID is already equipped.");
            }
            
            insertStmt = connection.prepareStatement(insertEquippedItem);
            insertStmt.setInt(1, equippedItem.getCharacter().getCharacterID());
            insertStmt.setString(2, equippedItem.getSlotName().name());
            insertStmt.setInt(3, equippedItem.getEquipment().getItemID());
            
            insertStmt.executeUpdate();
            return equippedItem;
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
	
	public EquippedItem getEquippedItemByCharacterAndSlot(int characterID, EquippedItem.EquippedItemSlot slotName) throws SQLException {
        String selectEquippedItem = 
            "SELECT `character`, slotName, itemID " +
            "FROM EquippedItem " +
            "WHERE `character` = ? AND slotName = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquippedItem);
            selectStmt.setInt(1, characterID);
            selectStmt.setString(2, slotName.name());
            
            results = selectStmt.executeQuery();
            CharacterClassDao characterClassDao = CharacterClassDao.getInstance();
            EquipmentDao equipmentDao = EquipmentDao.getInstance();
            
            if (results.next()) {
                int resultCharacterID = results.getInt("character");
                String resultSlotName = results.getString("slotName");
                int itemID = results.getInt("itemID");

                CharacterClass characterClass = characterClassDao.getCharacterByID(resultCharacterID);
                Equipment equipment = equipmentDao.getEquipmentByEquipmentID(itemID);
                
                EquippedItem equippedItem = new EquippedItem(characterClass, EquippedItem.EquippedItemSlot.valueOf(resultSlotName), equipment);
                return equippedItem;
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
	
	
}
