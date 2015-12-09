package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy;

/**
 * Strategy used to calculate X^n.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ExpStrategy implements ResultOperatorStrategy {

	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy#calculateResult(double, double, boolean)
	 * 
	 * Calculates X^n or n-th root of X, depending on the inverted flag.
	 */
	@Override
	public double calculateResult(double savedNumber, double currentNumber,
			boolean inverted) {
		if(!inverted) {
			return Math.pow(savedNumber, currentNumber);
		} else {
			return Math.pow(savedNumber, 1.0/currentNumber);
		}
	}
}
