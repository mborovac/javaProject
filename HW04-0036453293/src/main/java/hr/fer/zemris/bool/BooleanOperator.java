/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract class implementing getDomain() function of BooleanSource interface. Class is used to represent
 * an operator for boolean values.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class BooleanOperator implements BooleanSource {
	
	private List<BooleanSource> sources;
	
	/**
	 * Class constructor. Constructor take a list of sources.
	 * 
	 * @param sources sources of the operator, can not be null or an empty list
	 */
	protected BooleanOperator(List<BooleanSource> sources) {
		if(sources == null || sources.isEmpty()) {
			throw new IllegalArgumentException("Sources can not be null or an empty list");
		}
		this.sources = new ArrayList<>(sources);
	}
	
	/**
	 * Method for acquiring operator sources.
	 * 
	 * @return returns a list of operator sources
	 */
	protected List<BooleanSource> getSources() {
		return Collections.unmodifiableList(this.sources);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 * 
	 * Method returns a list of domains of operator sources.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		Set<BooleanVariable> domainSet = new HashSet<BooleanVariable>();
		List<BooleanVariable> domain = new ArrayList<BooleanVariable>();
		for(int i = 0; i < sources.size(); i++) {
			domainSet.addAll(sources.get(i).getDomain());
		}
		domain.addAll(domainSet);
		return domain;
	}
}
