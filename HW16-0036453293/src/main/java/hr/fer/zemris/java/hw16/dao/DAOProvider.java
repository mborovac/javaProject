package hr.fer.zemris.java.hw16.dao;

import hr.fer.zemris.java.hw16.dao.jpa.JPADAOImpl;

/**
 * Class used to acquire the currenty used DAO implementation.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DAOProvider {
	
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Method returns the current DAO implementation.
	 * 
	 * @return returns the DAO implementation
	 */
	public static DAO getDAO() {
		return dao;
	}
}