/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Interface defining the only method needed to implement an integer storage observer.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method is called when the value of the observed subject has changed. It is used to signal the
	 * observers that they have to do some work specific to them.
	 * 
	 * @param istorageChange the class holding all the info about the object whose value has changed
	 */
	void valueChanged(IntegerStorageChange istorageChange);
}
