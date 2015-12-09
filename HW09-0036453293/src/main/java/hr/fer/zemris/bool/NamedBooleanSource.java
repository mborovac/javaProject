/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

/**
 * Interface used to differentiate named boolean sources and un-named boolean sources. It extends the
 * interface BooleanSource and defines another method: getName().
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface NamedBooleanSource extends BooleanSource {
	
	/**
	 * Method used to acquire the name of the named source.
	 * 
	 * @return a string representing the name of the source
	 */
	String getName();
}
