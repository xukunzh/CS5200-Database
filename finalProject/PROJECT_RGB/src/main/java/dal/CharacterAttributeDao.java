package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class CharacterAttributeDao {

    protected ConnectionManager connectionManager;

    // Singleton pattern: instantiation is limited to one object.
    private static CharacterAttributeDao instance = null;

    protected CharacterAttributeDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterAttributeDao getInstance() {
        if (instance == null) {
            instance = new CharacterAttributeDao();
        }
        return instance;
    }
 
    public CharacterAttributes create(CharacterAttributes characterAttribute) throws SQLException {
        String insertCharacterAttribute = 
            "INSERT INTO CharacterAttributes(`character`, attribute, value) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterAttribute);
            insertStmt.setInt(1, characterAttribute.getCharacter().getCharacterID());
            insertStmt.setString(2, characterAttribute.getAttribute().getName());
            insertStmt.setString(3, characterAttribute.getValue());
            insertStmt.executeUpdate();
            return characterAttribute;
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

 
    public CharacterAttributes getCharacterAttributeByCharacterIDAndAttribute(int characterID, String attributeName) throws SQLException {
        String selectCharacterAttribute = 
            "SELECT `character`, attribute, value FROM CharacterAttributes WHERE `character` = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterAttribute);
            selectStmt.setInt(1, characterID);
            selectStmt.setString(2, attributeName);
            results = selectStmt.executeQuery();

            if (results.next()) {
            	int resultCharacterID = results.getInt("character");
                
                String resultAttributeName = results.getString("attribute");
                String value = results.getString("value");
                // Fetch PlayerAccount
                CharacterClassDao characterDao = CharacterClassDao.getInstance();
                CharacterClass character = characterDao.getCharacterByID(resultCharacterID);
                
                Attribute attribute = new Attribute(resultAttributeName);

                return new CharacterAttributes(character, attribute, value);
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
    
    public List<CharacterAttributes> getCharacterAttributeByCharacterID(int characterID) throws SQLException {
        String selectCharacterAttributes = 
            "SELECT `character`, attribute, value FROM CharacterAttributes WHERE `character` = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        List<CharacterAttributes> attributesList = new ArrayList<>();

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterAttributes);
            selectStmt.setInt(1, characterID);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int resultCharacterID = results.getInt("character");
                String resultAttributeName = results.getString("attribute");
                String value = results.getString("value");

                CharacterClassDao characterDao = CharacterClassDao.getInstance();
                CharacterClass character = characterDao.getCharacterByID(resultCharacterID);
                
                Attribute attribute = new Attribute(resultAttributeName);

                CharacterAttributes characterAttribute = new CharacterAttributes(character, attribute, value);
                attributesList.add(characterAttribute);
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
        return attributesList;
    }


 
    public CharacterAttributes delete(int characterID, String attributeName) throws SQLException {
        String deleteCharacterAttribute = 
            "DELETE FROM CharacterAttributes WHERE `character` = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacterAttribute);
            deleteStmt.setInt(1, characterID);
            deleteStmt.setString(2, attributeName);
            deleteStmt.executeUpdate();
            return null;
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
        
    }
}
