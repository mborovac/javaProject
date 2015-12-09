/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.List;

/**
 * Class extending BooleanOperator class and implementing its method getValue().
 * Class represents an OR operator over boolean values.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class BooleanOperatorOR extends BooleanOperator {
	
	/**
	 * Class constructor. Constructor takes a list of sources.
	 * 
	 * @param source list of operator sources
	 */
	public BooleanOperatorOR(List<BooleanSource> source) {
		super(source);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getValue()
	 * 
	 * Method returns a boolean value that is the result of the logical OR over all of the operator sources.
	 * @return returns a single boolean value, the result of the operator
	 */
	@Override
	public BooleanValue getValue() {
		boolean dontCare = false;
		for(BooleanSource source: getSources()) {
			if(source.getValue() == BooleanValue.TRUE) {
				return BooleanValue.TRUE;
			} else if(source.getValue() == BooleanValue.DONT_CARE) {
				dontCare = true;
			}
		}
		if(dontCare) {
			return BooleanValue.DONT_CARE;
		}
		return BooleanValue.FALSE;
	}
}
