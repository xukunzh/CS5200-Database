package model;

public class Currency {
	
	protected String name;
	protected int cap;
	protected int weeklyCap;
	
	public Currency(String name, int cap, int weeklyCap) {
		this.name = name;
		this.cap = cap;
		this.weeklyCap = weeklyCap;
	}
	
	public Currency(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCap() {
		return cap;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	public int getWeeklyCap() {
		return weeklyCap;
	}
	public void setWeeklyCap(int weeklyCap) {
		this.weeklyCap = weeklyCap;
	}
	
	
}
