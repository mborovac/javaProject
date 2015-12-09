/**
 * Package holding all the node classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class used to create and maintain a text node. Text node is used in document parsing as a representation
 * of simple text.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Class constructor. Constructor accepts a single string which the node will represent.
	 * 
	 * @param text the string the node represents.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Getter method for the private variable text.
	 * 
	 * @return returns a single string the node is holding
	 */
	public String getText() {
		return text;
	}
}
