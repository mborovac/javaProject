package hr.fer.zemris.web.dao.sql;

import java.sql.Connection;

/**
 * Class used to map the available database connections to the ID of a thread that is currently using it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Method used to set the connection for the current thread or delete the mapping if the given argument is null.
	 * 
	 * @param con connection
	 */
	public static void setConnection(Connection con) {
		if(con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Method used to retrieve the connection available for use by the current thread.
	 * 
	 * @return returns the assigned connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}
}
