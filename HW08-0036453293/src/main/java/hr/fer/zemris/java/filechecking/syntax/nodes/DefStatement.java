/**
 * Package holding all the nodes used in the syntax analysis in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.FilecheckingNodeVisitor;

/**
 * Class used to represent a define statement during the parsing and execution of the file checking program.
 * Statement defines the given variable and sets its value to the given value.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DefStatement extends FilecheckingNode {
	
	private String variableName;
	private String variableValue;
	
	/**
	 * Class constructor. Constructor takes 2 arguments: variable name and variable value.
	 * 
	 * @param variableName name of the new variable
	 * @param variableValue value of the new variable
	 */
	public DefStatement(String variableName, String variableValue) {
		this.variableName = variableName;
		this.variableValue = variableValue;
	}
	
	/**
	 * Method used to retrieve the name of the variable that is to be defined.
	 * 
	 * @return returns the string containing the variable name
	 */
	public String getVariableName() {
		return this.variableName;
	}
	
	/**
	 * Method used to retrieve the value of the variable that is to be defined.
	 * 
	 * @return returns the string containing the variable value
	 */
	public String getVariableValue() {
		return this.variableValue;
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
