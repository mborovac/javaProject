/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create and maintain a boolean constant. Class has 3 types of boolean constant: true, false
 * and don't care. Class implements BooleanSource interface.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public final class BooleanConstant implements BooleanSource {
	
	private BooleanValue value;
	public final static BooleanConstant TRUE = new BooleanConstant(BooleanValue.TRUE);
	public final static BooleanConstant FALSE  = new BooleanConstant(BooleanValue.FALSE);
	public static final BooleanSource DONT_CARE = new BooleanConstant(BooleanValue.DONT_CARE);
	
	private BooleanConstant(BooleanValue value) {
		this.value = value;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getValue()
	 * 
	 * Method returns the boolean value of the constant.
	 * @returns a BooleanValue object defining the value of the constant
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 * 
	 * Method returns an empty list. A constant doesn't depend on any variables.
	 * @returns an empty list
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return new ArrayList<BooleanVariable>();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * 
	 * Hash value of the instance is the hash value of its value field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Constants are equal if their values are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BooleanConstant))
			return false;
		BooleanConstant other = (BooleanConstant) obj;
		if (value != other.value)
			return false;
		return true;
	}	
}
