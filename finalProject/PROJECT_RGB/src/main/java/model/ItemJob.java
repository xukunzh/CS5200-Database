package model;

public class ItemJob {
	
	protected Item item;
	protected Job job;
	
	public ItemJob(Item item, Job job) {
		this.item = item;
		this.job = job;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}

	
}
