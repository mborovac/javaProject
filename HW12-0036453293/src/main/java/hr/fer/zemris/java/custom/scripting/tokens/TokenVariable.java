/**
 * Package holding all the token classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class used to create and maintain a single variable token. This token is used as a representation of a variable and
 * stores its value.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TokenVariable extends Token {
	
	private String name;
	
	/**
	 * Class constructor. Constructor accepts a single string value and stores it.
	 * 
	 * @param value the value that will be stored in the token
	 */
	public TokenVariable(String name) {
		this.name = name;
	}
	
	@Override
	public String getValue() {
		return name;
	}
	
	/**
	 * @see hr.fer.zemris.java.custom.scripting.tokens.Token#asText()
	 */
	@Override
	public String asText() {
		return name;
	}
}
