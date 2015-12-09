package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to enable the user to choose the background color of the pages he visits.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SetColorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * Method reads the selected color from the request parameter and sets the session
	 * attribute pickedBgCol.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String color = req.getParameter("color");
		if(color == null || color.isEmpty()) {
			color = "white";
		}
		
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}
}
