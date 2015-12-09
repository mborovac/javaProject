package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate the inverse of the number.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InverseStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the inverse of the number.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		return 1.0/currentNumber;
	}
}
