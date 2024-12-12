package model;

public class Job {
	protected String jobName;
	
	// Base EXP needed for each level, can be calculated as level * 100
    protected static final int BASE_EXP_PER_LEVEL = 100;

	public Job(String jobName) {
		super();
		this.jobName = jobName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	// Calculate required EXP for next level
    public static int getRequiredExpForLevel(int level) {
        return level * BASE_EXP_PER_LEVEL;
    }

}
