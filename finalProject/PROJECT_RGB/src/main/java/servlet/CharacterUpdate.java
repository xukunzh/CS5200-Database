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


@WebServlet("/characterupdate")
public class CharacterUpdate extends HttpServlet {
	
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

        // Retrieve user and validate.
        String characterFullName = req.getParameter("characterfullname");
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Character Fullname.");
        } else {
        	try {
        		CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
        		if(character == null) {
        			messages.put("success", "Character Fullname does not exist.");
        		}
        		req.setAttribute("character", character);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String characterFullName = req.getParameter("characterfullname");
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Character Fullname.");
        } else {
        	try {
        		CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
        		if(character == null) {
        			messages.put("success", "Character Fullname does not exist. No update to perform.");
        		} else {
        			String newLastName = req.getParameter("lastname");
        			if (newLastName == null || newLastName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid LastName.");
        	        } else {
        	        	character = characterClassDao.updateLastName(character, newLastName);
        	        	messages.put("success", "Successfully updated " + newLastName);
        	        }
        		}
        		req.setAttribute("character", character);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
    }
}
