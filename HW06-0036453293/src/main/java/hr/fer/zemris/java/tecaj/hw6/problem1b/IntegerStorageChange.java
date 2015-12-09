/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to hold the info about the object that has changed. Method also notifies all of
 * the registered observers.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class IntegerStorageChange {
	
	private IntegerStorage istorage;
	private int oldValue;
	private int newValue;
	
	/**
	 * CLass constructor. Constructor takes 3 arguments, integer storage, old value and a new value.
	 * 
	 * @param istorage given integer storage
	 * @param oldValue old value of the changing object
	 * @param newValue new value of the changing object
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
		// Notify all registered observers
		List<IntegerStorageObserver> observers = istorage.getObservers();
		if(observers != null) {
			for(IntegerStorageObserver observer : new ArrayList<>(observers)) {
				observer.valueChanged(this);
			}
		}
	}

	/**
	 * Getter for integer storage the current instance represents.
	 * 
	 * @return returns the integer storage it represents.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Getter for the old value of the integer storage.
	 * 
	 * @return returns the old value of the integer storage
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for the new value of the integer storage.
	 * 
	 * @return returns the new changed value of the integer storage
	 */
	public int getNewValue() {
		return newValue;
	}
}
