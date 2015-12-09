package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate the natural logarithm of the number or e^X.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LnStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the natural logarithm of the number or e^X, depending on the flag inverted.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return Math.log(currentNumber);
		} else {
			return Math.pow(Math.E, currentNumber);
		}
	}
}
