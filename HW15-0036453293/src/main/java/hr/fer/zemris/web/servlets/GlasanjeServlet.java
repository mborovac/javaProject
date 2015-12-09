package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollEntry;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to prepare the info about the poll options available for voting. The info is shown to the
 * user by the glasanjeIndex jsp.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method acquires the information from the database and saves it as request parameter for the jsp
	 * to show to the user.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		if(pollID < 1) {
			req.setAttribute("error", "Nelegalan identifikator ankete! Identifikator ne može biti manji od 1.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		DAO dao = DAOProvider.getDao();
		List<PollEntry> polls = dao.getPoll(pollID);
		if(polls.size() < 1) {
			req.setAttribute("error", "Odabrana je nepostojeća anketa!");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		req.setAttribute("poll", polls.get(0));
		List<PollOptionEntry> pollOptions = dao.getPollOptionsByPollID(pollID);
		Collections.sort(pollOptions, new Comparator<PollOptionEntry>() {

			@Override
			public int compare(PollOptionEntry entry1, PollOptionEntry entry2) {
				return (int) (entry1.getId() - entry2.getId());
			}
		});
		req.setAttribute("pollOptions", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
