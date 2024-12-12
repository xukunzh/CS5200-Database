package model;

public class Money {
	
	protected CharacterClass character;
	protected Currency currency;
	protected int totalAmount;
	protected int weeklyAmount;
	public Money(CharacterClass character, Currency currency, int totalAmount, int weeklyAmount) {
		this.character = character;
		this.currency = currency;
		this.totalAmount = totalAmount;
		this.weeklyAmount = weeklyAmount;
	}
	public Money(CharacterClass character, Currency currency) {
		this.character = character;
		this.currency = currency;
	}
	public CharacterClass getCharacter() {
		return character;
	}
	public void setCharacter(CharacterClass character) {
		this.character = character;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getWeeklyAmount() {
		return weeklyAmount;
	}
	public void setWeeklyAmount(int weeklyAmount) {
		this.weeklyAmount = weeklyAmount;
	}
	
	

}
