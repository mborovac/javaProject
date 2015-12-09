/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to implement the root node of the program.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ProgramNode extends FilecheckingNode {
	
	private List<FilecheckingNode> statements;
	
	/**
	 * Class constructor. Constructor takes 1 argument, a list of statements to be executed.
	 * 
	 * @param statements list of program statements
	 */
	public ProgramNode(List<FilecheckingNode> statements) {
		this.statements = new ArrayList<>(statements);
	}

	/**
	 * Method used to retrieve the program statements.
	 * 
	 * @return returns the list of program statements given in the constructor
	 */
	public List<FilecheckingNode> getStatements() {
		return new ArrayList<>(statements);
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
