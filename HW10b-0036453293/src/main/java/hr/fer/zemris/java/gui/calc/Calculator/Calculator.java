package hr.fer.zemris.java.gui.calc.Calculator;

import hr.fer.zemris.java.gui.calc.Calculator.listener.EqualsOperatorListener;
import hr.fer.zemris.java.gui.calc.Calculator.listener.ResultOperatorListener;
import hr.fer.zemris.java.gui.calc.Calculator.listener.SingleOperandOperatorListener;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.ResultOperatorStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.CosStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.CtgStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.DivideStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.EqualsStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.ExpStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.InverseStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.LnStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.LogStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.MinusStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.MultiplyStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.PlusStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.SinStrategy;
import hr.fer.zemris.java.gui.calc.Calculator.strategy.imp.TanStrategy;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * Class implementing a simple calculator. Calculator only accepts input via clicking the GUI buttons.
 * Keyboard input is not allowed.
 * 
 * Button "+/-" is used to change the current number from positive to negative and back to positive. It can
 * only be used after at least one digit has been typed.
 * 
 * Operators that use two operands will display a number zero after pressing the operator and before selecting
 * the 2nd operand. This is the intended functionality. Pressing "=" will display the correct result.
 *  
 * CheckBox "Inv" is used to select inverted operations. Supported operations are: arcus sin, arcus cos, arc tg,
 * arc ctg, 10^x, e^x and n-th root. CheckBox must be checked when selecting the operation for it to be registered
 * as the inverted operation.
 *  
 * @author MarkoB
 * @version 1.0
 */
public class Calculator extends JFrame {
	
	private boolean inverted;
	private double currentNumber;
	public double savedNumber;
	private List<JButton> numberButtons;;
	private JLabel label;
	private ResultOperatorStrategy lastResultOperator = null;
	private Stack<Double> stack;
	private boolean blocked = false;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor. Constructor takes no arguments.
	 */
	public Calculator() {
		inverted = false;
		currentNumber = 0.0;
		savedNumber = 0.0;
		numberButtons = new ArrayList<>();
		stack = new Stack<Double>();
		initGUI();
	}
	
	/**
	 * Getter for inverted.
	 * 
	 * @return returns inverted
	 */
	public boolean getInverted() {
		return inverted;
	}
	
	public double getCurrentNumber() {
		return this.currentNumber;
	}
	
	public void setCurrentNumber(double value) {
		this.currentNumber = value;
	}
	
	public JLabel getLabel() {
		return this.label;
	}
	
	public ResultOperatorStrategy getLastResultOperator() {
		return this.lastResultOperator;
	}
	
	public void setLastResultOperator(ResultOperatorStrategy strategy) {
		this.lastResultOperator = strategy;
	}
	
	public boolean getBlocked() {
		return this.blocked;
	}
	
