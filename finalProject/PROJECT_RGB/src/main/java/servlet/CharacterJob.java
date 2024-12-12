package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.*;
import model.*;

@WebServlet("/characterjob")
public class CharacterJob extends HttpServlet {
    protected CharacterJobDao characterJobDao;
    protected CharacterClassDao characterClassDao;
    protected JobLevelHistoryDao jobHistoryDao;
    protected JobDao jobDao;
    
    @Override
    public void init() throws ServletException {
        characterJobDao = CharacterJobDao.getInstance();
        characterClassDao = CharacterClassDao.getInstance();
        jobHistoryDao = JobLevelHistoryDao.getInstance();
        jobDao = JobDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String characterFullName = request.getParameter("characterfullname");
        
        if (characterFullName == null || characterFullName.trim().isEmpty()) {
            request.setAttribute("error", "Character full name is required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterJob.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            CharacterClass character = characterClassDao.getCharacterByFullName(characterFullName);
            if (character == null) {
                request.setAttribute("error", "Character not found.");
            } else {
                List<model.CharacterJob> characterJobs = characterJobDao.getCharacterJobsByCharacterId(character.getCharacterID());
                request.setAttribute("characterJobs", characterJobs);
                
                List<JobLevelHistory> jobHistory = jobHistoryDao.getHistoryForCharacter(character);
                request.setAttribute("jobHistory", jobHistory);
                
                request.setAttribute("character", character);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterJob.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to retrieve character job information: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CharacterJob.jsp");
            dispatcher.forward(request, response);
        }
    }
    
}