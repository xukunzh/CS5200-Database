package model;

import java.sql.Timestamp;

public class MoneyTransaction {
    private int transactionId;
    private CharacterClass character;
    private Currency currency;
    private int amount;          // Amount changed (positive for increase, negative for decrease)
    private int previousAmount;
    private int newAmount;
    private String transactionType;
    private String reason;
    private Timestamp transactionTime;
    
    // Constructor for creating new transaction
    public MoneyTransaction(CharacterClass character, Currency currency, 
            int amount, int previousAmount, int newAmount, String transactionType, 
            String reason) {
        this.character = character;
        this.currency = currency;
        this.amount = amount;
        this.previousAmount = previousAmount;
        this.newAmount = newAmount;
        this.transactionType = transactionType;
        this.reason = reason;
    }
    
    // Constructor for retrieving from database
    public MoneyTransaction(int transactionId, CharacterClass character, Currency currency, 
            int amount, int previousAmount, int newAmount, String transactionType, 
            String reason, Timestamp transactionTime) {
        this(character, currency, amount, previousAmount, newAmount, transactionType, reason);
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
    }
    
    // Getters and setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    
    public CharacterClass getCharacter() { return character; }
    public void setCharacter(CharacterClass character) { this.character = character; }
    
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
    
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
    public int getPreviousAmount() { return previousAmount; }
    public void setPreviousAmount(int previousAmount) { this.previousAmount = previousAmount; }
    
    public int getNewAmount() { return newAmount; }
    public void setNewAmount(int newAmount) { this.newAmount = newAmount; }
    
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public Timestamp getTransactionTime() { return transactionTime; }
    public void setTransactionTime(Timestamp transactionTime) { this.transactionTime = transactionTime; }
}