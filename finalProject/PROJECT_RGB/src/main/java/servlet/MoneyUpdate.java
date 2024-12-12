package servlet;

import dal.*;
import model.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/moneyupdate")
public class MoneyUpdate extends HttpServlet {
    protected MoneyDao moneyDao;
    protected CharacterClassDao characterClassDao;

    @Override
    public void init() throws ServletException {
        moneyDao = MoneyDao.getInstance();
        characterClassDao = CharacterClassDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String characterFullName = req.getParameter("characterfullname");
        String currencyName = req.getParameter("currency");
        String amountStr = req.getParameter("newAmount");
        String reason = req.getParameter("reason");

        if (characterFullName == null || characterFullName.trim().isEmpty() ||
            currencyName == null || currencyName.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {
            resp.sendRedirect("charactermoney?characterfullname=" + characterFullName + "&error=Missing required fields");
            return;
        }

        try {
            CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
            if (character == null) {
                resp.sendRedirect("findcharacters?error=Character not found");
                return;
            }

            int newAmount;
            try {
                newAmount = Integer.parseInt(amountStr);
                if (newAmount < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("charactermoney?characterfullname=" + characterFullName + 
                    "&error=Invalid amount value");
                return;
            }

            // update money and store it in transaction
            moneyDao.updateAmount(character, currencyName, newAmount, reason);
            
            // redirect to money page
            resp.sendRedirect("charactermoney?characterfullname=" + characterFullName + 
                "&success=Money updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("charactermoney?characterfullname=" + characterFullName + 
                "&error=" + e.getMessage());
        }
    }
}