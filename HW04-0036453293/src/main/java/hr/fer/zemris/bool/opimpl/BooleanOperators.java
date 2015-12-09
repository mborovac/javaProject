/**
 * Package holding the boolean class BooleanOperators and its methods for 4th Java homework
 * 
 */
package hr.fer.zemris.bool.opimpl;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanOperatorAND;
import hr.fer.zemris.bool.BooleanOperatorNOT;
import hr.fer.zemris.bool.BooleanOperatorOR;
import hr.fer.zemris.bool.BooleanSource;

/**
 * Class used to create new instances of boolean operators AND, OR and NOT from a variable number of sources.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class BooleanOperators {
	
	/**
	 * Private (and only) class constructor.
	 */
	private BooleanOperators() {
		
	}
	
	/**
	 * Method for creating a new instance of boolean operator AND from a variable number of sources.
	 * 
	 * @param sources variable number of sources to create the operator with, can not contain null elements
	 * @return returns a new instance of boolean operator AND
	 */
	public static BooleanOperator and(BooleanSource ... sources) {
		for(BooleanSource source: sources) {
			if(source == null) {
				throw new IllegalArgumentException("Source can not be null!");
			}
		}
		List<BooleanSource> sourceList = new ArrayList<BooleanSource>();
		for(BooleanSource source: sources) {
			sourceList.add(source);
		}
		return new BooleanOperatorAND(sourceList);
	}
	
	/**
	 * Method for creating a new instance of boolean operator OR from a variable number of sources.
	 * 
	 * @param sources variable number of sources to create the operator with, can not contain null elements
	 * @return returns a new instance of boolean operator OR
	 */
	public static BooleanOperator or(BooleanSource ... sources) {
		for(BooleanSource source: sources) {
			if(source == null) {
				throw new IllegalArgumentException("Source can not be null!");
			}
		}
		List<BooleanSource> sourceList = new ArrayList<BooleanSource>();
		for(BooleanSource source: sources) {
			sourceList.add(source);
		}
		return new BooleanOperatorOR(sourceList);
	}
	
	/**
	 * Method for creating a new instance of boolean operator NOT from a single source as the operator i unary.
	 * 
	 * @param sources a single boolean source
	 * @return returns a new instance of boolean operator NOT
	 */
	public static BooleanOperator not(BooleanSource source) {
		if(source == null) {
			throw new IllegalArgumentException("Source can not be null!");
		}
		return new BooleanOperatorNOT(source);
	}
}
