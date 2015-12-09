package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to redirect a request from "/index.jsp" to "/servleti/main"
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebServlet("/index.jsp")
public class MainRedirectServer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Redirects the request to "/servleti/main" servlet.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
