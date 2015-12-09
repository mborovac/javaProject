package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to prepare the info about vote state for the jsp.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method acquires the vote state from the database and prepares the info for the jsp by storing
	 * it as request attribute.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		if(pollID < 1) {
			req.setAttribute("error", "Nelegalan identifikator ankete! Identifikator ne može biti manji od 1.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		req.setAttribute("pollID", pollID);
		List<PollOptionEntry> pollOptions = dao.getPollOptionsByPollID(pollID);
		req.setAttribute("results", pollOptions);
		int highestVotesNumber = (int) pollOptions.get(0).getVotesCount();
		List<PollOptionEntry> highestVotedOptions = new ArrayList<>();
		for(PollOptionEntry entry: pollOptions) {
			if(entry.getVotesCount() == highestVotesNumber) {
				highestVotedOptions.add(entry);
			}
		}
		req.setAttribute("highestVotedOptions", highestVotedOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
