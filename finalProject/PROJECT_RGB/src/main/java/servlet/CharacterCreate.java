package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.CharacterClassDao;
import dal.PlayerAccountDao;
import model.CharacterClass;
import model.PlayerAccount;

@WebServlet("/charactercreate")
public class CharacterCreate extends HttpServlet {

    protected CharacterClassDao characterClassDao;
    protected PlayerAccountDao playerAccountDao;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO objects.
        characterClassDao = CharacterClassDao.getInstance();
        playerAccountDao = PlayerAccountDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
        
        // Just render the JSP page for character creation.
        req.getRequestDispatcher("/CharacterCreate.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Retrieve and validate player username (email) and character details.
        String playerEmail = req.getParameter("username"); // Player's email
        String firstName = req.getParameter("firstname"); // Character first name
        String lastName = req.getParameter("lastname"); // Character last name

        if (playerEmail == null || playerEmail.trim().isEmpty() || 
            firstName == null || firstName.trim().isEmpty() || 
            lastName == null || lastName.trim().isEmpty()) {
            messages.put("error", "Please provide valid player email, first name, and last name.");
        } else {
            try {
                // Retrieve PlayerAccount using the email address.
                PlayerAccount playerAccount = playerAccountDao.getPlayerAccountByEmail(playerEmail);
                
                if (playerAccount == null) {
                    messages.put("error", "Player with email " + playerEmail + " does not exist.");
                } else {
                    // Create a new Character object with the PlayerAccount object.
                    CharacterClass newCharacter = new CharacterClass(playerAccount, firstName, lastName);
                    
                    // Create the character in the database.
                    CharacterClass createdCharacter = characterClassDao.create(newCharacter);

                    if (createdCharacter != null) {
                        messages.put("success", "Successfully created character for " + createdCharacter.getFirstName() + " " + createdCharacter.getLastName());
                    } else {
                        messages.put("error", "Error creating character. Please try again.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("error", "Database error: " + e.getMessage());
                throw new IOException(e);
            }
        }

        // Render the CharacterCreate.jsp page with success or error messages.
        req.getRequestDispatcher("/CharacterCreate.jsp").forward(req, resp);
    }
}
