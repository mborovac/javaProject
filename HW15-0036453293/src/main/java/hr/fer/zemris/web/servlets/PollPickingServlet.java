package hr.fer.zemris.web.servlets;

import hr.fer.zemris.web.dao.DAO;
import hr.fer.zemris.web.dao.DAOProvider;
import hr.fer.zemris.web.model.PollEntry;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to prepare the info for listPolls jsp which shows the user all available polls
 * and gives the user an option to choose one of the polls and cast his vote in it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PollPickingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Method acquires the info about available polls from the database and prepares it for the jsp as
	 * a request attribute.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		List<PollEntry> listOfPolls = dao.getAllPolls();
		req.setAttribute("polls", listOfPolls);
		req.getRequestDispatcher("/WEB-INF/pages/listPolls.jsp").forward(req, resp);
	}
}
