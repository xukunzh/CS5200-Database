package model;

public class CharacterJob {
	protected CharacterClass characterClass;
	protected Job job;
	protected int level;
	protected int experiencePoints;
	
	public CharacterJob(CharacterClass characterClass, Job job, int level, int experiencePoints) {
		super();
		this.characterClass = characterClass;
		this.job = job;
		this.level = level;
		this.experiencePoints = experiencePoints;
	}
	public CharacterClass getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExperiencePoints() {
		return experiencePoints;
	}
	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}
	
	// New methods for job progression
	public int getNextLevelExp() {
	    return Job.getRequiredExpForLevel(level); 
	}
    
	public int getExpToNextLevel() {
	    return getNextLevelExp() - experiencePoints;
	}
    
	public int getExpPercentage() {
	    int expToNextLevel = getNextLevelExp() - experiencePoints;
	    return expToNextLevel; 
	}

    public boolean canLevelUp() {
        return experiencePoints >= getNextLevelExp();
    }
    
    public void addExperience(int amount) {
        this.experiencePoints += amount;
    }

}
