package hr.fer.zemris.java.hw11.jvdraw.observers;

import hr.fer.zemris.java.hw11.jvdraw.component.IColorProvider;

import java.awt.Color;

/**
 * Interface defining the only method needed to implement a ColorChangeListener.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface ColorChangeListener {
	
	/**
	 * Method called by the subject on all the registered listeners when its color changes.
	 * 
	 * @param source the subject
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
