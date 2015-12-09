/**
 * Package containing classes used in syntax analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.FilecheckingException;

/**
 * Exception describing the error that occurred during the syntax analysis.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingSyntaxException extends FilecheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public FilecheckingSyntaxException() {
	}

	/**
	 * Constructor.
	 * @param message error description
	 */
	public FilecheckingSyntaxException(String message) {
		super(message);
	}
}
