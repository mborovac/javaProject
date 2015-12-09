package hr.fer.zemris.java.gui.calc.Calculator.strategy;

/**
 * Interface used to define the only method used in the processing of the 
 * equals button.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface EqualsOperatorStrategy {
	
	/**
	 * Method used to calculate and display the value after the "=" button has been called.
	 * 
	 * @param savedNumber 1st operand
	 * @param currentNumber 2nd operand
	 * @param lastResultOperator last saved operator, the one that will be used to calculate the
	 * value
	 * @param inverted flag used to determine whether the operator should be inverted
	 * @return returns the value calculated by the use of the last operator
	 */
	double calculate(double savedNumber, double currentNumber, ResultOperatorStrategy lastResultOperator,
			boolean inverted);
}
