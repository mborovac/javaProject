package hr.fer.zemris.java.hw11.jvdraw.component;

import java.awt.Color;

/**
 * Interface used to define the only method needed to implement a color provider.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface IColorProvider {
	
	/**
	 * Method used to acquire the current provider's color.
	 * 
	 * @return returns the current color
	 */
	Color getCurrentColor();
}
