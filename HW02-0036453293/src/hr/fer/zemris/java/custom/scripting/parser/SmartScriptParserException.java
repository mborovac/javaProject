/**
 * Package holding all the parsing classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class used to create an exception that will be thrown if the document being parsed is ill-formed.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default class constructor.
	 * 
	 */
	public SmartScriptParserException() { 
		super(); 
	}
	
	/**
	 * Class constructor which accepts a string that will be printed to the error stream once the exception is thrown.
	 * 
	 * @param string the string that will be printed
	 */
	public SmartScriptParserException(String string) {
		super();
		System.err.println(string);
	}
}
