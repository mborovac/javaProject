/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1a;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used as a Subject in the Observer design pattern. Every class instance has an internal list of
 * registered observers and it notifies all of them once the value of the instance has changed.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class IntegerStorage {
	
	private int value;
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Class constructor. Constructor takes a single integer, the initial value of the storage.
	 * 
	 * @param initialValue the initial value of the storage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Method adds the given observer to the internal list of observers if it's not already there
	 * 
	 * @param observer the observer to be removed
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observer == null) {
			throw new IllegalArgumentException("Given observer can not be null.");
		}
		if(observers == null) {
			observers = new ArrayList<>();
		}
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Method removes the given observer from the internal list of observers. Method does nothing
	 * if the observer is not in the list.
	 * 
	 * @param observer the observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observer == null) {
			throw new IllegalArgumentException("Given observer can not be null.");
		}
		if(observers != null) {
			observers.remove(observer);
		}
	}
	
	/**
	 * Method removes all of the observers from the internal list of observers.
	 */
	public void clearObservers() {
		if(observers != null) {
			observers.clear();
		}
	}
	
	/**
	 * Method for acquiring the current value of the storage.
	 * 
	 * @return returns the integer representation of the value of the storage
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Method sets the value of the storage to the given value. If the new value is different from the old
	 * one, all the observers from the list of registered observers are notified.
	 * 
	 * @param value the new value of the storage
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers != null) {
				for(IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
