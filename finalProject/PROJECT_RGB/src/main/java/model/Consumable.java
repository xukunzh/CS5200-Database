package model;

public class Consumable extends Item {
	
	protected String description;

	public Consumable(int itemID, String itemName, int maxStackSize, int vendorPrice, int itemLevel,
			String description) {
		super(itemID, itemName, maxStackSize, vendorPrice, itemLevel);
		this.description = description;
	}

	public Consumable(String itemName, int maxStackSize, int vendorPrice, int itemLevel, String description) {
		super(itemName, maxStackSize, vendorPrice, itemLevel);
		this.description = description;
	}

	public Consumable(int itemID) {
		super(itemID);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
