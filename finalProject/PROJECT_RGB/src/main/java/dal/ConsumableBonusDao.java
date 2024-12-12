package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class ConsumableBonusDao {

    protected ConnectionManager connectionManager;

    // Singleton pattern: instantiate one instance only.
    private static ConsumableBonusDao instance = null;

    protected ConsumableBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static ConsumableBonusDao getInstance() {
        if (instance == null) {
            instance = new ConsumableBonusDao();
        }
        return instance;
    }

    
    public ConsumableBonus create(ConsumableBonus consumableBonus) throws SQLException {
        String insertConsumableBonus = 
            "INSERT INTO ConsumableBonus (itemID, attribute, percentage, cap) VALUES (?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertConsumableBonus);
            insertStmt.setInt(1, consumableBonus.getItem().getItemID());
            insertStmt.setString(2, consumableBonus.getAttribute().getName());
            insertStmt.setDouble(3, consumableBonus.getPercentage());
            insertStmt.setInt(4, consumableBonus.getCap());
            insertStmt.executeUpdate();
            return consumableBonus;
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

     
    public ConsumableBonus getConsumableBonusByIDAndAttribute(int itemID, String attributeName) throws SQLException {
        String selectConsumableBonus = 
            "SELECT itemID, attribute, percentage, cap FROM ConsumableBonus WHERE itemID = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectConsumableBonus);
            selectStmt.setInt(1, itemID);
            selectStmt.setString(2, attributeName);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int cap = results.getInt("cap");
                double percentage = results.getDouble("percentage");

                Consumable consumable = new Consumable(itemID);
                Attribute attribute = new Attribute(attributeName);

                return new ConsumableBonus(cap, percentage, consumable, attribute);
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

   
    public ConsumableBonus delete(int itemID, String attributeName) throws SQLException {
        String deleteConsumableBonus = 
            "DELETE FROM ConsumableBonus WHERE itemID = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteConsumableBonus);
            deleteStmt.setInt(1, itemID);
            deleteStmt.setString(2, attributeName);
            int affectedRows = deleteStmt.executeUpdate();

            if (affectedRows > 0) {
                Consumable consumable = new Consumable(itemID);
                Attribute attribute = new Attribute(attributeName);
                return new ConsumableBonus(0, 0.0, consumable, attribute); // Return object with dummy values for cap and percentage.
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
