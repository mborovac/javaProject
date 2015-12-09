package hr.fer.zemris.java.gui.calc.Calculator.listener;

import hr.fer.zemris.java.gui.calc.Calculator.Calculator;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CLass used as a listener for buttons that represent any operator that uses two operands. Class implements 
 * ActionListener interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ResultOperatorListener implements ActionListener {
	
	private ResultOperatorStrategy strategy;
	private Calculator calculator;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, strategy that will process the info and the
	 * reference to the original calculator.
	 * 
	 * @param strategy strategy that will process the info
	 * @param calc reference to the original calculator
	 */
	public ResultOperatorListener(ResultOperatorStrategy strategy, Calculator calc) {
		this.strategy = strategy;
		this.calculator = calc;
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Method that is called when the operator button is pressed. Method calculates the
	 * value using the last saved operator, previews it in the label and sets the called
	 * operator as the last saved operator.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(calculator.getBlocked()) {
			return;
		}
		if(calculator.getLastResultOperator() != null) {
			calculator.setCurrentNumber(calculator.getLastResultOperator().calculateResult(calculator.savedNumber, 
				Double.parseDouble(calculator.getLabel().getText()), calculator.getInverted()));
		}
		calculator.setLastResultOperator(strategy);
		calculator.savedNumber = calculator.getCurrentNumber();
		calculator.getLabel().setText("0");
	}
}
