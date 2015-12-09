/**
 * Package holding all the classes and methods for 3rd Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw3;

/**
 * Exception class meant to be thrown when an iterator is out of elements.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class OutOfElementsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default class constructor.
	 * 
	 */
	public OutOfElementsException() { 
		super(); 
	}
	
	/**
	 * Class constructor which accepts a string that will be printed to the error stream once the exception is thrown.
	 * 
	 * @param string the string that will be printed
	 */
	public OutOfElementsException(String string) {
		super();
		System.err.println(string);
	}
}
