/**
 * Package containing classes used in syntax analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilenameStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateStatement;

/**
 * Interface used to define all the visit methods used in file checking.
 * One visit method is defined for each possible node.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface FilecheckingNodeVisitor {
	
	/**
	 * Execution of the "def" statement ({@link DefStatement}).
	 * @param stmt statement
	 */
	boolean visit(DefStatement stmt);
	/**
	 * Execution of the "exists" statement ({@link ExistsStatement}).
	 * @param stmt statement
	 */
	boolean visit(ExistsStatement stmt);
	/**
	 * Execution of the "fail" statement ({@link FailStatement}).
	 * @param stmt statement
	 */
	boolean visit(FailStatement stmt);
	/**
	 * Execution of the "filename" statement ({@link FilenameStatement}).
	 * @param stmt statement
	 */
	boolean visit(FilenameStatement stmt);
	/**
	 * Execution of the "format" statement ({@link FormatStatement}).
	 * @param stmt statement
	 */
	boolean visit(FormatStatement stmt);
	/**
	 * Execution of the "terminate" statement" ({@link TerminateStatement}).
	 * @param stmt statement
	 */
	boolean visit(TerminateStatement stmt);
	/**
	 * Execution of the list of statements ({@link ProgramNode}).
	 * @param node list of statements
	 * @return 
	 */
	boolean visit(ProgramNode node);
}
