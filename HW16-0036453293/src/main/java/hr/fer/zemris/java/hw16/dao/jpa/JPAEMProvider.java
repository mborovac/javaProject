package hr.fer.zemris.java.hw16.dao.jpa;

import hr.fer.zemris.java.hw16.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class used as a JPA entity manager provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JPAEMProvider {

	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Method for acquiring the entity manager. If the entity manager does not exist, the method creates one.
	 * 
	 * @return returns the entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Method used to end the database transaction.
	 * 
	 * @throws DAOException if transaction can not commit or can't close the entity manager
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) {
			throw dex;
		}
	}
	
	/**
	 * Class representing the data acquired by ThreadLocal method get.
	 */
	private static class LocalData {
		EntityManager em;
	}
}