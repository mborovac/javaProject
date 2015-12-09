/**
 * Package holding all the token classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class used to create and maintain a single parsing token.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class Token {
	
	/**
	 * Method used to access the data the token is holding.
	 * 
	 * @return returns the string the token is holding
	 */
	public String asText() {
		return null;
	}
	
	public abstract Object getValue();
}
