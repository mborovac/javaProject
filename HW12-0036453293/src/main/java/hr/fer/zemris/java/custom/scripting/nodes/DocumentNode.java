/**
 * Package holding all the node classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class used to create and maintain a document node. It is a main node of the document and it has no methods of its own.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
