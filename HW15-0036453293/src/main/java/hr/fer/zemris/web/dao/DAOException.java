package hr.fer.zemris.web.dao;

/**
 * Class representing an exception that will be thrown in case of errors originating from database connection.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor.
	 */
	public DAOException() {
	}

	/**
	 * Class constructor. Constructor takes a single argument: a message that will be printed once the exception
	 * is thrown.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}