package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to call the main jsp.
 * 
 * @author MarkoB
 * vversion 1.0
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Prepares the list of all the registered users for the Main.jsp and calls it.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<BlogUser> listOfUsers = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("listOfUsers", listOfUsers);
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
}
