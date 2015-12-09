package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface defining methods needed to implement s node visitor.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface INodeVisitor {
	
	/**
	 * Method used to visit a text node.
	 * 
	 * @param node text node to visit
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method used to visit a for loop node.
	 * 
	 * @param node for loop node to visit
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method used to visit an echo node.
	 * 
	 * @param node echo node to visit
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method used to visit a document node.
	 * 
	 * @param node document node to visit
	 */
	public void visitDocumentNode(DocumentNode node);
}
