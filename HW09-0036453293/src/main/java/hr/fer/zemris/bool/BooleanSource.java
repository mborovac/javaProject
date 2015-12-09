/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.List;

/**
 * Interface defining 2 methods crucial for any boolean source: getValue() and getDomain().
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface BooleanSource {
	
	/**
	 * Method used to acquire the boolean value of the source.
	 * 
	 * @return returns one of the 3 boolean values: true, false, don't care, depending on the
	 * implementation
	 */
	BooleanValue getValue();
	/**
	 * Method used to acquire the domain of the source. 
	 * 
	 * @return returns a list of boolean variables representing the domain of the source, variables on which
	 * the source depends
	 */
	List<BooleanVariable> getDomain();
}
