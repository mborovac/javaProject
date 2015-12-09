/**
 * Package holding all the node classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Class used to create and maintain a single node.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public abstract class Node {
	
	ArrayBackedIndexedCollection children;
	
	/**
	 * Method for adding a child node to the current node.
	 * 
	 * @param child a child that is to be added, can not be null
	 * @throws illegal argument exception is the given child is null
	 */
	public void addChildNode(Node child) {
		if(child == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else if(children == null) {
			children = new ArrayBackedIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Method Method used to determine the number of the children of this node.
	 * 
	 * @return returns the number of children
	 */
	public int numberOfChildren() {
		if(children == null) {
			return 0;
		} else if(children.isEmpty()) {
			throw new IndexOutOfBoundsException("There are no children nodes to access");
		} else {
			return children.size();
		}
	}
	
	/**
	 * Method for retrieving a child of the node from the given position. Does not remove the child.
	 * 
	 * @param index the position from which the child is to be retrieved, integer greater than 0 and not greater than
	 * the number of children
	 * @return returns the child node from the given position
	 */
	public Node getChild(int index) {
		if(index < 0 || index >= children.size()) {
			throw new IndexOutOfBoundsException();
		}
		return (Node) children.get(index);
	}
	
	public abstract void accept(INodeVisitor visitor);
}
