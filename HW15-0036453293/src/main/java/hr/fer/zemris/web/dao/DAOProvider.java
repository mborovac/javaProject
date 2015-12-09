package hr.fer.zemris.web.dao;

import hr.fer.zemris.web.dao.sql.SQLDAO;

/**
 * Singleton class with knowledge of the currently available DAO provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DAOProvider {

	private static DAO dao = new SQLDAO();
	
	/**
	 * Method used to acquire an implementation of the DAO interface.
	 * 
	 * @return returns (currently only one) available DAO interface implementation.
	 */
	public static DAO getDao() {
		return dao;
	}
}
