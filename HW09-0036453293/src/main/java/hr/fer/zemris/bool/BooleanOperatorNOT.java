/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.Arrays;

/**
 * Class extending BooleanOperator class and implementing its method getValue().
 * Class represents a NOT operator over boolean values.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class BooleanOperatorNOT extends BooleanOperator {

	/**
	 * Class constructor. Constructor takes a single operator source.
	 * 
	 * @param source operator source
	 */
	public BooleanOperatorNOT(BooleanSource source) {
		super(Arrays.asList(source));
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getValue()
	 * 
	 * Method returns a boolean value that is the result of the logical NOT over the operator source.
	 * @return returns a single boolean value, the result of the operator
	 */
	@Override
	public BooleanValue getValue() {
		if(getSources().get(0).getValue() == BooleanValue.FALSE) {
			return BooleanValue.TRUE;
		} else if(getSources().get(0).getValue() == BooleanValue.TRUE) {
			return BooleanValue.FALSE;
		} else {
			return BooleanValue.DONT_CARE;
		}
	}
}
