package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Equipment;
import model.Item;

public class EquipmentDao extends ItemDao {
	
	private static EquipmentDao instance = null;
	
	protected EquipmentDao() {
		super();
	}
	
	public static EquipmentDao getInstance() {
		if (instance == null) {
			instance = new EquipmentDao();
		}
		return instance;
	}
	
	public Equipment create(Equipment equipment) throws SQLException {
		Item item = create(new Item(equipment.getItemName(), equipment.getMaxStackSize(), equipment.getVendorPrice(), equipment.getItemLevel()));
		
		String insertEquipment = "INSERT INTO Equipment(equipmentID, slotName, requiredLevel) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEquipment);
			insertStmt.setInt(1, item.getItemID());
			insertStmt.setString(2, equipment.getSlotName().name());
			insertStmt.setInt(3, equipment.getRequiredLevel());
			insertStmt.executeUpdate();
			
			equipment.setItemID(item.getItemID());
			return equipment;
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
	
	public Equipment getEquipmentByEquipmentID(int equipmentID) throws SQLException {
		String selectEquipment = "SELECT Equipment.equipmentID AS equipmentID, itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel "
				+ "FROM Equipment INNER JOIN Item "
				+ "ON Equipment.equipmentID = Item.itemID "
				+ "WHERE Equipment.equipmentID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEquipment);
			selectStmt.setInt(1, equipmentID);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultItemName = results.getString("itemName");
				int resultMaxStackSize = results.getInt("maxStackSize");
				int resultVendorPrice = results.getInt("vendorPrice");
				int resultItemLevel = results.getInt("itemLevel");
				Equipment.EquipmentSlotType resultSlotName = Equipment.EquipmentSlotType.valueOf(results.getString("slotName"));
				int resultRequiredLevel = results.getInt("requiredLevel");
				Equipment equipment = new Equipment(equipmentID, resultItemName, resultMaxStackSize, resultVendorPrice, resultItemLevel, resultSlotName, resultRequiredLevel);
				return equipment;
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
	
	public List<Equipment> getAllEquipmentBySlot(Equipment.EquipmentSlotType slotType) throws SQLException {
		List<Equipment> equipments = new ArrayList<Equipment>();
		
		String selectEquipments = "SELECT Equipment.equipmentID AS equipmentID, itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel "
				+ "FROM Equipment INNER JOIN Item "
				+ "ON Equipment.equipmentID = Item.itemID "
				+ "WHERE Equipment.slotName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEquipments);
			selectStmt.setString(1, slotType.name());
			results = selectStmt.executeQuery();
			while (results.next()) {
				int equipmentID = results.getInt("equipmentID");
				String resultItemName = results.getString("itemName");
				int resultMaxStackSize = results.getInt("maxStackSize");
				int resultVendorPrice = results.getInt("vendorPrice");
				int resultItemLevel = results.getInt("itemLevel");
				Equipment.EquipmentSlotType resultSlotName = Equipment.EquipmentSlotType.valueOf(results.getString("slotName"));
				int resultRequiredLevel = results.getInt("requiredLevel");
				Equipment equipment = new Equipment(equipmentID, resultItemName, resultMaxStackSize, resultVendorPrice, resultItemLevel, resultSlotName, resultRequiredLevel);
				equipments.add(equipment);
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
		return equipments;
		
	}
}
