package model;

public class Inventory {
	protected CharacterClass character;
	protected int locationIndex;
	protected Item item;
	protected int quantity;
	
	public Inventory(CharacterClass character, int locationIndex, Item item, int quantity) {
		super();
		this.character = character;
		this.locationIndex = locationIndex;
		this.item = item;
		this.quantity = quantity;
	}

	public CharacterClass getCharacter() {
		return character;
	}

	public void setCharacter(CharacterClass character) {
		this.character = character;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
