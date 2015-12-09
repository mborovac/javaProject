/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.List;

/**
 * Interface used to define crucial methods for functions handling boolean variables, operators and constants.
 * Methods are hasMinterm(), hasMaxterm(), hasDontCare(), mintermIterable, maxtermIterable and  dontcareIterable.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface BooleanFunction extends NamedBooleanSource {
	
	/**
	 * Method checks whether the function has any minterms.
	 * 
	 * @param index integer whose binary representation is checked to be a minterm of the function, can not be lower
	 * than 0 or greater than 2^domainSize
	 * @return returns true if the index is a minterm, false otherwise
	 */
	boolean hasMinterm(int index);
	
	/**
	 * Method checks whether the function has any maxterms.
	 * 
	 * @param index integer whose binary representation is checked to be a maxterm of the function, can not be lower
	 * than 0 or greater than domainSize^2
	 * @return returns true if the index is a maxterm, false otherwise
	 */
	boolean hasMaxterm(int index);
	
	/**
	 * Method checks whether the function has any don't cares. 
	 * 
	 * @param index integer whose binary representation is checked to be don't care of the function, can not be lower
	 * than 0 or greater than 2^domainSize
	 * @return returns true if the index is don't care, false otherwise
	 */
	boolean hasDontCare(int index);
	
	/**
	 * Method returns a collection used to iterate through function minterms.
	 * 
	 * @return returns a collection of function minterms
	 */
	Iterable<Integer> mintermIterable();
	/**
	 * Method returns a collection used to iterate through function maxterms.
	 * 
	 * @return returns a collection of function maxterms
	 */
	Iterable<Integer> maxtermIterable();
	
	/**
	 * Method returns a collection used to iterate through function don't cares.
	 * 
	 * @return returns a collection of function don't cares
	 */
	Iterable<Integer> dontcareIterable();
	
	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 */
	List<BooleanVariable> getDomain();
}
