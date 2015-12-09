/**
 * Package holding all the node classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Class used to create and maintain an echo node. Echo node is used in document parsing as a representation of "="-tag.
 * It holds an array of tokens of any kind.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class EchoNode extends Node {
	
	private Token[] tokens;
	
	/**
	 * Class constructor.
	 * 
	 * @param tokens the array of tokens the node will hold.
	 */
	public EchoNode(Token[] tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * Getter for the private variable Token[]
	 * 
	 * @return returns an array of tokens the node is holding.
	 */
	public Token[] getTokens() {
		return tokens;
	}
}
