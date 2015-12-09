package hr.fer.zemris.java.gui.calc.Calculator.listener;

import hr.fer.zemris.java.gui.calc.Calculator.Calculator;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.EqualsOperatorStrategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CLass used as a listener for "=" button. Class implements ActionListener interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class EqualsOperatorListener implements ActionListener {
	
	private EqualsOperatorStrategy strategy;
	private Calculator calculator;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, strategy that will process the info and the
	 * reference to the original calculator.
	 * 
	 * @param strategy strategy that will process the info
	 * @param calc reference to the original calculator
	 */
	public EqualsOperatorListener(EqualsOperatorStrategy strategy, Calculator calc) {
		this.strategy = strategy;
		this.calculator = calc;
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Method that is called when the "=" button is pressed. Method calculates the latest
	 * value using the last saved operator and previews it in the label.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(calculator.getBlocked()) {
			return;
		}
		calculator.setCurrentNumber(strategy.calculate(calculator.savedNumber, 
				Double.parseDouble(calculator.getLabel().getText()), calculator.getLastResultOperator(), 
				calculator.getInverted()));
		if(calculator.getCurrentNumber() == Math.floor(calculator.getCurrentNumber())) {
			   calculator.getLabel().setText(String.valueOf((int) calculator.getCurrentNumber()));
		} else {
			calculator.getLabel().setText(String.valueOf(calculator.getCurrentNumber()));
		}
		calculator.setLastResultOperator(null);
		calculator.savedNumber = calculator.getCurrentNumber();
	}
}
