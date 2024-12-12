package model;

public class ConsumableBonus {

	protected int cap; 
    protected double percentage;  
    protected Consumable consumable;  
    protected Attribute attribute;  

    // Constructor
    public ConsumableBonus(int cap, double percentage, Consumable consumable, Attribute attribute) {
     
        this.cap = cap;
        this.percentage = percentage;
        this.consumable = consumable;
        this.attribute = attribute;
    }

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Consumable getItem() {
		return consumable;
	}

	public void setItem(Consumable item) {
		this.consumable = item;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
    

}