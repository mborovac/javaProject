package hr.fer.zemris.java.gui.calc.Calculator.listener;

import hr.fer.zemris.java.gui.calc.Calculator.Calculator;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.SingleOperandOperatorStrategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CLass used as a listener for buttons that represent any operator that uses a single operand. Class implements 
 * ActionListener interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SingleOperandOperatorListener implements ActionListener {
	
	private SingleOperandOperatorStrategy strategy;
	private Calculator calculator;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, strategy that will process the info and the
	 * reference to the original calculator.
	 * 
	 * @param strategy strategy that will process the info
	 * @param calc reference to the original calculator
	 */
	public SingleOperandOperatorListener(SingleOperandOperatorStrategy strategy, Calculator calc) {
		this.strategy = strategy;
		this.calculator = calc;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Method that is called when the operator button is pressed. Method calculates the
	 * value using the last saved operator and previews it in the label. The calculated value
	 * is displayed immediately and can be used.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(calculator.getBlocked()) {
			return;
		}
		calculator.setCurrentNumber(strategy.calculate(calculator.getCurrentNumber(), calculator.getInverted()));
		if(calculator.getCurrentNumber() == Math.floor(calculator.getCurrentNumber())) {
			   calculator.getLabel().setText(String.valueOf((int) calculator.getCurrentNumber()));
		} else {
			calculator.getLabel().setText(String.valueOf(calculator.getCurrentNumber()));
		}
	}
}
