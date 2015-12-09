/**
 * Package containing syntax node visitor classes for Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingSyntaxException;
import hr.fer.zemris.java.filechecking.syntax.nodes.CheckingStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilecheckingNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilenameStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.formatchecking.FileOpener;

/**
 * Class used to implement a visitor class for instances of the FilecheckingNode class.
 * Class implements a single visit method for every instance of the FilecheckingNode class.
 * Class implements the FilecheckingNodeVisitor interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ProgramExecutorVisitor implements FilecheckingNodeVisitor {
	
	private Map<String, Object> initialData;
	private File file;
	private String fileName;
	private Map<String, Object> definedVariables;
	private List<String> errors;
	
	/**
	 * Class constructor. Takes 3 arguments: map of predefined variables, a file being checked and
	 * the original file name.
	 * 
	 * @param initialData map of predefined variables
	 * @param file file being checked
	 * @param fileName the original file name
	 */
	public ProgramExecutorVisitor(Map<String, Object> initialData, File file, String fileName) {
		if(initialData == null) {
			throw new IllegalArgumentException("Map of initial data can not be null.");
		}
		if(file == null) {
			throw new IllegalArgumentException("File can not be null.");
		}
		if(fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException("File name can not be null or empty.");
		}
		this.initialData = initialData;
		this.file = file;
		this.fileName = resolveVariable(fileName);
		definedVariables = new HashMap<>();
		this.errors = new ArrayList<>();
	}
	
	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.DefStatement)
	 * 
	 * Method used to execute an instance of the DefStatement.
	 * Method puts the variable and its value in to the map of defined variables.
	 * @return returns true
	 */
	@Override
	public boolean visit(DefStatement stmt) {
		String variableName = stmt.getVariableName();
		String variableValue = resolvePackage(resolveVariable(stmt.getVariableValue()));
		definedVariables.put(variableName, variableValue);
		return true;
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.ExistsStatement)
	 * 
	 * Method checks whether the current file contains the selected file.
	 * Statement can be inverted.
	 * Method executes the statements in the current statement's statement block if 
	 * the original check is true.
	 * Method writes the error message to the internal log if the original check is false.
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	@Override
	public boolean visit(ExistsStatement stmt) {
		boolean success = false;
		String path = resolvePackage(resolveVariable(stmt.getPath()));
		if(stmt.getType() == "dir") {
			if(!path.endsWith("/")) {
				path += "/";
			}
		}
		List<String> listOfFiles = new FileOpener(this.file, stmt.getType()).useAdequateOpener();
		if(listOfFiles != null) {
			for(String fileName: listOfFiles) {
				if(fileName.compareTo(path) == 0) {
					success = true;
				}
			}
		}
		if(stmt.isInverted()) {
			success = !success;
		}
		String genericMessage;
		if(!stmt.isInverted()) {
			if(stmt.getType() == "dir") {
				genericMessage = "The directory " + path + " does not exist.";
			} else {
				genericMessage = "The file " + path + " does not exist.";
			}
		} else {
			if(stmt.getType() == "dir") {
				genericMessage = "The directory " + path + " exists.";
			} else {
				genericMessage = "The file " + path + " exists.";
			}
		}
		return afterStatement(stmt, success, genericMessage);
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.FailStatement)
	 * 
	 * Method executing a statement that has failed by default.
	 * Statement can be inverted.
	 * Method executes the statements in the current statement's statement block if 
	 * the original check is true.
	 * Method writes the error message to the internal log if the original check is false.
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	@Override
	public boolean visit(FailStatement stmt) {
		boolean success = false;
		if(stmt.isInverted()) {
			success = !success;
		}
		String genericMessage = "Generic fail statement message.";
		return afterStatement(stmt, success, genericMessage);
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.FilenameStatement)
	 * 
	 * Method checks whether the original file name is the same as the required name.
	 * The check can be case sensitive or in-sensitive.
	 * Statement can be inverted.
	 * Method executes the statements in the current statement's statement block if 
	 * the original check is true.
	 * Method writes the error message in to the internal log if the original check is false.
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	@Override
	public boolean visit(FilenameStatement stmt) {
		boolean success = false;
		boolean caseInsensitive = !stmt.getCaseSesitive();
		String requestedFilename = resolveVariable(stmt.getExpectedFileName());
		String actualName = this.fileName;
		if(caseInsensitive) {
			if(requestedFilename.compareToIgnoreCase(actualName) == 0) {
				success = true;
			}
		} else {
			if(requestedFilename.compareTo(actualName) == 0) {
				success = true;
			}
		}
		if(stmt.isInverted()) {
			success = !success;
		}
		String genericMessage = "File name does not match the required file name. Requested: " 
					+ requestedFilename + " Actual name: " + actualName;
		return afterStatement(stmt, success, genericMessage);
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.FormatStatement)
	 * 
	 * Method checks whether the original file is in the required format.
	 * Statement can be inverted.
	 * Method executes the statements in the current statement's statement block if 
	 * the original check is true.
	 * Method writes the error message in to the internal log if the original check is false.
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	@Override
	public boolean visit(FormatStatement stmt) {
		String requestedFormat = stmt.getFormat();
		FileOpener fileOpener = new FileOpener(this.file, null);
		boolean success = fileOpener.detectFormat(requestedFormat);
		if(stmt.isInverted()) {
			success = !success;
		}
		String genericMessage = "File format does not match the required format. Requested: " 
					+ requestedFormat;
		return afterStatement(stmt, success, genericMessage);
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.TerminateStatement)
	 * 
	 * Method terminates the file checking.
	 * @return returns false
	 */
	@Override
	public boolean visit(TerminateStatement stmt) {
		return false;
	}

	/**
	 * @see hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor#visit(
	 * hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode)
	 * 
	 * Method visits the statements in the statement block of the given program node.
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	@Override
	public boolean visit(ProgramNode node) {
		for(FilecheckingNode stmt : node.getStatements()) {
			if(!stmt.accept(this)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method used to resolve a given variable. Variable that will be resolved is preceded by "${" and
	 * followed by "}".
	 * Variable can be resolved only if its value was defined earlier.
	 * 
	 * @param string string that might contain a variable that should be resolved
	 * @return returns a new string containing the resolved variable
	 */
	private String resolveVariable(String string) {
		String newString = string;
		while(newString.contains("$")) {
			int indexOfResolve = newString.indexOf("$");
			if(newString.charAt(indexOfResolve + 1) != '{') {
				throw new FilecheckingSyntaxException("Character '$' must be followed by character '{'");
			}
			int indexOfVariable = indexOfResolve + 2;
			while(!Character.isLetter(newString.charAt(indexOfVariable)) && 
					newString.charAt(indexOfVariable) != '.' &&
							newString.charAt(indexOfVariable) != '_') {
				indexOfVariable++;
			}
			int lengthOfVariable = 1;
			while(Character.isLetterOrDigit(newString.charAt(indexOfVariable + lengthOfVariable)) || 
					newString.charAt(indexOfVariable + lengthOfVariable) == '.' ||
							newString.charAt(indexOfVariable + lengthOfVariable) == '_') {
				lengthOfVariable++;
			}
			int indexOfResolveEnd = indexOfVariable + lengthOfVariable;
			while(newString.charAt(indexOfResolveEnd) != '}') {
				indexOfResolveEnd++;
			}
			String variable = newString.substring(indexOfVariable, indexOfVariable + lengthOfVariable);
			if(!initialData.containsKey(variable) && !definedVariables.containsKey(variable)) {
				System.out.println("Internal error: variable "+ variable + " is not defined.");
				System.exit(-1);
			}
			StringBuilder sb = new StringBuilder();
			sb.append(newString.subSequence(0, indexOfResolve));
			if(initialData.containsKey(variable)) {
				sb.append(initialData.get(variable));
			} else {
				sb.append(definedVariables.get(variable));
			}
			sb.append(newString.substring(indexOfResolveEnd + 1));
			newString = sb.toString();
		}
		return newString;
	}
	
	/**
	 * Method used to resolve a package. String that should be resolved to a package
	 * contains a character ':'. Everything after the colon character is resolved in to 
	 * a package with '/' as a separator.
	 * 
	 * @param oldString string that might contain a package that should be resolved
	 * @return returns a new string containing the resolved package
	 */
	private String resolvePackage(String oldString) {
		String newString;
		if(oldString.contains(":")) {
			newString = oldString.replace(':', '/');
			return newString.replace('.', '/');
		}
		return oldString;
	}
	
	/**
	 * Method used save the error message to the internal log.
	 * 
	 * @param stmt statement that failed the check
	 * @param genericMessage a message that will be saved in the log if no
	 * error message was given with the statement
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	private boolean doIfFalse(CheckingStatement stmt, String genericMessage) {
		String errorMessage = stmt.getErrorMessage();
		if(errorMessage != null && !errorMessage.isEmpty()) {
			this.errors.add(errorMessage);
		} else {
			this.errors.add(genericMessage);
		}
		return true;
	}
	
	/**
	 * Method used to execute the statements in the current statement's statement block
	 * 
	 * @param stmt current statement that passed the check
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	private boolean doIfTrue(CheckingStatement stmt) {
		List<FilecheckingNode> blockStatements = stmt.getBlockStatements();
		if(blockStatements != null && !blockStatements.isEmpty()) {
			for(FilecheckingNode statement: stmt.getBlockStatements()) {
				if(!statement.accept(this)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Method called after the statement succeeded or failed. Method checks whether the statement
	 * succeeded or not and calls the adequate method to continue.
	 * 
	 * @param stmt statement that just finished its check
	 * @param success flag indicating whether the check was a success
	 * @param errorMessage generic error message to be saved in a log if the statements has
	 * no error message of its own
	 * @return returns false if statement terminate was executed, true otherwise
	 */
	private boolean afterStatement(CheckingStatement stmt, boolean success, String errorMessage) {
		if(success) {
			if(!doIfTrue(stmt)) {
				return false;
			}
		} else {
			if(!doIfFalse(stmt, errorMessage)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method used to acquire the error log.
	 * 
	 * @return returns the error log
	 */
	public List<String> getErrors() {
		return this.errors;
	}
}
