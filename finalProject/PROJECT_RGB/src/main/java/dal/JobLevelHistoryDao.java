package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class JobLevelHistoryDao {
    protected ConnectionManager connectionManager;
    private static JobLevelHistoryDao instance = null;
    
    protected JobLevelHistoryDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static JobLevelHistoryDao getInstance() {
        if (instance == null) {
            instance = new JobLevelHistoryDao();
        }
        return instance;
    }
    
    public JobLevelHistory create(JobLevelHistory history) throws SQLException {
        String insertHistory = 
            "INSERT INTO JobLevelHistory (`character`, job, oldLevel, newLevel, " +
            "oldExp, newExp, changeType, reason) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertHistory, 
                Statement.RETURN_GENERATED_KEYS);
            
            insertStmt.setInt(1, history.getCharacter().getCharacterID());
            insertStmt.setString(2, history.getJob().getJobName());
            insertStmt.setObject(3, history.getOldLevel());  // Can be null
            insertStmt.setInt(4, history.getNewLevel());
            insertStmt.setObject(5, history.getOldExp());    // Can be null
            insertStmt.setInt(6, history.getNewExp());
            insertStmt.setString(7, history.getChangeType());
            insertStmt.setString(8, history.getReason());
            
            insertStmt.executeUpdate();
            
            resultKey = insertStmt.getGeneratedKeys();
            int historyId = -1;
            if (resultKey.next()) {
                historyId = resultKey.getInt(1);
            }
            history.setHistoryId(historyId);
            
            return history;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }
    
    public List<JobLevelHistory> getHistoryForCharacter(CharacterClass character) throws SQLException {
        List<JobLevelHistory> historyList = new ArrayList<>();
        String selectHistory = 
            "SELECT historyId, job, oldLevel, newLevel, oldExp, newExp, " +
            "changeTime, changeType, reason " +
            "FROM JobLevelHistory WHERE `character` = ? " +
            "ORDER BY changeTime DESC;";
        
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectHistory);
            selectStmt.setInt(1, character.getCharacterID());
            results = selectStmt.executeQuery();
            
            JobDao jobDao = JobDao.getInstance();
            
            while (results.next()) {
                String jobName = results.getString("job");
                Job job = jobDao.getJobByJobName(jobName);
                
                JobLevelHistory history = new JobLevelHistory(
                    character,
                    job,
                    (Integer) results.getObject("oldLevel"),
                    results.getInt("newLevel"),
                    (Integer) results.getObject("oldExp"),
                    results.getInt("newExp"),
                    results.getString("changeType"),
                    results.getString("reason")
                );
                
                history.setHistoryId(results.getInt("historyId"));
                history.setChangeTime(results.getTimestamp("changeTime"));
                
                historyList.add(history);
            }
            
            return historyList;
            
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