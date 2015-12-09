package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

/**
 * Strategy used to calculate a cotangens or arcus cotangens of a number.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CtgStrategy implements SingleOperandOperatorStrategy {
	
	/**
	 * @see hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy#calculate(double, boolean)
	 * 
	 * Calculates the cotangens or arcus cotangens of a number in radians.
	 */
	public double calculate(double currentNumber, boolean inverted) {
		if(!inverted) {
			return 1.0/Math.tan(currentNumber);
		} else {
			return 1.0/Math.atan(currentNumber);
		}
	}
}
