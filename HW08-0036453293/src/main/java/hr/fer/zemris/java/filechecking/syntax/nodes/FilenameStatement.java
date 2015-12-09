/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingSyntaxException;

/**
 * Class used to represent a filename statement during the parsing and execution of the file checking program.
 * Statement checks whether the original file name equals the required one. Depending on the flag caseSensitive, 
 * the comparation can be case sensitive or in-sensitive.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilenameStatement extends CheckingStatement {
	
	private String expectedFileName;
	private boolean caseSensitive;
	private String errorMessage;
	private List<FilecheckingNode> blockStatements;
	private boolean inverted;
	
	/**
	 * Class constructor. Constructor takes 5 arguments: original file name, flag caseSensitive, possible
	 * error message if the statement is false, possible list of statements that will be executed if the
	 * statement is true and a flag representing whether the statement is inverted.
	 * 
	 * @param expectedFileName original file name
	 * @param caseSensitive flag representing whether the names should be checked as case sensitive
	 * @param errorMessage possible error message if the statement is false
	 * @param blockStatements possible list of statements that will be executed if the statement is true
	 * @param inverted a flag representing whether the statement is inverted
	 */
	public FilenameStatement(String expectedFileName, boolean caseSensitive, 
			String errorMessage, List<FilecheckingNode> blockStatements, boolean inverted) {
		if(expectedFileName == null || expectedFileName.isEmpty()) {
			throw new FilecheckingSyntaxException("Expected file name can not be null or empty!");
		}
		this.expectedFileName = expectedFileName;
		this.caseSensitive = caseSensitive;
		if(errorMessage != null && errorMessage.isEmpty()) {
			throw new FilecheckingSyntaxException("Error message can not be empty.");
		}
		this.errorMessage = errorMessage;
		this.blockStatements = new ArrayList<>(blockStatements);
		this.inverted = inverted;
	}
	
	/**
	 * Method used to retrieve the original file name.
	 * 
	 * @return returns the string containing the original file name
	 */
	public String getExpectedFileName() {
		return this.expectedFileName;
	}
	
	/**
	 * Method used to retrieve the case sensitive flag.
	 * 
	 * @return returns the case sensitive flag
	 */
	public boolean getCaseSesitive() {
		return this.caseSensitive;
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
