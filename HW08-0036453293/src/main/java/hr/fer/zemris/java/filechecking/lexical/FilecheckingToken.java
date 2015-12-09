/**
 * Package holding the classes used in the lexical analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.lexical;

/**
 * Class representing a token created by the lexical analysis of a file.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingToken {
	
	private FilecheckingTokenType tokenType;
	private Object value;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, a type of the token and its value.
	 * 
	 * @param tokenType the type of the token
	 * @param value token value
	 */
	public FilecheckingToken(FilecheckingTokenType tokenType, Object value) {
		if(tokenType == null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		this.tokenType = tokenType;
		this.value = value;
	}
	
	/**
	 * Method for retrieving the type of the token.
	 * 
	 * @return returns the type of the token
	 */
	public FilecheckingTokenType getTokenType() {
		return this.tokenType;
	}
	
	/**
	 * Method for retrieving the value of the token.
	 * 
	 * @return returns the value of the token
	 */
	public Object getValue() {
		return this.value;
	}
}
