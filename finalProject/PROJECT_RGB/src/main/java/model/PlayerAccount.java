package model;

public class PlayerAccount {
	protected String email;
	protected String name;
	protected Boolean activeStatus;
	
	public PlayerAccount(String email, String name, Boolean activeStatus) {
		super();
		this.email = email;
		this.name = name;
		this.activeStatus = activeStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
}
