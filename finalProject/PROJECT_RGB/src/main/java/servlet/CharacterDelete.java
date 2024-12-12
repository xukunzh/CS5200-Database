package servlet;

import dal.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/characterdelete")
public class CharacterDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Character");        
        req.getRequestDispatcher("/CharacterDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String characterFullName = req.getParameter("characterfullname");
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            messages.put("title", "Invalid Character FullName");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the BlogUser.
	        try {
	        	CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
	        	character = characterClassDao.delete(character);
	        	// Update the message.
		        if (character == null) {
		            messages.put("title", "Successfully deleted " + characterFullName);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + characterFullName);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterDelete.jsp").forward(req, resp);
    }
}
