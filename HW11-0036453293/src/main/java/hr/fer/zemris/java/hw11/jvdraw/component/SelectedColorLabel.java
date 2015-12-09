package hr.fer.zemris.java.hw11.jvdraw.component;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jvdraw.observers.ColorChangeListener;

/**
 * Class used to show the RBG components of Color Providers.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SelectedColorLabel extends JLabel implements ColorChangeListener {
	
	private static final long serialVersionUID = 1L;
	
	private IColorProvider frontColorProvider;
	private IColorProvider backColorProvider;
	private Color currentForegroundColor;
	private Color currentBackgroundColor;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, the Color Providers whose RBG components
	 * it will show.
	 * 
	 * @param frontColorProvider Color Provider whose RBG components the label will show
	 * @param backColorProvider Color Provider whose RBG components the label will show
	 */
	public SelectedColorLabel(IColorProvider frontColorProvider, IColorProvider backColorProvider) {
		this.frontColorProvider = frontColorProvider;
		this.backColorProvider = backColorProvider;
		this.currentForegroundColor = frontColorProvider.getCurrentColor();
		this.currentBackgroundColor = backColorProvider.getCurrentColor();
		this.changeText();
		if(frontColorProvider instanceof JColorArea) {
			((JColorArea) frontColorProvider).addColorChangeListener(this);
		}
		if(backColorProvider instanceof JColorArea) {
			((JColorArea) backColorProvider).addColorChangeListener(this);
		}
	}

	/**
	 * @see hr.fer.zemris.java.hw11.jvdraw.observers.ColorChangeListener#newColorSelected(
	 * hr.fer.zemris.java.hw11.jvdraw.component.IColorProvider, java.awt.Color, java.awt.Color)
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		if(source == null) {
			throw new IllegalArgumentException("Source can not be null.");
		}
		if(oldColor == null || newColor == null) {
			throw new IllegalArgumentException("Old or new color can not be null.");
		}
		if(!(source instanceof JColorArea)) {
			throw new IllegalArgumentException("Source must be an instance of JColorArea.");
		}
		JColorArea castSource = (JColorArea) source;
		if(castSource.equals(frontColorProvider)) {
			currentForegroundColor = newColor;
		} else if(castSource.equals(backColorProvider)){
			currentBackgroundColor = newColor;
		} else {
			throw new IllegalArgumentException("Unknown source.");
		}
		this.changeText();
	}
	
	/**
	 * Method used to update the label text.
	 */
	private void changeText() {
		this.setText("Foreground color: ("+
				currentForegroundColor.getRed() +","+
				currentForegroundColor.getGreen() +","+
				currentForegroundColor.getBlue() +
				"), background color: ("+
				currentBackgroundColor.getRed() +","+
				currentBackgroundColor.getGreen() +","+
				currentBackgroundColor.getBlue() +
				").");
	}
}
