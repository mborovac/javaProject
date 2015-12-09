/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingSyntaxException;

/**
 * Class used to represent an exists statement during the parsing and execution of the file checking program.
 * Statement checks whether the given file or directory exists in the given archive.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ExistsStatement extends CheckingStatement {
	
	private String type;
	private String path;
	private String errorMessage;
	private List<FilecheckingNode> blockStatements;
	private boolean inverted;
	private static String[] acceptableFiles = new String[] {"f", "fi", "fil", "file"};
	private static String[] acceptableDirectories = new String[] {"d", "di", "dir"};
	
	/**
	 * Class constructor. Constructor takes 5 arguments: type of the file, path to the file, possible
	 * error message if the statement is false, possible list of statements that will be executed if the
	 * statement is true and a flag representing whether the statement is inverted.
	 * 
	 * @param type type of the file
	 * @param path path to the file
	 * @param errorMessage possible error message if the statement is false
	 * @param blockStatements possible list of statements that will be executed if the statement is true
	 * @param inverted a flag representing whether the statement is inverted
	 */
	public ExistsStatement(String type, String path, String errorMessage, List<FilecheckingNode> blockStatements, 
			boolean inverted) {
		if(type == null || type.isEmpty()) {
			throw new FilecheckingSyntaxException("Type can not be null or empty.");
		}
		if(path == null || path.isEmpty()) {
			throw new FilecheckingSyntaxException("Path can not be null or empty.");
		}
		if(errorMessage != null && errorMessage.isEmpty()) {
			throw new FilecheckingSyntaxException("Error message can not be empty.");
		}
		boolean acceptedFile = false;
		for(String acceptableType: acceptableFiles) {
			if(type.equalsIgnoreCase(acceptableType)) {
				acceptedFile = true;
			}
		}
		boolean acceptedDirectory = false;
		for(String acceptableType: acceptableDirectories) {
			if(type.equalsIgnoreCase(acceptableType)) {
				acceptedDirectory = true;
			}
		}
		if(!acceptedFile && !acceptedDirectory) {
			throw new FilecheckingSyntaxException("Unsupported file type: " + type);
		}
		if(acceptedFile) {
			this.type = acceptableFiles[acceptableFiles.length - 1]; 				// type = file
		} else if(acceptedDirectory) {
			this.type = acceptableDirectories[acceptableDirectories.length - 1];	// type = dir
		}
		this.path = path;
		this.errorMessage = errorMessage;
		this.blockStatements = new ArrayList<FilecheckingNode>(blockStatements);
		this.inverted = inverted;
	}
	
	/**
	 * Method used to retrieve the type of the variable that is to be defined.
	 * 
	 * @return returns the string containing the variable type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Method used to retrieve the path to the variable that is to be defined.
	 * 
	 * @return returns the string containing the path to the variable
	 */
	public String getPath() {
		return this.path;
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
