/**
 * Package holding all the collection classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Class used to create and maintain a stack.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ObjectStack {
	
	private ArrayBackedIndexedCollection stack;
	
	/**
	 * Class constructor. Creates a new object of the class ObjectStack.
	 * 
	 */
	public ObjectStack() {
		stack = new ArrayBackedIndexedCollection();
	}
	
	/**
	 * Method used to determine whether the stack is empty. 
	 * 
	 * @return returns true if the stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		if(stack.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method used to determine the size of the stack.
	 * 
	 * @return returns number of the elements on the stack.
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Method for adding a new element to the stack. The new element is added to the top of the stack.
	 * 
	 * @param value the element that is to be added to the stack, can not be null
	 */
	public void push(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value of the new element can not be null");
		} else {
			stack.add(value);
		}
	}
	
	/**
	 * Method for retrieving the top element of the stack. The element is removed from the stack.
	 * 
	 * @return returns the element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object pop() {
		if(this.isEmpty()) {
			throw new EmptyStackException();
		} else {
			Object element = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return element;
		}
	}
	
	/**
	 * Method for retrieving the top element of the stack. The element is not removed from the stack.
	 * 
	 * @return returns the element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException();
		} else {
			return stack.get(stack.size() - 1);
		}		
	}
	
	/**
	 * Method for removing all the elements from the stack.
	 * 
	 */
	public void clear() {
		stack.clear();
	}
}
