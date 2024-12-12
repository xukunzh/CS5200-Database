package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;


public class MoneyDao {
	protected ConnectionManager connectionManager;
	private static MoneyDao instance = null;
	
	protected MoneyDao() {
		this.connectionManager = new ConnectionManager();
	}
	
	public static MoneyDao getInstance() {
		if (instance == null) {
			instance = new MoneyDao();
		}
		return instance;
	}
	
	public Money create(Money money) throws SQLException {
		String insertMoney =
				"INSERT INTO Money(Character,Currency,TotalAmount,WeeklyAmount) VALUES(?,?,?,?);";
			Connection connection = null;
			PreparedStatement insertStmt = null;
			try {
				connection = connectionManager.getConnection();
				insertStmt = connection.prepareStatement(insertMoney);
				insertStmt.setInt(1, money.getCharacter().getCharacterID());
				insertStmt.setString(2, money.getCurrency().getName());
				insertStmt.setInt(3, money.getTotalAmount());
				insertStmt.setInt(4, money.getWeeklyAmount());
				insertStmt.executeUpdate();
				
				return money;
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(insertStmt != null) {
					insertStmt.close();
				}
			}
	}
	
	public Money delete(Money money) throws SQLException {
		String deleteMoney = "DELETE FROM Money WHERE Character=? AND Currency =?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMoney);
			deleteStmt.setInt(1, money.getCharacter().getCharacterID());
			deleteStmt.setString(2, money.getCurrency().getName());
			deleteStmt.executeUpdate();

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	public Money getMoneyForCharacter(CharacterClass character) throws SQLException {
		String query = "SELECT currency, totalAmount, weeklyAmount FROM money WHERE `character` =?;";
        Connection connection = null; 
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // SQL query to get the money data for the character
        	connection = connectionManager.getConnection();
            
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, character.getCharacterID());  
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Fetch the currency name from the database and create a Currency object
                String currencyName = rs.getString("currency");
                Currency currency = new Currency(currencyName); // Create a Currency object with the name
                
                // Fetch total and weekly amounts
                int totalAmount = rs.getInt("totalAmount");
                int weeklyAmount = rs.getInt("weeklyAmount");
                
                // Create and return a Money object
                return new Money(character, currency, totalAmount, weeklyAmount);
            } else {
                return null; // No money record found for the character
            }
        } finally {
        	if(connection != null) {
				connection.close();
			}
			if(stmt != null) {
				stmt.close();
			}
        }
    }
	
	public Money updateAmount(CharacterClass character, String currencyName, int newAmount, String reason) 
            throws SQLException {
        String updateMoney = "UPDATE Money SET TotalAmount=? WHERE `Character`=? AND Currency=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            // 先获取当前金额以记录变化
            Money currentMoney = getMoneyForCharacter(character);
            if (currentMoney == null) {
                throw new SQLException("No money record found for this character");
            }

            connection = connectionManager.getConnection();
            // 开始事务
            connection.setAutoCommit(false);

            // 更新金额
            updateStmt = connection.prepareStatement(updateMoney);
            updateStmt.setInt(1, newAmount);
            updateStmt.setInt(2, character.getCharacterID());
            updateStmt.setString(3, currencyName);
            updateStmt.executeUpdate();

            // 创建交易记录
            MoneyTransaction transaction = new MoneyTransaction(
                character,
                currentMoney.getCurrency(),
                newAmount - currentMoney.getTotalAmount(),  // 金额变化
                currentMoney.getTotalAmount(),              // 原金额
                newAmount,                                  // 新金额
                newAmount > currentMoney.getTotalAmount() ? "INCREASE" : "DECREASE",
                reason
            );

            // 保存交易记录
            MoneyTransactionDao.getInstance().create(transaction);

            // 提交事务
            connection.commit();
            
            return getMoneyForCharacter(character);
        } catch (SQLException e) {
            // 如果出错，回滚事务
            if (connection != null) {
                try {
                    connection.rollback();
                } catch(SQLException ex) {
                    throw ex;
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.setAutoCommit(true);  // 恢复自动提交
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }
}
