package hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.JLabel;

/**
 * Class used to process the number buttons.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Number {
	
private int value;
	
	/**
	 * Class constructor.
	 * 
	 * @param value the value of the pressed button
	 */
	public Number(int value) {
		this.value = value;
	}
	
	/**
	 * Method used to add the value of the pressed button to the existing value and preview it in the label
	 * space. This method is called when the user types in numbers using the number buttons.
	 * 
	 * @param label label where the number will be previewed
	 */
	public void addNumber(JLabel label) {
		String text = label.getText();
		boolean negative = false;
		if(text.startsWith("-")) {
			negative = true;
			text = text.substring(1);
		}
		text += value;
		if(negative) {
			text = "-" + text;
		}
		if(Double.parseDouble(text) == Math.floor(Double.parseDouble(text))) {
			   label.setText(String.valueOf((int) Double.parseDouble(text)));
		} else {
			label.setText(String.valueOf(Double.parseDouble(text)));
		}
	}
}
