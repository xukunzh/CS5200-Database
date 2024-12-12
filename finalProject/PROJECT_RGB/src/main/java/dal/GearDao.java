package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Equipment;
import model.Gear;

public class GearDao extends EquipmentDao {
	
	private static GearDao instance = null;
	
	protected GearDao() {
		super();
	}
	
	public static GearDao getInstance() {
		if (instance == null) {
			instance = new GearDao();
		}
		return instance;
	}
	
	public Gear create(Gear gear) throws SQLException {
		Equipment equipment = create(new Equipment(gear.getItemName(), gear.getMaxStackSize(), gear.getVendorPrice(), gear.getItemLevel(), gear.getSlotName(), gear.getRequiredLevel()));
		
		String insertGear = "INSERT INTO Gear(gearID, defenseRating, magicDefenseRating) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGear);
			insertStmt.setInt(1, equipment.getItemID());
			insertStmt.setInt(2, gear.getDefenseRating());
			insertStmt.setInt(3, gear.getMagicDefenseRating());
			insertStmt.executeUpdate();
			
			gear.setItemID(equipment.getItemID());
			return gear;
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
	
	public Gear getGearByGearID(int gearID) throws SQLException {
		String selectGear = "SELECT Gear.gearID AS gearID, itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel, defenseRating, magicDefenseRating "
				+ "FROM Gear "
				+ "INNER JOIN Equipment ON Gear.gearID = Equipment.equipmentID "
				+ "INNER JOIN Item ON Equipment.equipmentID = Item.itemID "
				+ "WHERE Gear.gearID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGear);
			selectStmt.setInt(1, gearID);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultItemName = results.getString("itemName");
				int resultMaxStackSize = results.getInt("maxStackSize");
				int resultVendorPrice = results.getInt("vendorPrice");
				int resultItemLevel = results.getInt("itemLevel");
				Equipment.EquipmentSlotType resultSlotName = Equipment.EquipmentSlotType.valueOf(results.getString("slotName"));
				int resultRequiredLevel = results.getInt("requiredLevel");
				int resultDefenseRating = results.getInt("defenseRating");
				int resultMagicDefenseRating = results.getInt("magicDefenseRating");
				Gear gear = new Gear(gearID, resultItemName, resultMaxStackSize, resultVendorPrice, resultItemLevel, resultSlotName, resultRequiredLevel, resultDefenseRating, resultMagicDefenseRating);
				return gear;
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
