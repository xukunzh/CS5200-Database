package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class MoneyTransactionDao {
    protected ConnectionManager connectionManager;
    private static MoneyTransactionDao instance = null;
    
    protected MoneyTransactionDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static MoneyTransactionDao getInstance() {
        if (instance == null) {
            instance = new MoneyTransactionDao();
        }
        return instance;
    }
    
    public MoneyTransaction create(MoneyTransaction transaction) throws SQLException {
        String insertTransaction = 
            "INSERT INTO MoneyTransaction(`character`,currency,amount,previousAmount,newAmount," +
            "transactionType,reason) VALUES(?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertTransaction, 
                Statement.RETURN_GENERATED_KEYS);
            
            insertStmt.setInt(1, transaction.getCharacter().getCharacterID());
            insertStmt.setString(2, transaction.getCurrency().getName());
            insertStmt.setInt(3, transaction.getAmount());
            insertStmt.setInt(4, transaction.getPreviousAmount());
            insertStmt.setInt(5, transaction.getNewAmount());
            insertStmt.setString(6, transaction.getTransactionType());
            insertStmt.setString(7, transaction.getReason());
            
            insertStmt.executeUpdate();
            
            resultKey = insertStmt.getGeneratedKeys();
            int transactionId = -1;
            if (resultKey.next()) {
                transactionId = resultKey.getInt(1);
            }
            transaction.setTransactionId(transactionId);
            
            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }
    
    public List<MoneyTransaction> getTransactionsByCharacter(CharacterClass character) 
            throws SQLException {
        List<MoneyTransaction> transactions = new ArrayList<>();
        String selectTransactions = 
            "SELECT transactionId,currency,amount,previousAmount,newAmount," +
            "transactionType,reason,transactionTime " +
            "FROM MoneyTransaction WHERE `character`=? " +
            "ORDER BY transactionTime DESC;";
        
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTransactions);
            selectStmt.setInt(1, character.getCharacterID());
            results = selectStmt.executeQuery();
            
            while (results.next()) {
                int transactionId = results.getInt("transactionId");
                String currencyName = results.getString("currency");
                Currency currency = new Currency(currencyName);
                int amount = results.getInt("amount");
                int previousAmount = results.getInt("previousAmount");
                int newAmount = results.getInt("newAmount");
                String transactionType = results.getString("transactionType");
                String reason = results.getString("reason");
                Timestamp transactionTime = results.getTimestamp("transactionTime");
                
                MoneyTransaction transaction = new MoneyTransaction(transactionId, 
                    character, currency, amount, previousAmount, newAmount,
                    transactionType, reason, transactionTime);
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
    }
}