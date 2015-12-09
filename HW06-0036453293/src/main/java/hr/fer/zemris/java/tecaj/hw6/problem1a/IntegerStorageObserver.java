/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1a;

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
	 * @param istorage the subject being observed whose value has changed
	 */
	void valueChanged(IntegerStorage istorage);
}
