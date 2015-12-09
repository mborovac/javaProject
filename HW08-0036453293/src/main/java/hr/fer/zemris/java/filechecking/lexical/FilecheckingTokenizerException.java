/**
 * Package holding the classes used in the lexical analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.FilecheckingException;

/**
 * Exception describing the error that occurred during the lexical analysis.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingTokenizerException extends FilecheckingException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public FilecheckingTokenizerException() {
		
	}
	
	/**
	 * Constructor.
	 * @param message error description
	 */
	public FilecheckingTokenizerException(String message) {
		super(message);
	}
}
