package model;

public class Weapon extends Equipment {
	
	protected int damageDone;
	protected int autoAttack;
	protected int attackDelay;
	
	public Weapon(int itemID, String itemName, int maxStackSize, int vendorPrice, int itemLevel, int requiredLevel, int damageDone, int autoAttack, int attackDelay) {
		super(itemID, itemName, maxStackSize, vendorPrice, itemLevel, Equipment.EquipmentSlotType.main_hand, requiredLevel);
		this.damageDone = damageDone;
		this.autoAttack = autoAttack;
		this.attackDelay = attackDelay;
	}

	public Weapon(String itemName, int maxStackSize, int vendorPrice, int itemLevel, int requiredLevel, int damageDone, int autoAttack, int attackDelay) {
		super(itemName, maxStackSize, vendorPrice, itemLevel, Equipment.EquipmentSlotType.main_hand, requiredLevel);
		this.damageDone = damageDone;
		this.autoAttack = autoAttack;
		this.attackDelay = attackDelay;
	}

	public Weapon(int itemID) {
		super(itemID);
		this.slotName = EquipmentSlotType.main_hand;
	}

	public int getDamageDone() {
		return damageDone;
	}

	public void setDamageDone(int damageDone) {
		this.damageDone = damageDone;
	}

	public int getAutoAttack() {
		return autoAttack;
	}

	public void setAutoAttack(int autoAttack) {
		this.autoAttack = autoAttack;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}
}
