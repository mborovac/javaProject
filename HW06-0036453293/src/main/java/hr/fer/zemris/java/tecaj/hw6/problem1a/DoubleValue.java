/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1a;

/**
 * Class used as an Observer which prints the double value of the new changed value of the observed
 * Subject. Observer also counts the number of changes since the registration and unregisters after
 * the 2nd change.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	
	private static final int TWO = 2;
	private int timesChanged;
	
	/**
	 * Class constructor. Sets the value of the counter to 0.
	 */
	public DoubleValue() {
		this.timesChanged = 0;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver#valueChanged(
	 * hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage)
	 * 
	 * Method increments the counter of changes since the registration, prints the new value of the
	 * Subject multiplied by 2 and if the counter equals 2 it unregisters itself from the Subject.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null) {
			throw new IllegalArgumentException("Given storage can not be null.");
		}
		timesChanged++;
		if(timesChanged == 2) {
			istorage.removeObserver(this);
		}
		System.out.println("Double value: " + istorage.getValue()*TWO);
	}
	
	public void reset() {
		this.timesChanged = 0;
	}
}
