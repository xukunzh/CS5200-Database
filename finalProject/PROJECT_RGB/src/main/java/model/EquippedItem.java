package model;

public class EquippedItem {
	public enum EquippedItemSlot {
		main_hand, head, off_hand, body, earring, hands, wrist, legs, ring, feet,
	}
	protected CharacterClass character;
	protected EquippedItemSlot slotName;
	protected Equipment equipment;
	
	public EquippedItem(CharacterClass character, EquippedItemSlot slotName, Equipment equipment) {
		super();
		this.character = character;
		this.slotName = slotName;
		this.equipment = equipment;
	}

	public CharacterClass getCharacter() {
		return character;
	}

	public void setCharacter(CharacterClass character) {
		this.character = character;
	}

	public EquippedItemSlot getSlotName() {
		return slotName;
	}

	public void setSlotName(EquippedItemSlot slotName) {
		this.slotName = slotName;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	
	
}
