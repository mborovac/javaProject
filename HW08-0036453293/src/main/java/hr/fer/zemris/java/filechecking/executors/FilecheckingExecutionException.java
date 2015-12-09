/**
 * Package holding the file checking executors of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.executors;

import hr.fer.zemris.java.filechecking.FilecheckingException;

/**
 * Exception describing the error that occurred during the execution of a statement
 * during the checking of a file.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingExecutionException extends FilecheckingException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public FilecheckingExecutionException() {
	}
	
	/**
	 * Constructor.
	 * @param message error description
	 */
	public FilecheckingExecutionException(String message) {
		super(message);
	}
}
