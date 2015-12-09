package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.EqualsOperatorStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy;

/**
 * Strategy used to calculate and display the result of the last selected operator.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class EqualsStrategy implements EqualsOperatorStrategy {

	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.EqualsOperatorStrategy#calculate(double, 
	 * double, hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy, boolean)
	 * 
	 * Calculates and displays the result of the last selected operator.
	 */
	@Override
	public double calculate(double savedNumber, double currentNumber, ResultOperatorStrategy lastResultOperator, 
			boolean inverted) {
		if(lastResultOperator == null) {
			return savedNumber;
		} else {
			return lastResultOperator.calculateResult(savedNumber, currentNumber, inverted);
		}
	}
}
