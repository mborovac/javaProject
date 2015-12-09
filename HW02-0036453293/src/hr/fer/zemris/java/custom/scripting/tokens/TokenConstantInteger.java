/**
 * Package holding all the token classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class used to create and maintain a single integer constant token. This token is used as a representation of a constant 
 * and stores its value.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TokenConstantInteger extends Token {
	
	private Integer value;
	
	/**
	 * Class constructor. Constructor accepts a single integer value and stores it.
	 * 
	 * @param value the value that will be stored in the token
	 */
	public TokenConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * @see hr.fer.zemris.java.custom.scripting.tokens.Token#asText()
	 */
	@Override
	public String asText() {
		return value.toString();
	}
}
