package model;

public class Equipment extends Item {
	
	public enum EquipmentSlotType {
		main_hand, head, off_hand, body, earring, hands, wrist, legs, ring, feet,
	}
	
	protected EquipmentSlotType slotName;
	protected int requiredLevel;
	
	public Equipment(int itemID, String itemName, int maxStackSize, int vendorPrice, int itemLevel,
			EquipmentSlotType slotName, int requiredLevel) {
		super(itemID, itemName, maxStackSize, vendorPrice, itemLevel);
		this.slotName = slotName;
		this.requiredLevel = requiredLevel;
	}

	public Equipment(String itemName, int maxStackSize, int vendorPrice, int itemLevel, EquipmentSlotType slotName,
			int requiredLevel) {
		super(itemName, maxStackSize, vendorPrice, itemLevel);
		this.slotName = slotName;
		this.requiredLevel = requiredLevel;
	}

	public Equipment(int itemID) {
		super(itemID);
	}
	
	public int getItemID() {
		return itemID;
	}

	public EquipmentSlotType getSlotName() {
		return slotName;
	}

	public void setSlotName(EquipmentSlotType slotName) {
		this.slotName = slotName;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
}
