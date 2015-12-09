package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet called when a user wishes to log in.
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Creates a new user and saves him as a session parameter.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String metoda = req.getParameter("metoda");
		if(!metoda.equals("Login")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		String username = req.getParameter("username");
		if(username == null || username.equals("")) {
			req.setAttribute("loginError", "Unesite korisničko ime.");
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req,resp);
			return;
		}
		
		String password = req.getParameter("password");
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(username);
		if(user == null) {
			req.setAttribute("loginError", "Neispravno korisničko ime ili lozinka.");
			req.setAttribute("username", username);
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req,resp);
			return;
		} else {
			if(!checkPassword(user, password)) {
				req.setAttribute("loginError", "Neispravno korisničko ime ili lozinka.");
				req.setAttribute("username", username);
				req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req,resp);
				return;
			}
		}
		req.getSession().setAttribute("currentUser", user);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
	/**
	 * Method used to check whether the user trying to log in is using a valid password.
	 * 
	 * @param username given username
	 * @param password given password
	 * @return returns a new User if the username and password are correct, null otherwise
	 */
	private boolean checkPassword(BlogUser user, String password) {
		try {
			return compareByteArrays(user.getPasswordHash().getBytes("UTF-8"), 
					BlogUser.calcHash(password).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding!");
		}
		return false;
	}
	
	/**
	 * Method used to compare 2 byte arrays byte by byte.
	 * 
	 * @param array1 1st array
	 * @param array2 2nd array
	 * @return returns true if the arrays are exactly the same, false otherwise
	 */
	private static boolean compareByteArrays(byte[] array1, byte[] array2) {
		if(array1.length != array2.length) {
			return false;
		} else {
			for(int i = 0; i < array1.length; i++) {
				if(array1[i] != array2[i]) {
					return false;
				}
			}
			return true;
		}
	}
}

