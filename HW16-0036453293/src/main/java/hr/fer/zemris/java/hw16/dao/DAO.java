package hr.fer.zemris.java.hw16.dao;

import java.util.List;

import javax.transaction.Transactional;

import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

/**
 * Interface declaring all the methods used in communication with the database used to
 * maintain a blog provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface DAO {

	/**
	 * Method used to acquire a blog entry based on the given ID.
	 * 
	 * @param id blog entry ID
	 * @return entry with the given ID or null if such entry doesn't exist
	 * @throws DAOException if there is an error
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Method used to add a new blog entry.
	 * 
	 * @param blogEntry blog entry that should be added to the data base
	 * @throws DAOException if there is an error
	 */
	public void addBlogEntry(BlogEntry blogEntry) throws DAOException;
	
	@Transactional
	public void deleteBlogEntry(Long entryId) throws DAOException;
	
	/**
	 * Method used to acquire a blog user with the given nick name.
	 * 
	 * @param nickName the nick name of the user that should be acquired
	 * @return returns the user with the given nick name or null if such user doesn't exist
	 * @throws DAOException if there is an error
	 */
	public BlogUser getBlogUser(String nickName) throws DAOException;
	
	/**
	 * Method used to add a new blog user to the data base.
	 * 
	 * @param user blog user that should be added to the data base 
	 * @throws DAOException if there is an error
	 */
	public void addBlogUser(BlogUser user) throws DAOException;
	
	/**
	 * Method used to acquire all the registered blog users.
	 * 
	 * @return returns a list of all the registered blog users, can be empty
	 * @throws DAOException if there is an error
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	
	/**
	 * Method used to acquire all the blog entries made by the user with the given nick name.
	 * 
	 * @param nick nick name of the blog entry owner
	 * @return returns a list of blog entries made by the selected user, can be empty
	 * @throws DAOException if there is an error
	 */
	public List<BlogEntry> getUserBlogEntries(String nick) throws DAOException;
	
	/**
	 * Method used to add a new blog comment to the data base.
	 * 
	 * @param blogComment blog comment that should be added to the data base
	 * @throws DAOException if there is an error
	 */
	public void addBlogComment(BlogComment blogComment) throws DAOException;
	
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) throws DAOException;
}