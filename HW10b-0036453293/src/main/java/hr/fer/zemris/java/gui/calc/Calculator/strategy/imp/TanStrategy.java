package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate a tangens or arcus tangens of a number.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TanStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the tangens or arcus tangens of a number in radians.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return Math.tan(currentNumber);
		} else {
			return Math.atan(currentNumber);
		}
	}
}
