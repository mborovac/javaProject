/**
 * Package holding all the token classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class used to create and maintain a single operator token. This token is used as a representation of an operator and
 * stores its symbol.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TokenOperator extends Token {
	
	private String symbol;
	
	/**
	 * Class constructor. Constructor accepts a single string value and stores it.
	 * 
	 * @param value the value that will be stored in the token
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}
	
	
	/**
	 * @see hr.fer.zemris.java.custom.scripting.tokens.Token#asText()
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
