/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;

/**
 * Abstract class defining the method accept needed to implement all of the file checking nodes.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class FilecheckingNode {
	
	public FilecheckingNode() {
	}
	
	/**
	 * Method used to accept the visitor.
	 * 
	 * @param visitor the visitor
	 */
	public abstract boolean accept(FilecheckingNodeVisitor visitor);
}
