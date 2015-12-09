/**
 * Package holding all the collection classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Class used to create an exception that will be thrown if the stack whose elements the user is trying to access is empty
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default class constructor.
	 * 
	 */
	public EmptyStackException() { 
		super(); 
	}

	/**
	 * Class constructor which accepts a string that will be printed to the error stream once the exception is thrown.
	 * 
	 * @param string the string that will be printed
	 */
	public EmptyStackException(String string) {
		super();
		System.err.println(string);
	}
}
