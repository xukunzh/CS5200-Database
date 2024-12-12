package model;

public class CharacterAttributes {

 	protected CharacterClass character;
	protected Attribute attribute;
	protected String value;
	public CharacterAttributes(CharacterClass character, Attribute attribute, String value) {
		super();
		this.character = character;
		this.attribute = attribute;
		this.value = value;
	}
	public CharacterClass getCharacter() {
		return character;
	}
	public void setCharacter(CharacterClass character) {
		this.character = character;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}