package hr.fer.zemris.java.hw16.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import hr.fer.zemris.java.hw16.dao.DAO;
import hr.fer.zemris.java.hw16.dao.DAOException;
import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

/**
 * Class implementing the DAO.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JPADAOImpl implements DAO {

	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#getBlogEntry(java.lang.Long)
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#addBlogEntry(hr.fer.zemris.java.hw16.model.BlogEntry)
	 */
	@Override
	public void addBlogEntry(BlogEntry blogEntry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(blogEntry);
	}
	
	@Override
	@Transactional
	public void deleteBlogEntry(Long entryId) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry entry = this.getBlogEntry(entryId);
		BlogUser user = this.getBlogUser(entry.getCreator().getNick());
		user.getBlogs().remove(entry);
		em.remove(entry);
		em.persist(user);
	}

	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#getBlogUser(java.lang.String)
	 */
	@Override
	public BlogUser getBlogUser(String nickName) throws DAOException {
		BlogUser user = null;
		try {
			user = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("select b from BlogUser as b where b.nick=:nickName")
					.setParameter("nickName", nickName).getSingleResult();
		} catch (NoResultException  ignorable) {
		}
		return user;
	}
	
	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#addBlogUser(hr.fer.zemris.java.hw16.model.BlogUser)
	 */
	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#getAllUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		return JPAEMProvider.getEntityManager().createQuery("select b from BlogUser as b").getResultList();
	}

	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#getUserBlogEntries(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserBlogEntries(String nickName) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser blogUser = this.getBlogUser(nickName);
		return em.createQuery("select e from BlogEntry as e where e.creator=:bu")
					.setParameter("bu", blogUser)
					.getResultList();
	}

	/**
	 * @see hr.fer.zemris.java.hw16.dao.DAO#addBlogComment(hr.fer.zemris.java.hw16.model.BlogComment)
	 */
	@Override
	public void addBlogComment(BlogComment blogComment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(blogComment);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		return em.createQuery("select e from BlogComment as e where e.blogEntry=:be")
					.setParameter("be", blogEntry)
					.getResultList();
	}
}
