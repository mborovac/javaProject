/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1a;

import java.nio.file.Paths;

/**
 * Class used to test the Observer design pattern implemented in Java 6th homework.
 */
public class ObserverExample {
	
	/**
	 * Main method used to execute the program. Method takes no arguments, all given arguments will
	 * be ignored.
	 * 
	 * @param args command line arguments 
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		istorage.addObserver(new LogValue(Paths.get("./log.txt")));
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
