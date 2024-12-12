package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Equipment;
import model.Weapon;

public class WeaponDao extends EquipmentDao {
	
	private static WeaponDao instance = null;
	
	protected WeaponDao() {
		super();
	}
	
	public static WeaponDao getInstance() {
		if (instance == null) {
			instance = new WeaponDao();
		}
		return instance;
	}
	
	public Weapon create(Weapon weapon) throws SQLException {
		Equipment equipment = create(new Equipment(weapon.getItemName(), weapon.getMaxStackSize(), weapon.getVendorPrice(), weapon.getItemLevel(), weapon.getSlotName(), weapon.getRequiredLevel()));
		
		String insertWeapon = "INSERT INTO Weapon(weaponID, damageDone, autoAttack, attackDelay) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertWeapon);
			insertStmt.setInt(1, equipment.getItemID());
			insertStmt.setInt(2, weapon.getDamageDone());
			insertStmt.setInt(3, weapon.getAutoAttack());
			insertStmt.setInt(4, weapon.getAttackDelay());
			insertStmt.executeUpdate();
			
			weapon.setItemID(equipment.getItemID());
			return weapon;
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
	
	public Weapon getWeaponByWeaponID(int weaponID) throws SQLException {
		String selectGear = "SELECT Weapon.weaponID AS weaponID, itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel, damageDone, autoAttack, attackDelay "
				+ "FROM Weapon "
				+ "INNER JOIN Equipment ON Weapon.weaponID = Equipment.equipmentID "
				+ "INNER JOIN Item ON Equipment.equipmentID = Item.itemID "
				+ "WHERE Weapon.weaponID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGear);
			selectStmt.setInt(1, weaponID);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultItemName = results.getString("itemName");
				int resultMaxStackSize = results.getInt("maxStackSize");
				int resultVendorPrice = results.getInt("vendorPrice");
				int resultItemLevel = results.getInt("itemLevel");
				int resultRequiredLevel = results.getInt("requiredLevel");
				int resultDamageDone = results.getInt("damageDone");
				int resultAutoAttack = results.getInt("autoAttack");
				int resultAttackDelay = results.getInt("attackDelay");
				
				Weapon weapon = new Weapon(weaponID, resultItemName, resultMaxStackSize, resultVendorPrice, resultItemLevel, resultRequiredLevel, resultDamageDone, resultAutoAttack, resultAttackDelay);
				return weapon;
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
