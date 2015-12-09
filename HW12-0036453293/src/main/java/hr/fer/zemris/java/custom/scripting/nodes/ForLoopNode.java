/**
 * Package holding all the node classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Class used to create and maintain a for loop node. For loop node is used in document parsing as a representation of
 * "FOR"-tag. It holds four tokens: single TokenVariable and three Tokens. 
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ForLoopNode extends Node {
	
	private TokenVariable variable;
	private TokenConstantInteger startExpression;
	private TokenConstantInteger endExpression;
	private TokenConstantInteger stepExpression;
	
	/**
	 * Class constructor. Constructor takes 4 arguments, 1st of which has to be a TokenVariable, the rest can be any
	 * type of Token.
	 * 
	 * @param variable variable in the FOR loop
	 * @param startExpression the starting expression of the FOR loop
	 * @param endExpression the end expression of the FOR loop
	 * @param stepExpression the step expression of the FOR loop
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		
		this.variable = variable;
		this.startExpression = (TokenConstantInteger) startExpression;
		this.endExpression = (TokenConstantInteger) endExpression;
		this.stepExpression = (TokenConstantInteger) stepExpression;
	}
	
	/**
	 * Class constructor. Constructor takes 3 arguments, 1st of which has to be a TokenVariable, the rest can be any
	 * type of Token.
	 * 
	 * @param variable variable in the FOR loop
	 * @param startExpression the starting expression of the FOR loop
	 * @param endExpression the end expression of the FOR loop
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Getter method for the private variable variable.
	 * 
	 * @return returns single TokenVariable
	 */
	public TokenVariable getVariable() {
		return variable;
	}

	/**
	 * Getter method for the private variable startExpression.
	 * 
	 * @return returns single Token
	 */
	public Token getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Getter method for the private variable endExpression.
	 * 
	 * @return returns single Token
	 */
	public Token getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Getter method for the private variable stepExpression.
	 * 
	 * @return returns single Token
	 */
	public Token getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
