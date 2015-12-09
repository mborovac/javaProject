/**
 * Package holding all the token classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class used to create and maintain a single string token. This token is used as a representation of a string and
 * stores its value.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TokenString extends Token {
	
	private String value;
	
	/**
	 * Class constructor. Constructor accepts a single string value and stores it.
	 * 
	 * @param value the value that will be stored in the token
	 */
	public TokenString(String value) {
		this.value = value;
	}
	
	/**
	 * @see hr.fer.zemris.java.custom.scripting.tokens.Token#asText()
	 */
	@Override
	public String asText() {
		return value;
	}
}
