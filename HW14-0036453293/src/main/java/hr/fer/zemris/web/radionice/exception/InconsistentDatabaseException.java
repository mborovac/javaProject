package hr.fer.zemris.web.radionice.exception;

/**
 * Class used to create an exception that will be thrown if the options currently set are not the same
 * as the original ones.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InconsistentDatabaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default class constructor.
	 * 
	 */
	public InconsistentDatabaseException() { 
		super(); 
	}
	
	/**
	 * Class constructor which accepts a string that will be printed to the error stream once the exception is thrown.
	 * 
	 * @param string the string that will be printed
	 */
	public InconsistentDatabaseException(String string) {
		super();
		System.err.println(string);
	}
}
