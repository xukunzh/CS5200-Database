package dal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class EquipmentBonusDao {

    protected ConnectionManager connectionManager;

    // Singleton pattern: single instance.
    private static EquipmentBonusDao instance = null;

    protected EquipmentBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquipmentBonusDao getInstance() {
        if (instance == null) {
            instance = new EquipmentBonusDao();
        }
        return instance;
    }
 
    public EquipmentBonus create(EquipmentBonus equipmentBonus) throws SQLException {
        String insertEquipmentBonus =
            "INSERT INTO EquipmentBonus (gearID, attribute, amount) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquipmentBonus);
            insertStmt.setInt(1, equipmentBonus.getGear().getItemID());
            insertStmt.setString(2, equipmentBonus.getAttribute().getName());
            insertStmt.setInt(3, equipmentBonus.getAmount());
            insertStmt.executeUpdate();
            return equipmentBonus;
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

 
    public EquipmentBonus getEquipmentBonusByIDAndAttribute(int gearID, String attributeName) throws SQLException {
        String selectEquipmentBonus =
            "SELECT gearID, attribute, amount FROM EquipmentBonus WHERE gearID = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquipmentBonus);
            selectStmt.setInt(1, gearID);
            selectStmt.setString(2, attributeName);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int amount = results.getInt("amount");

                Equipment equipment = new Equipment(gearID); // Placeholder constructor
                Attribute attribute = new Attribute(attributeName);

                return new EquipmentBonus(amount, equipment, attribute);
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

  
    public EquipmentBonus delete(int gearID, String attributeName) throws SQLException {
        String deleteEquipmentBonus =
            "DELETE FROM EquipmentBonus WHERE gearID = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteEquipmentBonus);
            deleteStmt.setInt(1, gearID);
            deleteStmt.setString(2, attributeName);
            int affectedRows = deleteStmt.executeUpdate();

            if (affectedRows > 0) {
                Equipment equipment = new Equipment(gearID); // Placeholder constructor
                Attribute attribute = new Attribute(attributeName);
                return new EquipmentBonus(0, equipment, attribute); // Return dummy EquipmentBonus object.
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