package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class CharacterClassDao {
	protected ConnectionManager connectionManager;
	private static CharacterClassDao instance = null;
	protected CharacterClassDao() {
		connectionManager = new ConnectionManager();
	}
	public static CharacterClassDao getInstance() {
		if(instance == null) {
			instance = new CharacterClassDao();
		}
		return instance;
	}
	
	public CharacterClass create(CharacterClass characterClass) throws SQLException {
		Connection connection = null;
		PreparedStatement checkStmt = null;
		String checkQuery = "SELECT COUNT(*) FROM `Character` WHERE firstName = ? AND lastName = ?";
	    try {
	    	connection = connectionManager.getConnection();
	    	checkStmt = connection.prepareStatement(checkQuery);
	        checkStmt.setString(1, characterClass.getFirstName());
	        checkStmt.setString(2, characterClass.getLastName());
	        ResultSet rs = checkStmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            throw new SQLException("Character with the same first and last name already exists.");
	        }
	    } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	    
		String insertCharacter = "INSERT INTO `Character` (player,firstName,lastName) VALUES(?,?,?);";
		
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			
			insertStmt = connection.prepareStatement(insertCharacter, Statement.RETURN_GENERATED_KEYS);

			insertStmt.setString(1, characterClass.getPlayerAccount().getEmail());
			insertStmt.setString(2, characterClass.getFirstName());
			insertStmt.setString(3, characterClass.getLastName());

			
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int characterID = -1;
			if(resultKey.next()) {
				characterID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			characterClass.setCharacterID(characterID);
			
			return characterClass;
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
	
	public CharacterClass getCharacterByID(int characterID) throws SQLException {
		String selectCharacter =
			"SELECT characterID,player,firstName,lastName " +
			"FROM `Character` " +
			"WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacter);
			selectStmt.setInt(1, characterID);
			results = selectStmt.executeQuery();
			PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
			if(results.next()) {
				int resultCharacterID = results.getInt("characterID");
				String playerAccountEmail = results.getString("player");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				PlayerAccount playerAccount = playerAccountDao.getPlayerAccountByEmail(playerAccountEmail);
				CharacterClass characterClass = new CharacterClass(resultCharacterID, playerAccount, firstName, lastName);
				
				return characterClass;
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
	
	public List<CharacterClass> getCharactersByFirstName(String firstName) throws SQLException {
		List<CharacterClass> characters = new ArrayList<CharacterClass>();
		String selectCharacter =
				"SELECT characterID,player,firstName,lastName " +
				"FROM `Character` " +
				"WHERE firstName LIKE ? " + 
				"ORDER BY lastName ASC;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacter);
			selectStmt.setString(1, "%" + firstName + "%");
			results = selectStmt.executeQuery();
			PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
			while(results.next()) {
				int resultCharacterID = results.getInt("characterID");
				String playerAccountEmail = results.getString("player");
				String originalFirstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				PlayerAccount playerAccount = playerAccountDao.getPlayerAccountByEmail(playerAccountEmail);
				CharacterClass characterClass = new CharacterClass(resultCharacterID, playerAccount, originalFirstName, lastName);
				
				characters.add(characterClass);
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
		return characters;
	}
	
	//  CharacterClassDao to fetch character by full name
	public CharacterClass getCharacterByFullName(String fullName) throws SQLException {
		if (fullName == null || fullName.trim().isEmpty()) {
	        throw new SQLException("Full name cannot be null or empty.");
	    }

	    String[] nameParts = fullName.split(" ");
	    if (nameParts.length != 2) {
	        throw new SQLException("Full name should consist of exactly two parts (first name and last name).");
	    }
	    
	    String selectCharacter =
	        "SELECT characterID,player,firstName,lastName " +
	        "FROM `Character` " +
	        "WHERE firstName = ? AND lastName = ?;";
	        
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectCharacter);
	        selectStmt.setString(1, nameParts[0]);
	        selectStmt.setString(2, nameParts[1]);
	        results = selectStmt.executeQuery();
	        
	        PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
	        if (results.next()) {
	            int resultCharacterID = results.getInt("characterID");
	            String playerAccountEmail = results.getString("player");
	            String firstName = results.getString("firstName");
	            String lastName = results.getString("lastName");
	            
	            PlayerAccount playerAccount = playerAccountDao.getPlayerAccountByEmail(playerAccountEmail);
	            CharacterClass characterClass = new CharacterClass(resultCharacterID, playerAccount, firstName, lastName);
	            
	            return characterClass;
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
	
	public List<CharacterClass> getAllCharacters() throws SQLException {
	    List<CharacterClass> characters = new ArrayList<CharacterClass>();
	    String selectCharacter =
	        "SELECT characterID,player,firstName,lastName " +
	        "FROM `Character` " +
	        "ORDER BY lastName ASC;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectCharacter);
	        results = selectStmt.executeQuery();
	        PlayerAccountDao playerAccountDao = PlayerAccountDao.getInstance();
	        while(results.next()) {
	            int resultCharacterID = results.getInt("characterID");
	            String playerAccountEmail = results.getString("player");
	            String firstName = results.getString("firstName");
	            String lastName = results.getString("lastName");
	            
	            PlayerAccount playerAccount = playerAccountDao.getPlayerAccountByEmail(playerAccountEmail);
	            CharacterClass characterClass = new CharacterClass(resultCharacterID, playerAccount, firstName, lastName);
	            
	            characters.add(characterClass);
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
	    return characters;
	}
	
	/**
	 * Update the LastName of the CharacterClass instance.
	 * This runs a UPDATE statement.
	 */
	public CharacterClass updateLastName(CharacterClass character, String newLastName) throws SQLException {
		String updateCharacterClass = "UPDATE `Character` SET LastName=? WHERE FirstName=? AND LastName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCharacterClass);
			updateStmt.setString(1, newLastName);
			updateStmt.setString(2, character.getFirstName());
			updateStmt.setString(3, character.getLastName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			character.setLastName(newLastName);
			return character;
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
	
	/**
	 * Delete the Character instance.
	 * This runs a DELETE statement.
	 */
	public CharacterClass delete(CharacterClass character) throws SQLException {
		String deleteCharacter = "DELETE FROM `Character` WHERE FirstName=? AND LastName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCharacter);
			deleteStmt.setString(1, character.getFirstName());
			deleteStmt.setString(2, character.getLastName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
