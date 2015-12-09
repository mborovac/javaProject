package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate a cosinus or arcus cosinus of a number.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SinStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the cosinus or arcus cosinus of a number in radians.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return Math.sin(currentNumber);
		} else {
			return Math.asin(currentNumber);
		}
	}
}
