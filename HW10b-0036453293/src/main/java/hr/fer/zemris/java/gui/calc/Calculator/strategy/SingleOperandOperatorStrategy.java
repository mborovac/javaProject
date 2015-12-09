package hr.fer.zemris.java.gui.calc.Calculator.strategy;

/**
 * Interface used to define the only method used in the processing of the button representing 
 * an operator that uses a single operand.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface SingleOperandOperatorStrategy {
	
	/**
	 * Method used to calculate and display the value after the button representing an operator that uses
	 * a single operand has been called.
	 * 
	 * @param currentNumber the only operand
	 * @param inverted flag used to determine whether the operator should be inverted
	 * @return returns the value calculated by the use of the operator that fits the button that
	 * called the method
	 */
	double calculate(double currentNumber, boolean inverted);
}
