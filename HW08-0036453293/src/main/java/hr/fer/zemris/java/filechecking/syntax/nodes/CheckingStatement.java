/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.List;

/**
 * Class used to implement a node representing all the methods that do some kind of
 * checking.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class CheckingStatement extends FilecheckingNode {
	
	/**
	 * Method used to retrieve the statement's error message.
	 */
	public abstract String getErrorMessage();
	
	/**
	 * Method used to retrieve the statements from the current statement's statement block.
	 */
	public abstract List<FilecheckingNode> getBlockStatements();
	
	/**
	 * Method used to check whether the statement is inverted.
	 */
	public abstract boolean isInverted();
}
