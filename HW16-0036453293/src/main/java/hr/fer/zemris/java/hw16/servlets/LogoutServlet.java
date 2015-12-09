package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet called when a user wishes to log out.
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Method invalidates the session and redirects to a safe page.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getSession().invalidate();
		String returnTo = req.getParameter("returnTo");
		
		if(returnTo == null || returnTo.isEmpty() || returnTo.contains("/new") || returnTo.contains("/edit/")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		returnTo = returnTo.substring(1);
		returnTo = returnTo.substring(returnTo.indexOf("/"));
		resp.sendRedirect(req.getServletContext().getContextPath() + returnTo);
	}
}
