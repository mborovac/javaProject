/**
 * Package holding all the main file checking classes for Java 8th homework.
 */
package hr.fer.zemris.java.filechecking;

/**
 * Exception describing the error that occurred during the checking of a file.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public FilecheckingException() {
	}
	
	/**
	 * Constructor.
	 * @param message error description
	 */
	public FilecheckingException(String message) {
		super(message);
	}
}
