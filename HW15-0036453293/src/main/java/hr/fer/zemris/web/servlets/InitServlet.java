package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollEntry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Servlet used to fill the database with needed information on both available polls.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Method checks whether both polls are present in the Polls table. If any of the polls is missing, it fills
	 * both Polls and PollOptions table with corresponding rows.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		List<PollEntry> pollEntrys = dao.getAllPolls();
		List<String> listOfTitles = new ArrayList<>();
		for(PollEntry entry: pollEntrys) {
			listOfTitles.add(entry.getTitle());
		}
		int counter = 0;
		// 1st poll
		if(!listOfTitles.contains("Glasanje za omiljeni bend:")) {
			Long id = dao.insertPoll("Glasanje za omiljeni bend:", "Od sljedećih bendova,"
					+ " koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
			if(id != null) {
				// 1st poll options
				if(dao.insertPollOption("The Beatles", "http://www.youtube.com/watch?v=nkhTA6MQ3BQ", id, 0)
						!= null) {
					counter++;
				}
				if(dao.insertPollOption("The Platters", "http://www.youtube.com/watch?v=57tK6aQS_H0", id, 0)
						!= null) {
					counter++;
				}
				if(dao.insertPollOption("Simon And Garfunkel", "http://www.youtube.com/watch?v=4zLfCnGVeL4",
						id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("The Beach Boys", "http://www.youtube.com/watch?v=B0dWXyTa2Cw",
						id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Bob Dylan", "http://www.youtube.com/watch?v=QqvUz0HrNKY", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("The Four Seasons", "http://www.youtube.com/watch?v=40bTOCv3_ak",
						id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Red Hot Chili Peppers", "http://www.youtube.com/watch?v=YlUKcNNmywk",
						id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Iron Maiden", "http://www.youtube.com/watch?v=7mHe6FMs46o", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Sting", "http://www.youtube.com/watch?v=s_IMFIOGCFc", id, 0) != null) {
					counter++;
				}
			}
		}
		// 2nd poll
		if(!listOfTitles.contains("Anketa o preglednicima:")) {
			Long id = dao.insertPoll("Anketa o preglednicima:", "Koji preglednik koristite? Kliknite " +
					"na link kako biste glasali!");
			if(id != null) {
				// 2nd poll options
				if(dao.insertPollOption("Opera", "http://www.opera.com/computer/windows", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Google Chrome", "http://google-chrome.en.softonic.com/", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Mozilla Firefox", "http://download.cnet.com/mozilla-firefox/", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Safari", "http://safari.en.softonic.com/", id, 0) != null) {
					counter++;
				}
				if(dao.insertPollOption("Internet Explorer(Really?)",
						"http://internet-explorer-7.en.softonic.com/", id, 0) != null) {
					counter++;
				}
			}
		}
		req.setAttribute("numberOfOptions", counter);
		req.getRequestDispatcher("/WEB-INF/pages/init.jsp").forward(req, resp);
	}
}
