package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class CharacterJobDao {
	protected ConnectionManager connectionManager;

	private static CharacterJobDao instance = null;
	protected CharacterJobDao() {
		connectionManager = new ConnectionManager();
	}
	public static CharacterJobDao getInstance() {
		if(instance == null) {
			instance = new CharacterJobDao();
		}
		return instance;
	}
	
	public CharacterJob create(CharacterJob characterJob) throws SQLException {
		Connection connection = null;
		PreparedStatement checkStmt = null;
		String checkQuery = "SELECT COUNT(*) FROM CharacterJob WHERE `character` = ? AND job = ?";
	    try {
	    	connection = connectionManager.getConnection();
	    	checkStmt = connection.prepareStatement(checkQuery);
	        checkStmt.setInt(1, characterJob.getCharacterClass().getCharacterID());
	        checkStmt.setString(2, characterJob.getJob().getJobName());
	        ResultSet rs = checkStmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            throw new SQLException("CharacterJob with the same character and job already exists.");
	        }
	    } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	    
		String insertCharacterJob = "INSERT INTO CharacterJob (`character`,job,`level`,experiencePoints) VALUES(?,?,?,?);";
		
		PreparedStatement insertStmt = null;
		try {
			insertStmt = connection.prepareStatement(insertCharacterJob);

			insertStmt.setInt(1, characterJob.getCharacterClass().getCharacterID());
			insertStmt.setString(2, characterJob.getJob().getJobName());
			insertStmt.setInt(3, characterJob.getLevel());
			insertStmt.setInt(4, characterJob.getExperiencePoints());

			
			insertStmt.executeUpdate();
			
			return characterJob;
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
	
	public CharacterJob getBlogPostByCharacterAndJob(int characterID, String jobName) throws SQLException {
		String selectCharacterJob =
			"SELECT `character`,job,`level`,experiencePoints " +
			"FROM CharacterJob " +
			"WHERE `character`=? AND job=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterJob);
			selectStmt.setInt(1, characterID);
			selectStmt.setString(2,  jobName);
			results = selectStmt.executeQuery();
			CharacterClassDao characterClassDao = CharacterClassDao.getInstance();
			JobDao jobDao = JobDao.getInstance();
			if(results.next()) {
				int resultCharacterID = results.getInt("character");
				String resultJobName = results.getString("job");
				int level = results.getInt("level");
				int experiencePoints = results.getInt("experiencePoints");
				
				CharacterClass characterClass = characterClassDao.getCharacterByID(resultCharacterID);
				Job job = jobDao.getJobByJobName(resultJobName);
				CharacterJob characterJob = new CharacterJob(characterClass, job, level, experiencePoints);
				return characterJob;
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
	
    public List<CharacterJob> getCharacterJobsByCharacterId(int characterId) throws SQLException {
        List<CharacterJob> characterJobs = new ArrayList<>();
        String selectQuery = 
            "SELECT cj.job, cj.level, cj.experiencePoints " +
            "FROM CharacterJob cj " +
            "WHERE cj.character = ?;";
        
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();
            
            CharacterClassDao characterClassDao = CharacterClassDao.getInstance();
            JobDao jobDao = JobDao.getInstance();
            CharacterClass character = characterClassDao.getCharacterByID(characterId);
            
            while(results.next()) {
                String jobName = results.getString("job");
                int level = results.getInt("level");
                int exp = results.getInt("experiencePoints");
                
                Job job = jobDao.getJobByJobName(jobName);
                CharacterJob characterJob = new CharacterJob(character, job, level, exp);
                characterJobs.add(characterJob);
            }
            
            return characterJobs;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
    }
    
    // Method to update experience points
    public void updateExperience(CharacterClass character, String jobName, 
            int newExp, String reason) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStmt = null;
        
        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);  // Start transaction
            
            // First get current job info
            CharacterJob currentJob = getBlogPostByCharacterAndJob(
                character.getCharacterID(), jobName);
            
            if (currentJob == null) {
                throw new SQLException("Job not found for character");
            }
            
            // Update the experience points
            String updateQuery = 
                "UPDATE CharacterJob SET experiencePoints = ?, level = ? " +
                "WHERE `character` = ? AND job = ?;";
            
            updateStmt = connection.prepareStatement(updateQuery);
            
            int currentLevel = currentJob.getLevel();
            int newLevel = currentLevel;
            
            // Check if should level up
            while (newExp >= Job.getRequiredExpForLevel(newLevel + 1)) {
                newLevel++;
            }
            
            updateStmt.setInt(1, newExp);
            updateStmt.setInt(2, newLevel);
            updateStmt.setInt(3, character.getCharacterID());
            updateStmt.setString(4, jobName);
            
            updateStmt.executeUpdate();
            
            // Create history record
            JobLevelHistory history = new JobLevelHistory(
                character,
                currentJob.getJob(),
                currentLevel,
                newLevel,
                currentJob.getExperiencePoints(),
                newExp,
                newLevel > currentLevel ? "LEVEL_UP" : "EXP_GAIN",
                reason
            );
            
            JobLevelHistoryDao.getInstance().create(history);
            
            connection.commit();  // Commit transaction
            
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }
    
    // get all available jobs
    public List<Job> getAllAvailableJobs() throws SQLException {
        String selectQuery = "SELECT jobName FROM Job ORDER BY jobName;";
        List<Job> jobs = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectQuery);
            results = selectStmt.executeQuery();
            
            while(results.next()) {
                String jobName = results.getString("jobName");
                jobs.add(new Job(jobName));
            }
            return jobs;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
    }
}
