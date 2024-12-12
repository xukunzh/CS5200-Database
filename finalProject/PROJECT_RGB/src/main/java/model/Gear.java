package model;

public class Gear extends Equipment {
	
	protected int defenseRating;
	protected int magicDefenseRating;
	
	public Gear(int itemID, String itemName, int maxStackSize, int vendorPrice, int itemLevel,
			EquipmentSlotType slotName, int requiredLevel, int defenseRating, int magicDefenseRating) {
		super(itemID, itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel);
		this.defenseRating = defenseRating;
		this.magicDefenseRating = magicDefenseRating;
	}

	public Gear(String itemName, int maxStackSize, int vendorPrice, int itemLevel, EquipmentSlotType slotName,
			int requiredLevel, int defenseRating, int magicDefenseRating) {
		super(itemName, maxStackSize, vendorPrice, itemLevel, slotName, requiredLevel);
		this.defenseRating = defenseRating;
		this.magicDefenseRating = magicDefenseRating;
	}

	public Gear(int itemID) {
		super(itemID);
	}

	public int getDefenseRating() {
		return defenseRating;
	}

	public void setDefenseRating(int defenseRating) {
		this.defenseRating = defenseRating;
	}

	public int getMagicDefenseRating() {
		return magicDefenseRating;
	}

	public void setMagicDefenseRating(int magicDefenseRating) {
		this.magicDefenseRating = magicDefenseRating;
	}
}
