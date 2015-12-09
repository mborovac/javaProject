package hr.fer.zemris.java.gui.layouts.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * Class used to demonstrate the CalcLayout.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class DemoProzor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor.
	 */
	public DemoProzor() {
		initGUI();
	}
	
	/**
	 * Method used to set the graphic user interface. It adds elements to the original container at 
	 * the specified positions.
	 */
	private void initGUI() {
		setLocation(200, 100);
		setPreferredSize(new Dimension(600,500));
		setMinimumSize(new Dimension(400,300));
		setTitle("Demo za CalcLayout.");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		
		// Adding the elements
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));
		p.add(new JLabel("b"), "5,5");
		for(int i = 0; i < p.getComponentCount(); i++) {
			JLabel component = (JLabel) p.getComponent(i);
			component.setHorizontalAlignment(SwingConstants.CENTER);
			component.setBackground(Color.decode("#819ECE"));
			component.setBorder(new LineBorder(Color.BLUE, 1));
			component.setOpaque(true);
		}
		
		// Setting the border
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Adding the panel
		getContentPane().add(p);
		
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
				new DemoProzor().setVisible(true);
			}
		});
	}
}
