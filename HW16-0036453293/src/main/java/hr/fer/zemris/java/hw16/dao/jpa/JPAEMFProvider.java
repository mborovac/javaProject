package hr.fer.zemris.java.hw16.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class used as a JPA entity manager factory provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;
	
	/**
	 * Method for acquiring the entity manager factory.
	 * 
	 * @return returns the current entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Method for setting the entity manager factory.
	 * 
	 * @param emf new entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}