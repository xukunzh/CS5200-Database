package model;

import java.sql.Timestamp;

public class JobLevelHistory {
    private int historyId;
    private CharacterClass character;
    private Job job;
    private Integer oldLevel;
    private int newLevel;
    private Integer oldExp;
    private int newExp;
    private Timestamp changeTime;
    private String changeType;
    private String reason;
    
    public JobLevelHistory(CharacterClass character, Job job, Integer oldLevel, int newLevel, 
            Integer oldExp, int newExp, String changeType, String reason) {
        this.character = character;
        this.job = job;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
        this.oldExp = oldExp;
        this.newExp = newExp;
        this.changeType = changeType;
        this.reason = reason;
    }
    
    // Add getters and setters
    public int getHistoryId() { return historyId; }
    public void setHistoryId(int historyId) { this.historyId = historyId; }
    
    public CharacterClass getCharacter() { return character; }
    public void setCharacter(CharacterClass character) { this.character = character; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    
    public Integer getOldLevel() { return oldLevel; }
    public void setOldLevel(Integer oldLevel) { this.oldLevel = oldLevel; }
    
    public int getNewLevel() { return newLevel; }
    public void setNewLevel(int newLevel) { this.newLevel = newLevel; }
    
    public Integer getOldExp() { return oldExp; }
    public void setOldExp(Integer oldExp) { this.oldExp = oldExp; }
    
    public int getNewExp() { return newExp; }
    public void setNewExp(int newExp) { this.newExp = newExp; }
    
    public Timestamp getChangeTime() { return changeTime; }
    public void setChangeTime(Timestamp changeTime) { this.changeTime = changeTime; }
    
    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}