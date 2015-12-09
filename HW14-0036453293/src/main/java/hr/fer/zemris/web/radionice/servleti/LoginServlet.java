package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet called when a user wishes to log in.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String metoda = req.getParameter("metoda");
		if(!metoda.equals("Login")) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/listaj");
			return;
		}
		
		String username = req.getParameter("username");
		if(username == null || username.equals("")) {
			req.setAttribute("greska", "Unesite korisničko ime.");
			req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req,
					resp);
			return;
		}
		
		String password = req.getParameter("password");
		User korisnik = provjeriKorisnika(username, password);

		if (korisnik == null) {
			req.setAttribute("greska", "Neispravno korisničko ime ili lozinka.");
			req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req,
					resp);
			return;
		}

		req.getSession().setAttribute("current.user", korisnik);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}
	
	/**
	 * Method used to check whether the user trying to log in is using valid information.
	 * 
	 * @param username given username
	 * @param password given password
	 * @return returns a new User if the username and password are correct, null otherwise
	 */
	private User provjeriKorisnika(String username, String password) {
		
		if (username.equals("aante") && password.equals("tajna")) {
			return new User("aante", "tajna", "Ante", "Anić");
		}
		return null;
	}
}
