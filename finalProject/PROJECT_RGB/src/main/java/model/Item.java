package model;

public class Item {
	protected int itemID;
	protected String itemName;
	protected int maxStackSize;
	protected int vendorPrice;
	protected int itemLevel;
	
	public Item(int itemID, String itemName, int maxStackSize, int vendorPrice, int itemLevel) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.maxStackSize = maxStackSize;
		this.vendorPrice = vendorPrice;
		this.itemLevel = itemLevel;
	}

	public Item(String itemName, int maxStackSize, int vendorPrice, int itemLevel) {
		this.itemName = itemName;
		this.maxStackSize = maxStackSize;
		this.vendorPrice = vendorPrice;
		this.itemLevel = itemLevel;
	}

	public Item(int itemID) {
		this.itemID = itemID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}

	public int getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(int vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}
}
