package model;

public class CharacterClass {
	protected int characterID;
	protected PlayerAccount playerAccount;
	protected String firstName;
	protected String lastName;
	
	public CharacterClass(PlayerAccount playerAccount, String firstName, String lastName) {
		super();
		this.playerAccount = playerAccount;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	

	public CharacterClass(int characterID, PlayerAccount playerAccount, String firstName, String lastName) {
		super();
		this.characterID = characterID;
		this.playerAccount = playerAccount;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	public int getCharacterID() {
		return characterID;
	}

	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}

	public PlayerAccount getPlayerAccount() {
		return playerAccount;
	}

	public void setPlayerAccount(PlayerAccount playerAccount) {
		this.playerAccount = playerAccount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
