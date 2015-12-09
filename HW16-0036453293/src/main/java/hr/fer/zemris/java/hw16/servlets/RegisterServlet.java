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
 * Servlet called when a new user wishes to register.
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Checks all the sent data, adds the user to the data base if the data is fine, redirects back to the
	 * Form.jsp with errors if data is wrong or missing.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		boolean error = false;
		String metoda = req.getParameter("metoda");
		if(!metoda.equals("Register")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		String firstName = req.getParameter("firstName");
		if(firstName == null || firstName.equals("")) {
			req.setAttribute("firstNameError", "Ime je obavezno.");
			req.setAttribute("firstName", "");
			error = true;
		} else {
			req.setAttribute("firstName", firstName);
		}
		
		String lastName = req.getParameter("lastName");
		if(lastName == null || lastName.equals("")) {
			req.setAttribute("lastNameError", "Prezime je obavezno.");
			req.setAttribute("lastName", "");
			error = true;
		} else {
			req.setAttribute("lastName", lastName);
		}
		
		String email = req.getParameter("email");
		if(email == null || email.equals("")) {
			req.setAttribute("emailError", "E-mail je obavezan.");
			req.setAttribute("email", "");
			error = true;
		} else if(!email.matches(".+@.+\\..+")) { 	// something@provider.domain
			req.setAttribute("emailError", "Primjer e-maila: ivan.ivkovic@gmail.com.");
			req.setAttribute("email", email);
			error = true;
		} else {
			req.setAttribute("email", email);
		}
		
		String nickName = req.getParameter("nickName");
		if(nickName == null || nickName.equals("")) {
			req.setAttribute("nickNameError", "Nadimak je obavezan.");
			req.setAttribute("nickName", "");
			error = true;
		} else if(nickNameTaken(nickName)) {
			req.setAttribute("nickNameError", "Nadimak je zauzet.");
			req.setAttribute("nickName", nickName);
			error = true;
		} else {
			req.setAttribute("nickName", nickName);
		}
		
		String password = req.getParameter("password");
		if(password == null || password.equals("")) {
			req.setAttribute("passwordError", "Šifra je obavezna.");
			error = true;
		}
		if(error) {
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser newUser = new BlogUser(firstName, lastName, email, nickName, BlogUser.calcHash(password));
		DAOProvider.getDAO().addBlogUser(newUser);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
		return;
	}

	private boolean nickNameTaken(String nickName) {
		List<BlogUser> listOfUsers = DAOProvider.getDAO().getAllUsers();
		System.out.println("Trazeni nickname: " + nickName);
		for(BlogUser user: listOfUsers) {
			System.out.println(user.getNick());
			if(user.getNick().equalsIgnoreCase(nickName)) {
				return true;
			}
		}
		return false;
	}
}
