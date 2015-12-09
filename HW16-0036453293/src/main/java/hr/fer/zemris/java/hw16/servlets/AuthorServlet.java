package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.log.LogCreation;
import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to handle all the requests with URI containing "author". It handles all the blog creations
 * and edits, blog deletions and comment creations.
 * 
 * @author MarkoB
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *  javax.servlet.http.HttpServletResponse)
	 *  
	 *  Method used to handle new blog and comment creations, blog deletion and blog edits.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		req.setAttribute("previousPage", req.getRequestURI());
		
		boolean error = false;
		String metoda = req.getParameter("metoda");
		if(!metoda.equals("Create") && !metoda.equals("Edit") && !metoda.equals("Comment") && !metoda.equals("Delete")) {
			BlogUser blogUser = (BlogUser) req.getSession().getAttribute("currentUser");
			if(blogUser == null) {
				resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
				return;
			}
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick());
			return;
		}
		
		boolean newBlog = false;
		boolean editBlog = false;
		boolean newComment = false;
		boolean deleteBlog = false;
		if(metoda.equals("Create")) {
			newBlog = true;
		} else if(metoda.equals("Edit")) {
			editBlog = true;
		} else if (metoda.equals("Comment")){
			newComment = true;
		} else {
			deleteBlog = true;
		}
		
		String title = null;
		String text = null;
		if(newBlog || editBlog) {
			title = req.getParameter("title");
			if(title == null || title.equals("")) {
				req.setAttribute("titleError", "Naziv bloga je obavezan.");
				req.setAttribute("title", "");
				error = true;
			} else {
				req.setAttribute("title", title);
			}
			
			text = req.getParameter("text");
			if(text == null || text.equals("")) {
				req.setAttribute("textError", "Blog mora imati nekakav tekst.");
				req.setAttribute("text", "");
				error = true;
			} else {
				req.setAttribute("text", text);
			}
			if(error) {
				if(newBlog) {
					req.setAttribute("action", "new");
				} else if(editBlog) {
					req.setAttribute("action", "edit");
					System.out.println("ID: " + Long.parseLong(req.getParameter("entryId")));
					req.setAttribute("entryId", Long.parseLong(req.getParameter("entryId")));
				}
				req.getRequestDispatcher("/WEB-INF/pages/Form.jsp").forward(req, resp);
				return;
			}
		}
		
		String message = null;
		String email = null;
		if(newComment) {
			message = req.getParameter("message");
			if(message == null || message.equals("")) {
				req.setAttribute("messageError", "Poruka je obavezna.");
				req.setAttribute("message", "");
				error = true;
			} else {
				req.setAttribute("message", message);
			}
			
			email = req.getParameter("email");
			if(email == null || email.equals("")) {
				req.setAttribute("emailError", "E-mail je obavezan.");
				req.setAttribute("email", "");
				error = true;
			} else {
				req.setAttribute("email", email);
			}
			if(error) {
				Long entryId = Long.parseLong(req.getParameter("entryId"));
				System.out.println("id: " + entryId);
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
				System.out.println(blogEntry.getTitle());
				req.setAttribute("blogEntry", blogEntry);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
				return;
			}
		}
		
		BlogUser blogUser = (BlogUser) req.getSession().getAttribute("currentUser");
		if(blogUser == null) {
			if(!newComment) {
				if(editBlog) {
					req.setAttribute("errorMessage", "Korsnik može mijenjati samo svoje blogove!");
				} else if(newBlog) {
					req.setAttribute("errorMessage", "Nemate dopuštenje za stvaranje novog bloga!"
						+ " Ulogirajte se ili registrirajte.");
				}
				req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
				return;
			}
		}
		
		if(editBlog) {
			Long entryId = Long.parseLong(req.getParameter("entryId"));
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
			blogEntry.setTitle(title);
			blogEntry.setText(text);
			blogEntry.setLastModifiedAt(new Date());
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick());
			return;
		} else if(newBlog) {
			BlogEntry blogEntry = new BlogEntry(title, text, blogUser);
			DAOProvider.getDAO().addBlogEntry(blogEntry);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick());
			return;
		} else if(deleteBlog) {
			Long entryId = Long.parseLong(req.getParameter("entryId"));
			DAOProvider.getDAO().deleteBlogEntry(entryId);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick());
			return;
		} else {
			Long entryId = Long.parseLong(req.getParameter("entryId"));
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
			BlogComment blogComment = new BlogComment(email, message, blogEntry);
			DAOProvider.getDAO().addBlogComment(blogComment);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick() + 
			"/" + entryId);
			return;
		}
	}
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Method used to handle all the get requests sent to URIs with "author" in them.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		LogCreation.createLog(req);
		
		req.setCharacterEncoding("UTF-8");
		
		req.setAttribute("previousPage", req.getRequestURI());
		
		String requested = req.getPathInfo().substring(1);
		String[] requestElements = null;
		String nickName = null;
		if(requested != null && !requested.isEmpty()) {
			requestElements = requested.split("/");
			nickName = requestElements[0];
		} else {
			req.setAttribute("errorMessage", "Invalid URL!");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
			return;
		}
		BlogUser blogUser = DAOProvider.getDAO().getBlogUser(nickName);
		if(blogUser == null) {
			req.setAttribute("errorMessage", "User with given nick name does not exist!");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
			return;
		}
		if(requestElements.length == 1) {
			List<BlogEntry> listOfEntries = DAOProvider.getDAO().getUserBlogEntries(nickName);
			req.setAttribute("entries", listOfEntries);
			req.setAttribute("nickName", nickName);
			req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);
			return;
		} else if(requestElements.length == 2) {
			if(requestElements[1].equals("new")) {
				req.setAttribute("action", "new");
				req.getRequestDispatcher("/WEB-INF/pages/Form.jsp").forward(req, resp);
				return;
			} else {
				Long entryId = null;
				try {
					entryId = Long.parseLong(requestElements[1]);
				} catch (NumberFormatException e) {
					req.setAttribute("errorMessage", "Invalid blog entry ID!");
					req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
					return;
				}
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
				if(blogEntry == null || !blogEntry.getCreator().getNick().equals(nickName)) {
					req.setAttribute("errorMessage", "Selected blog entry does not belong to the selected user!");
					req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
					return;
				}
				req.setAttribute("blogEntry", blogEntry);
				req.setAttribute("nickName", requestElements[0]);
				req.setAttribute("entryId", entryId);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
				return;
			}
		} else if(requestElements.length == 3) {
			if(requestElements[1].equals("edit") || requestElements[1].equals("delete")) {
				Long entryId;
				try {
					entryId = Long.parseLong(requestElements[2]);
				} catch (NumberFormatException e) {
					req.setAttribute("errorMessage", "Invalid blog entry ID!");
					req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
					return;
				}
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(entryId);
				req.setAttribute("title", blogEntry.getTitle());
				req.setAttribute("text", blogEntry.getText());
				if(requestElements[1].equals("edit")) {
					req.setAttribute("action", "edit");
				} else if(requestElements[1].equals("delete")) {
					req.setAttribute("action", "delete");
				}
				req.setAttribute("entryId", blogEntry.getId());
				req.setAttribute("entryId", entryId);
				req.getRequestDispatcher("/WEB-INF/pages/Form.jsp").forward(req, resp);
				return;
			} else {
				req.setAttribute("errorMessage", "Invalid URL!");
				req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
				return;
			}
		} else {
			req.setAttribute("errorMessage", "Invalid URL!");
			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
			return;
		}
	}
}