	/**
	 * Method used to set the graphic user interface. It is a huge method. It adds elements to the
	 * original container and sets the listener for each component.
	 */
	private void initGUI() {
		setLocation(200, 100);
		setPreferredSize(new Dimension(600,500));
		setMinimumSize(new Dimension(400,300));
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		
		UIManager.put("CheckBox.interiorBackground", Color.ORANGE);
		
		// Adding elements and setting listeners for most of them
		JPanel p = new JPanel(new CalcLayout(7));
		this.label = new JLabel("0", SwingConstants.RIGHT);
		label.setBackground(Color.ORANGE);
		label.setOpaque(true);
		p.add(label, "1,1");
		JButton equalsButton = new JButton("=");
		equalsButton.addActionListener(new EqualsOperatorListener(new EqualsStrategy(), this));
		p.add(equalsButton, "1,6");
		JButton clearButton = new JButton("clr");
		p.add(clearButton, "1,7");
		JButton inverseOperationButton = new JButton("1/x");
		inverseOperationButton.addActionListener(new SingleOperandOperatorListener(new InverseStrategy(), this));
		p.add(inverseOperationButton, "2,1");
		JButton sinButton = new JButton("sin");
		sinButton.addActionListener(new SingleOperandOperatorListener(new SinStrategy(), this));
		p.add(sinButton, "2,2");
		final JButton sevenButton = new JButton("7");
		p.add(sevenButton, "2,3");
		JButton eightButton = new JButton("8");
		p.add(eightButton, "2,4");
		JButton nineButton = new JButton("9");
		p.add(nineButton, "2,5");
		JButton divButton = new JButton("/");
		divButton.addActionListener(new ResultOperatorListener(new DivideStrategy(), this));
		p.add(divButton, "2,6");
		JButton resButton = new JButton("res");
		p.add(resButton, "2,7");
		JButton logButton = new JButton("log");
		logButton.addActionListener(new SingleOperandOperatorListener(new LogStrategy(), this));
		p.add(logButton, "3,1");
		JButton cosButton = new JButton("cos");
		cosButton.addActionListener(new SingleOperandOperatorListener(new CosStrategy(), this));
		p.add(cosButton, "3,2");
		JButton fourButton = new JButton("4");
		p.add(fourButton, "3,3");
		JButton fiveButton = new JButton("5");
		p.add(fiveButton, "3,4");
		JButton sixButton = new JButton("6");
		p.add(sixButton, "3,5");
		JButton mulButton = new JButton("*");
		mulButton.addActionListener(new ResultOperatorListener(new MultiplyStrategy(), this));
		p.add(mulButton, "3,6");
		JButton pushButton = new JButton("push");
		p.add(pushButton, "3,7");
		JButton lnButton = new JButton("ln");
		lnButton.addActionListener(new SingleOperandOperatorListener(new LnStrategy(), this));
		p.add(lnButton, "4,1");
		JButton tanButton = new JButton("tan");
		tanButton.addActionListener(new SingleOperandOperatorListener(new TanStrategy(), this));
		p.add(tanButton, "4,2");
		JButton oneButton = new JButton("1");
		p.add(oneButton, "4,3");
		JButton twoButton = new JButton("2");
		p.add(twoButton, "4,4");
		JButton threeButton = new JButton("3");
		p.add(threeButton, "4,5");
		JButton minusButton = new JButton("-");
		minusButton.addActionListener(new ResultOperatorListener(new MinusStrategy(), this));
		p.add(minusButton, "4,6");
		JButton popButton = new JButton("pop");
		p.add(popButton, "4,7");
		JButton expButton = new JButton("x^n");
		expButton.addActionListener(new ResultOperatorListener(new ExpStrategy(), this));
		p.add(expButton, "5,1");
		JButton ctgButton = new JButton("ctg");
		ctgButton.addActionListener(new SingleOperandOperatorListener(new CtgStrategy(), this));
		p.add(ctgButton, "5,2");
		JButton zeroButton = new JButton("0");
		p.add(zeroButton, "5,3");
		JButton signButton = new JButton("+/-");
		p.add(signButton, "5,4");
		JButton dotButton = new JButton(".");
		p.add(dotButton, "5,5");
		JButton plusButton = new JButton("+");
		plusButton.addActionListener(new ResultOperatorListener(new PlusStrategy(), this));
		p.add(plusButton, "5,6");
		final JCheckBox invBox = new JCheckBox("Inv");
		p.add(invBox, "5,7");
		
		for(int i = 0; i < p.getComponentCount(); i++) {
			Component component = p.getComponent(i);
			if(component instanceof JLabel) {
				((JLabel) component).setBorder(new LineBorder(Color.BLUE));
				continue;
			}
			component.setBackground(Color.decode("#819ECE"));
		}
		
		// Adding number buttons to the list
		numberButtons.add(oneButton);
		numberButtons.add(twoButton);
		numberButtons.add(threeButton);
		numberButtons.add(fourButton);
		numberButtons.add(fiveButton);
		numberButtons.add(sixButton);
		numberButtons.add(sevenButton);
		numberButtons.add(eightButton);
		numberButtons.add(nineButton);
		numberButtons.add(zeroButton);
		
		// Setting listener for the number buttons
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		getContentPane().add(p);
		
		for(JButton button: numberButtons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(blocked) {
						return;
					}
					Number number = new Number(Integer.parseInt(e.getActionCommand()));
					number.addNumber(label);
					currentNumber = Double.parseDouble(label.getText());
				}
			});
		}
		
		// Clear button listener
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentNumber = 0;
				label.setText("0");
				blocked = false;
			}
		});
		
		// Res button listener
		resButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blocked = false;
				savedNumber = 0;
				currentNumber = 0;
				lastResultOperator = null;
				invBox.setSelected(false);
				inverted = false;
				label.setText("0");
			}
		});
		
		// Push button listener
		pushButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blocked) {
					return;
				}
				stack.push(Double.parseDouble(label.getText()));
			}
		});
		
		// Pop button listener
		popButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blocked) {
					return;
				}
				if(stack.isEmpty()) {
					label.setText("Stack is empty.");
					blocked = true;
				}
				currentNumber = stack.pop();
				label.setText(Double.toString(currentNumber));
			}
		});
		
		// Signum button listener
		signButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blocked) {
					return;
				}
				currentNumber = (-1)*currentNumber;
				label.setText(Double.toString(currentNumber));
			}
		});
		
		// Dot button listener
		dotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blocked) {
					return;
				}
				String text = label.getText();
				if(text.contains(".")) {
					throw new IllegalArgumentException("Number already contains a '.'");
				}
				text = text + ".";
				label.setText(text);
			}
		});
		
		// Inverse check box listener
		invBox.addActionListener(new ActionListener() {
			int counter = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blocked) {
					return;
				}
				counter++;
				if((counter % 2) == 0) {
					inverted = false;
				} else {
					inverted = true;
				}
			}
		});
		
		// Window is made displayable, preferred size is calculated
		pack();
	}
	
	/**
	 * Method main used to run the program. Method takes no arguments, all arguments will be ignored.
	 * 
	 * @param args arguments that will be ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Calculator().setVisible(true);
			}
		});
	}
}
