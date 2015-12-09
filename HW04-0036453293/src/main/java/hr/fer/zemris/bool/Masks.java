/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create multiple instances of Mask class with a single constructor. Class has methods for creating
 * masks out of variable number of strings or indexes.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class Masks {
	
	
	/**
	 * Class constructor.
	 */
	private Masks() {
		
	}

	/**
	 * Method used to create multiple instances of class Mask from a variable number of indexes. All masks will be 
	 * of the same size.
	 * 
	 * @param maskSize size of the new masks, can not be lower than 1
	 * @param indexes variable number of indexes based on which new masks will be created, each index can not
	 * be lower than 0 or greater than maskSize^2, indexes can not repeat
	 * @return returns a list of new masks
	 */
	public static List<Mask> fromIndexes(int maskSize, int ... indexes) {
		if(maskSize < 1) {
			throw new IllegalArgumentException("Mask size can not be lower than 1");
		}
		for(int i = 0; i < indexes.length; i++) {
			for(int j = i+1; j < indexes.length; j++) {
				if(indexes[i] == indexes[j]) {
					throw new IllegalArgumentException("Index can not repeat: " + indexes[i]);
				}
			}
		}
		List<Mask> newMasks = new ArrayList<Mask>();
		int rangeOfIndexes = (int) Math.pow(2, maskSize);
		for(int i = 0; i < indexes.length; i++)  {
			if(indexes[i] < 0) {
				throw new IllegalArgumentException("Index can not be lower than 0: " + indexes[i]);
			}
			if(indexes[i] > rangeOfIndexes - 1) {
				throw new IllegalArgumentException("Index can not be greater than maskSize^2: " + indexes[i]);
			}
			newMasks.add(Mask.fromIndex(maskSize, indexes[i]));
		}
		return newMasks;
	}
	
	/**
	 * Method used to create multiple instances of class Mask from a variable number of strings.
	 *  
	 * @param inputStrings strings based on which new masks will be created, can not be null or empty, strings
	 * can not repeat
	 * @return returns a list of new masks
	 */
	public static List<Mask> fromStrings(String ... inputStrings) {
		List<Mask> newMasks = new ArrayList<Mask>();
		for(int i = 0; i < inputStrings.length; i++)  {
			if(inputStrings[i] == null || inputStrings[i].isEmpty()) {
				throw new IllegalArgumentException("Can not create a Mask from given string: " + inputStrings[i]);
			}
			newMasks.add(Mask.parse(inputStrings[i]));
		}
		for(int i = 0; i < inputStrings.length; i++)  {
			for(int j = i+1; j < inputStrings.length; j++)  {
				if(inputStrings[i].equalsIgnoreCase(inputStrings[j])) {
					throw new IllegalArgumentException("Can not create a Mask from given string: " + inputStrings[i]);
				}
			}
		}
		return newMasks;
	}
}
