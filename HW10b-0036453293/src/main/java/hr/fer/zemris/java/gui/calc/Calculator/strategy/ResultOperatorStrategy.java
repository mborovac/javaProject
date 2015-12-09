package hr.fer.zemris.java.gui.calc.Calculator.strategy;

/**
 * Interface used to define the only method used in the processing of the button representing 
 * an operator that uses two operands.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface ResultOperatorStrategy {
	
	/**
	 * Method used to calculate and display the value after the button representing an operator that uses
	 * two operands has been called.
	 * 
	 * @param savedNumber 1st operand
	 * @param currentNumber 2nd operand
	 * @param inverted flag used to determine whether the operator should be inverted
	 * @return returns the value calculated by the use of the operator that fits the button that
	 * called the method
	 */
	double calculateResult(double savedNumber, double currentNumber, boolean inverted);
}
