package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dal.CharacterClassDao;
import dal.MoneyDao;   
import dal.MoneyTransactionDao;
import model.CharacterClass;
import model.Money;
import model.MoneyTransaction;

@WebServlet("/charactermoney")
public class CharacterMoney extends HttpServlet {
    
    protected MoneyDao moneyDao;
    protected CharacterClassDao characterClassDao;
    protected MoneyTransactionDao transactionDao;
    
    @Override
    public void init() throws ServletException {
        moneyDao = MoneyDao.getInstance();
        characterClassDao = CharacterClassDao.getInstance();
        transactionDao = MoneyTransactionDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String characterFullName = request.getParameter("characterfullname");
        System.out.println("Received request for character: " + characterFullName); // Debug log
        
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            request.setAttribute("error", "Character full name is required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterMoney.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Fetch the character object using the full name
            System.out.println("Attempting to fetch character details..."); // Debug log
            CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);

            // Check if character exists
            if (character == null) {
                System.out.println("Character not found in database"); // Debug log
                request.setAttribute("error", "Character not found in database.");
            } else {
                System.out.println("Found character with ID: " + character.getCharacterID()); // Debug log
                // Fetch the character's money information
                Money money = moneyDao.getMoneyForCharacter(character);

                // Check if money information exists
                if (money != null) {
                    System.out.println("Found money record: Amount = " + money.getTotalAmount()); // Debug log
                    request.setAttribute("money", money);
                    request.setAttribute("character", character);
                    
                    // Don't try to get transactions if there's no MoneyTransactionDao
                    if (transactionDao != null) {
                        System.out.println("Fetching transaction history..."); // Debug log
                        List<MoneyTransaction> transactions = transactionDao.getTransactionsByCharacter(character);
                        request.setAttribute("transactions", transactions);
                    }
                } else {
                    System.out.println("No money record found for character"); // Debug log
                    request.setAttribute("error", "No money information available for this character.");
                }
                request.setAttribute("character", character);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterMoney.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred: " + e.getMessage()); // Debug log
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterMoney.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage()); // Debug log
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterMoney.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String characterFullName = request.getParameter("characterfullname");
        String newAmountStr = request.getParameter("newAmount");
        String reason = request.getParameter("reason");

        try {
            if (characterFullName == null || characterFullName.trim().isEmpty()) {
                request.setAttribute("error", "Character name is required.");
                doGet(request, response);
                return;
            }
            
            if (reason == null || reason.trim().isEmpty()) {
                reason = "Manual update"; // Default reason if none provided
            }
            
            int newAmount;
            try {
                newAmount = Integer.parseInt(newAmountStr);
                if (newAmount < 0) {
                    request.setAttribute("error", "Amount cannot be negative.");
                    doGet(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid amount value.");
                doGet(request, response);
                return;
            }
            
            CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
            if (character == null) {
                request.setAttribute("error", "Character not found.");
                doGet(request, response);
                return;
            }
            
            Money currentMoney = moneyDao.getMoneyForCharacter(character);
            if (currentMoney != null) {
                // Add the reason parameter to the updateAmount call
                moneyDao.updateAmount(character, currentMoney.getCurrency().getName(), newAmount, reason);
                request.setAttribute("success", "Money updated successfully!");
            } else {
                request.setAttribute("error", "No money record found for this character.");
            }
            
            doGet(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating money: " + e.getMessage());
            doGet(request, response);
        }
    }
}