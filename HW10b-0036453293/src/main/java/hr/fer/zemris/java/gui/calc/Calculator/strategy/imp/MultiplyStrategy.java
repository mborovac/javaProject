package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy;


/**
 * Strategy used to multiply two numbers.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MultiplyStrategy implements ResultOperatorStrategy {

	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy#calculateResult(double, 
	 * double, boolean)
	 * Multiplies two numbers.
	 */
	@Override
	public double calculateResult(double savedNumber, double currentNumber, boolean inverted) {
		return savedNumber * currentNumber;
	}
}
