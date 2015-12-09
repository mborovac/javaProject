package hr.fer.zemris.java.hw16.dao;

/**
 * Exception that is thrown if there is an error in the data base communication.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DAOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor.
	 * 
	 * @param message message that will be shown
	 * @param cause the cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Class constructor.
	 * 
	 * @param message message that will be shown
	 */
	public DAOException(String message) {
		super(message);
	}
}