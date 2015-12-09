/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Class used as an Observer which counts the number of value changes of the observed Subject
 * since the registration.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	private int counter;
	
	/**
	 * Class constructor. Sets the value of the counter to 0.
	 */
	public ChangeCounter() {
		this.counter = 0;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver#valueChanged(
	 * hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage)
	 * 
	 * Method counts the number of Subject's value changes since the registration and prints the counter.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		if(istorageChange == null) {
			throw new IllegalArgumentException("Given storageChange can not be null.");
		}
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}
}
