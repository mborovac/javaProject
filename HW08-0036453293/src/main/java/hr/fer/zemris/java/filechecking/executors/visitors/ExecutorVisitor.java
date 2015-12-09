/**
 * Package holding the main visitor class for file checking of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.executors.visitors;

import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.ProgramExecutorVisitor;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Class used to implement an executor visitor for file checking. Visitor creates a
 * program executor visitor which visits all the nodes created by parsing and executes them.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ExecutorVisitor {

	private ProgramNode programNode;
	
	/**
	 * Class constructor.
	 * 
	 * @param programNode main node of the program that has to be executed
	 */
	public ExecutorVisitor(ProgramNode programNode) {
		this.programNode = programNode;
	}

	/**
	 * Method used to execute the node given in the constructor.
	 * 
	 * @throws VLangExecutionException if an error occurred during the execution
	 */
	public List<String> execute(Map<String, Object> variables, File file, String fileName) {
		ProgramExecutorVisitor exec = new ProgramExecutorVisitor(variables, file, fileName);
		programNode.accept(exec);
		return exec.getErrors();
	}
}
