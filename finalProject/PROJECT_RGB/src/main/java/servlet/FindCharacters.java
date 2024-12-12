package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.CharacterClassDao;
import model.CharacterClass;

@WebServlet("/findcharacters")
public class FindCharacters extends HttpServlet {
	
	protected CharacterClassDao characterClassDao;
	
	@Override
	public void init() throws ServletException {
		characterClassDao = CharacterClassDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<CharacterClass> characters = new ArrayList<CharacterClass>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String firstName = req.getParameter("firstname");
        String showAll = req.getParameter("showAll");

        try {
            if (showAll != null) {
                characters = characterClassDao.getAllCharacters();
                messages.put("success", "Displaying all characters");
            } else if (firstName != null && !firstName.trim().isEmpty()) {
            	// Retrieve Characters, and store as a message.
                characters = characterClassDao.getCharactersByFirstName(firstName);
                messages.put("success", "Displaying results for " + firstName);
                // Save the previous search term, so it can be used as the default
            	// in the input box when rendering FindCharacters.jsp.
                messages.put("previousFirstName", firstName);
            } else {
                messages.put("success", "Please enter a valid name or show all characters.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("characters", characters);
        
        req.getRequestDispatcher("/FindCharacters.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<CharacterClass> characters = new ArrayList<CharacterClass>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String firstName = req.getParameter("firstname");
        if (firstName == null || firstName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve Characters, and store as a message.
        	try {
            	characters = characterClassDao.getCharactersByFirstName(firstName);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + firstName);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindCharacters.jsp.
        	messages.put("previousFirstName", firstName);
        }
        req.setAttribute("characters", characters);
        
        req.getRequestDispatcher("/FindCharacters.jsp").forward(req, resp);
	}
}
