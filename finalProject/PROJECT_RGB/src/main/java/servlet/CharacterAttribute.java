package servlet;

import dal.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/characterattribute")
public class CharacterAttribute extends HttpServlet {
	
	protected  CharacterAttributeDao characterAttributeDao;
	protected CharacterClassDao characterClassDao;
	
	@Override
	public void init() throws ServletException {
		characterAttributeDao = CharacterAttributeDao.getInstance();
		characterClassDao = CharacterClassDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String characterFullName = req.getParameter("characterfullname");
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            messages.put("title", "Invalid Character Full Name.");
        } else {
        	messages.put("title", "CharacterAttributes for " + characterFullName);
        }
        
        List<CharacterAttributes> characterAttributes = new ArrayList<CharacterAttributes>();
        try {
        	CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
        	
            if (character == null) {
            	messages.put("error", "Character not found.");
                req.getRequestDispatcher("/CharacterAttribute.jsp").forward(req, resp);
                return;
            }
            else {
            	int characterID = character.getCharacterID();
            	characterAttributes.addAll(characterAttributeDao.getCharacterAttributeByCharacterID(characterID));
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
			messages.put("error", "Unable to retrieve character attributes due to database error.");
        }
        req.setAttribute("characterAttributes", characterAttributes);
        req.getRequestDispatcher("/CharacterAttribute.jsp").forward(req, resp);
	}
}
