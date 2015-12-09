/**
 * Package holding all the main file checking classes for Java 8th homework.
 */
package hr.fer.zemris.java.filechecking;

import hr.fer.zemris.java.filechecking.executors.visitors.ExecutorVisitor;
import hr.fer.zemris.java.filechecking.lexical.FilecheckingTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FilecheckingParser;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class used to verify the given file based on the given rules.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FCFileVerifier {
	
	private File file;
	private String fileName;
	private String program;
	private Map<String, Object> initialData;
	private List<String> errors;

	/**
	 * Class constructor. Constructor takes 5 arguments: file that will be checked, original file name,
	 *  string containing the requirements which the file must fulfill and some predefined variables.
	 * 
	 * @param file file that will be checked
	 * @param fileName original file name
	 * @param program string containing the requirements which the file must fulfill
	 * @param initialData map of predefined variables
	 */
	public FCFileVerifier(File file, String fileName, String program,
			Map<String, Object> initialData) {
		if(file == null) {
			throw new IllegalArgumentException("File can not be null.");
		}
		if(fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException("File name can not be null or empty.");
		}
		if(program == null || program.isEmpty()) {
			throw new IllegalArgumentException("Program can not be null or empty.");
		}
		if(initialData == null) {
			throw new IllegalArgumentException("Initial data can not be null.");
		}
		this.file = file;
		this.fileName = fileName;
		this.program = program;
		this.initialData = initialData;
		this.errors = new ArrayList<>();
		run();
	}
	
	/**
	 * Method used to execute the file verifier.
	 * Method parses the given program and uses it to check the given file.
	 */
	private void run() {
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingParser parser = new FilecheckingParser(tokenizer);
		ProgramNode a = parser.getProgramNode();
		ExecutorVisitor exe = new ExecutorVisitor(a);
		this.errors = exe.execute(this.initialData, this.file, this.fileName);
	}

	/**
	 * Method used to check whether there have been any errors during the checking.
	 * 
	 * @return returns true if there have been errors, false otherwise
	 */
	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

	/**
	 * Method used to retrieve the list of errors that occurred during the file checking.
	 * 
	 * @return returns the list of errors that occurred
	 */
	public List<String> errors() {
		return errors;
	}
}
