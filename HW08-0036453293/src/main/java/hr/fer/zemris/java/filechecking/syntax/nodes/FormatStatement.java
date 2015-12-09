/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a format statement during the parsing and execution of the file checking program.
 * Statement checks whether the given file is of the requested format.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FormatStatement extends CheckingStatement {
	
	private String format;
	private String errorMessage;
	private List<FilecheckingNode> blockStatements;
	private boolean inverted;
	
	/**
	 * Class constructor. Constructor takes 4 arguments: required format, possible
	 * error message if the statement is false, possible list of statements that will be executed if the
	 * statement is true and a flag representing whether the statement is inverted.
	 * 
	 * @param format required format
	 * @param errorMessage possible error message if the statement is false
	 * @param blockStatements possible list of statements that will be executed if the statement is true
	 * @param inverted a flag representing whether the statement is inverted
	 */
	public FormatStatement(String format, String errorMessage, List<FilecheckingNode> blockStatements, 
			boolean inverted) {
		if(format == null || format.isEmpty()) {
			throw new FilecheckingSyntaxException("Format can not be null or empty!");
		}
		if(errorMessage != null && errorMessage.isEmpty()) {
			throw new FilecheckingSyntaxException("Error message can not be empty.");
		}
		this.format = format;
		this.errorMessage = errorMessage;
		this.blockStatements = new ArrayList<>(blockStatements);
		this.inverted = inverted;
	}
	
	/**
	 * Method used to retrieve the required format.
	 * 
	 * @return returns the string containing the required format
	 */
	public String getFormat() {
		return this.format;
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
