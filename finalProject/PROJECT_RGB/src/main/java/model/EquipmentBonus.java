package model;

public class EquipmentBonus {

 	protected int amount;
 	protected Equipment gear;
	protected Attribute attribute;
	public EquipmentBonus(int amount, Equipment gear, Attribute attribute) {
 
		this.amount = amount;
		this.gear = gear;
		this.attribute = attribute;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Equipment getGear() {
		return gear;
	}
	public void setGear(Equipment gear) {
		this.gear = gear;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
 
	
	
}