/**
 * Package holding all the boolean function classes and methods for 4th Java homework
 * 
 */
package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for creating and managing an operator tree boolean function. Class implements interface BooleanFunction.
 * Class modifies the value of its internal domain variables when creating its minterms/maxterms/don't cares so it 
 * should be used with care. After an instance of the class is created, all domain variable's values are set to true. 
 * Best way to use it is to set the variable's values to desired value after creating a new instance.
 * 
 * I am well aware of the code duplication in all three boolean function classes in this package, but I do not 
 * know how to fix it without changing the class diagrams (all 3 must directly implement BooleanFunction).
 * 
 * @author MarkoB
 * @version 1.0
 */
public class OperatorTreeBF implements BooleanFunction {
	
	private String name;
	private List<BooleanVariable> domain;
	private BooleanOperator operatorTree;
	private List<Integer> minterms;
	private List<Integer> maxterms;
	private List<Integer> dontCares;
	
	/**
	 * Class constructor. Constructor takes a name of the function, list of domain variables and a boolean operator.
	 * Constructor 1st calls a private method argumentChecker which checks whether all of the arguments are legal, 
	 * if some are not it throws an instance of IllegalArgumentException class.
	 * 
	 * @param name name of the function, can not be null or empty
	 * @param domain list of domain variables, can not be null, empty or contain null elements, domain must contain
	 * all of the operatorTree domain variables
	 * @param operatorTree
	 */
	public OperatorTreeBF(String name, List<BooleanVariable> domain,
			BooleanOperator operatorTree) {
		
		argumentChecker(name, domain, operatorTree);
		
		this.name = name;
		this.domain = new ArrayList<>(domain);
		this.operatorTree = operatorTree;
		
		this.minterms = createListOfIndexes(BooleanValue.TRUE);
		this.maxterms = createListOfIndexes(BooleanValue.FALSE);
		this.dontCares = createListOfIndexes(BooleanValue.DONT_CARE);
	}
	
	/**
	 * Private method used in constructor for checking arguments.
	 * 
	 * @param all of the arguments are the arguments of the constructor just passed on to this method
	 */
	private void argumentChecker(String name, List<BooleanVariable> domain, BooleanOperator operatorTree) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if(domain == null || domain.contains(null)) {
			throw new IllegalArgumentException();
		}
		if(operatorTree == null) {
			throw new IllegalArgumentException();
		}
		for(BooleanVariable treeDomainVariable: operatorTree.getDomain()) {
			if(!domain.contains(treeDomainVariable)) {
				throw new IllegalArgumentException("Function domain must be a superset of operator tree domain!");
			}
		}
	}

	/**
	 * @see hr.fer.zemris.bool.NamedBooleanSource#getName()
	 * 
	 * Method returns the string representation of the name of the function.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getValue()
	 * 
	 * Method returns a boolean value computed by the given operator based on the value of the domain variables.
	 */
	@Override
	public BooleanValue getValue() {
		for(BooleanVariable treeDomainVariable: this.domain) {
			for(BooleanVariable operatorDomainVariable: operatorTree.getDomain()) {
				if(treeDomainVariable.getName().equalsIgnoreCase(operatorDomainVariable.getName())) {
					operatorDomainVariable.setValue(treeDomainVariable.getValue());
				}
			}
		}
		return this.operatorTree.getValue();
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 * 
	 * Method returns the list of the domain variables of the function.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(this.domain);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#hasMinterm(int)
	 */
	@Override
	public boolean hasMinterm(int index) {
		if(index < 0 || index >= Math.pow(2, this.domain.size())) {
			throw new IllegalArgumentException("Index is invalid: " + index);
		}
		for(int minterm: mintermIterable()) {
			if(minterm == index) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#hasMaxterm(int)
	 */
	@Override
	public boolean hasMaxterm(int index) {
		if(index < 0 || index >= Math.pow(2, this.domain.size())) {
			throw new IllegalArgumentException("Index is invalid: " + index);
		}
		for(int maxterm: maxtermIterable()) {
			if(maxterm == index) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#hasDontCare(int)
	 */
	@Override
	public boolean hasDontCare(int index) {
		if(index < 0 || index >= Math.pow(2, this.domain.size())) {
			throw new IllegalArgumentException("Index is invalid: " + index);
		}
		for(int dontCare: dontcareIterable()) {
			if(dontCare == index) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#mintermIterable()
	 */
	@Override
	public Iterable<Integer> mintermIterable() {
		return Collections.unmodifiableList(this.minterms);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#maxtermIterable()
	 */
	@Override
	public Iterable<Integer> maxtermIterable() {
		return Collections.unmodifiableList(this.maxterms);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#dontcareIterable()
	 */
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(this.dontCares);
	}
	
	/**
	 * Private method used to create a list of indexes, minterms, maxterms or don't cares, based on the valuOfTree
	 * variable (TRUE = minterms, FALSE = maxterms, DONT_CARE = don't cares) and the function domain. 
	 * 
	 * @param valueOfTree boolean value that determines whether the 
	 * @return returns the list of sought indexes
	 */
	private List<Integer> createListOfIndexes(BooleanValue valueOfTree) {
		List<Integer> returnList = new ArrayList<Integer>();
		int indexFieldSize = (int) Math.pow(2, this.domain.size());
		for(int i = 0; i < indexFieldSize; i++) {
			String index = Integer.toBinaryString(i);
			while (index.length() < this.domain.size()) {    // fill in with leading 0s
				index = "0" + index;
			}
			setDomainVariableValue(index);
			if(this.getValue().equals(valueOfTree)) {
				returnList.add(i);
			}
		}
		return returnList;
	}
	
	/**
	 * Private method used to set the value of domain variables to match the given index.
	 * 
	 * @param index index to be matched by the domain variable's values
	 */
	private void setDomainVariableValue(String index) {
		char[] indexAsArray = index.toCharArray();
		int domainVariableCounter = 0;
		for(BooleanVariable treeDomainVariable: this.domain) {
			if(indexAsArray[domainVariableCounter] == '1') {
				treeDomainVariable.setValue(BooleanValue.TRUE);
			} else if(indexAsArray[domainVariableCounter] == '0') {
				treeDomainVariable.setValue(BooleanValue.FALSE);
			} else {
				throw new IllegalArgumentException("Method can only accept binary strings");
			}
			domainVariableCounter++;
		}
	}
}
