package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate the logarithm with base 10 of the number or 10^X.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LogStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the logarithm with base 10 of the number or 10^X, depending on the flag inverted.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return Math.log10(currentNumber);
		} else {
			return Math.pow(10, currentNumber);
		}
	}
}
