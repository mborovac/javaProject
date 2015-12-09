/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;

/**
 * Class used to represent a terminate statement during the parsing and execution of the file checking program.
 * Statement stops the execution of the file checking.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TerminateStatement extends FilecheckingNode {
	
	/**
	 * Class constructor.
	 */
	public TerminateStatement() {
	}
	
	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.nodes.FilecheckingNode#accept(
	 * hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor)
	 * 
	 * Method used to let the visitor know which type the current instance is of
	 */
	@Override
	public boolean accept(FilecheckingNodeVisitor visitor) {
		return visitor.visit(this);
	}
}
