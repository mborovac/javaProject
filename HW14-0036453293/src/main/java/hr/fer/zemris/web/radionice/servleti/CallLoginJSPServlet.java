package hr.fer.zemris.web.radionice.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet called when the user clicks on the log in button.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CallLoginJSPServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}
}
