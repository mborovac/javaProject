package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate a sinus or arcus sinus of a number.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CosStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the sinus or arcus sinus of a number in radians.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return Math.cos(currentNumber);
		} else {
			return Math.acos(currentNumber);
		}
	}
}
