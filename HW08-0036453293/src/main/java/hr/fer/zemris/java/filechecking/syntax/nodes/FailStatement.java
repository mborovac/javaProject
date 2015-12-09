/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingSyntaxException;

/**
 * Class used to represent a fail statement during the parsing and execution of the file checking program.
 * Statement that is false by default, unless it's inverted.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FailStatement extends CheckingStatement {
	
	private String errorMessage;
	private List<FilecheckingNode> blockStatements;
	private boolean inverted;
	
	/**
	 * Class constructor. Constructor takes 3 arguments: possible error message if the statement is 
	 * false, possible list of statements that will be executed if the statement is true and a flag 
	 * representing whether the statement is inverted.
	 * 
	 * @param errorMessage possible error message if the statement is false
	 * @param blockStatements possible list of statements that will be executed if the statement is true
	 * @param inverted a flag representing whether the statement is inverted
	 */
	public FailStatement(String errorMessage, List<FilecheckingNode> blockStatements, boolean inverted) {
		if(errorMessage != null && errorMessage.isEmpty()) {
			throw new FilecheckingSyntaxException("Error message can not be empty!");
		}
		this.errorMessage = errorMessage;
		this.blockStatements = new ArrayList<>(blockStatements);
		this.inverted = inverted;
	}
	
	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.nodes.CheckingStatement#getErrorMessage()
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.nodes.CheckingStatement#getBlockStatements()
	 */
	public List<FilecheckingNode> getBlockStatements() {
		return new ArrayList<>(this.blockStatements);
	}
	
	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.nodes.CheckingStatement#isInverted()
	 */
	public boolean isInverted() {
		return this.inverted;
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
