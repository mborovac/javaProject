/**
 * Package holding all the main file checking classes for Java 8th homework.
 */
package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.lexical.FilecheckingTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to parse and check the given program representing the set of rules.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FCProgramChecker {
	
	private String program;
	private List<String> errors;
	
	/**
	 * Class constructor. Constructor takes one argument, a string representing the given program.
	 * 
	 * @param program the string representing the given program, can not be null or empty
	 */
	public FCProgramChecker(String program) {
		if(program == null || program.isEmpty()) {
			throw new IllegalArgumentException("program can not be null or empty.");
		}
		this.program = program;
		this.errors = new ArrayList<>();
		checkProgram();
	}
	
	/**
	 * Method used to check whether there have been any errors during the parsing or tokenization.
	 * 
	 * @return returns true if there have been errors, false otherwise
	 */
	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}
	
	/**
	 * Method used to retrieve the list of errors that occurred during the parsing or tokenization.
	 * 
	 * @return returns the list of errors that occurred
	 */
	public List<String> errors() {
		return this.errors;
	}
	
	/**
	 * Method used to execute the program checker.
	 * Method tokenizes and parses the given program checking for errors.
	 */
	private void checkProgram() {
		try {
			FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
			FilecheckingParser parser = new FilecheckingParser(tokenizer);
			parser.getProgramNode();
		} catch (FilecheckingException e) {
			errors.add(e.getMessage());
		}
	}
}
