/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Class used as an Observer which prints the square value of the changed Subject value.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {
	
	/*
	 * No constructor is defined because the default one is enough.
	 */
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver#valueChanged(
	 * hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage)
	 * 
	 * Method prints the square value of the new Subject value.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		if(istorageChange == null) {
			throw new IllegalArgumentException("Given storageChange can not be null.");
		}
		int value = istorageChange.getNewValue();
		System.out.println("Provided new value: " + value + ", square is " + value*value);
	}
}
