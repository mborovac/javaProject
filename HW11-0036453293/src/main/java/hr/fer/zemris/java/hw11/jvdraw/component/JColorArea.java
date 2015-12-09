package hr.fer.zemris.java.hw11.jvdraw.component;

import hr.fer.zemris.java.hw11.jvdraw.observers.ColorChangeListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class used to implement a color provider. It is used as a color picker. Once the user clicks
 * on the component a color panel is shown where the user can pick a color. The picked color is set
 * as the current color.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	private static final long serialVersionUID = 1L;
	private Color selectedColor;
	private List<ColorChangeListener> listeners;
	
	/**
	 * Class constructor. Constructor takes one argument, the initial color.
	 * 
	 * @param selectedColor the initial color
	 */
	public JColorArea(Color selectedColor) {
		if(selectedColor == null) {
			throw new IllegalArgumentException("Color can not be null!");
		}
		this.selectedColor = selectedColor;
		listeners = new ArrayList<>();
		this.setOpaque(true);
		
		MouseListener mouseClick = new MouseAdapter() {
			/**
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 * 
			 * Method shows a color panel where the user can choose a new color. Insides of the 
			 * component are then colored with the new color and the picked color is set as the 
			 * current color. All the registered listeners are informed that the color has
			 * changed.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				Color oldColor = ((JColorArea) e.getSource()).selectedColor;
				((JColorArea) e.getSource()).selectedColor = JColorChooser.showDialog(JColorArea.this, 
						"Choose a new color", JColorArea.this.selectedColor);
				((JColorArea) e.getSource()).paintComponent(getGraphics());
				Color newColor = ((JColorArea) e.getSource()).selectedColor;
				if(!oldColor.equals(newColor)) {
					for(ColorChangeListener l: listeners) {
						l.newColorSelected(((JColorArea) e.getSource()), oldColor, newColor);
					}
				}
			}
		};
		this.addMouseListener(mouseClick);
	}
	
	/**
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	/**
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	@Override
	@Transient
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}
	
	/**
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	@Override
	@Transient
	public Dimension getMinimumSize() {
		return new Dimension(15, 15);
	}
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Method paints the inside of the component with the current color.
	 */
	@Override
	protected void paintComponent(Graphics g) {
        if (isOpaque()) { //paint background
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
	}
	
	/**
	 * @see java.awt.Component#getBackground()
	 */
	@Override
	@Transient
	public Color getBackground() {
		return this.selectedColor;
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.component.IColorProvider#getCurrentColor()
	 */
	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}
	
	/**
	 * Method used to register new listeners to the component.
	 * 
	 * @param l new listener
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		if(!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}
	
	/**
	 * Method used to unregister listeners from the component.
	 * 
	 * @param l listener to be unregistered
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
}
