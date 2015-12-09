package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollOptionEntry;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to register a vote cast for a band in the voting application.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class GlasanjeGlasajServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method acquires the vote count from the database, increases it by 1 and updates the database.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		if(id < 1) {
			req.setAttribute("error", "Nelegalan identifikator opcije! Identifikator ne može biti manji od 1.");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		DAO dao = DAOProvider.getDao();
		List<PollOptionEntry> selectedEntry = dao.getPollOption(id);
		if(selectedEntry.isEmpty()) {
			req.setAttribute("error", "Odabrana je nepostojeća opcija!");
			req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp").forward(req, resp);
		}
		PollOptionEntry odabranaOpcija = selectedEntry.get(0);
		odabranaOpcija.setVotesCount(odabranaOpcija.getVotesCount() + 1);
		dao.updatePollOption(odabranaOpcija.getId(), odabranaOpcija.getVotesCount());
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" 
		+ odabranaOpcija.getPollID());
	}
}
