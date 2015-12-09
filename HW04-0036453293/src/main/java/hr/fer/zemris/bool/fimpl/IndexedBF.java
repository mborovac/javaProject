/**
 * Package holding all the boolean function classes and methods for 4th Java homework
 * 
 */
package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperatorAND;
import hr.fer.zemris.bool.BooleanOperatorNOT;
import hr.fer.zemris.bool.BooleanOperatorOR;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

/**
 * Class for creating and managing an indexed boolean function. Class implements interface BooleanFunction.
 * 
 * I am well aware of the code duplication in all three boolean function classes in this package, but I do not 
 * know how to fix it without changing the class diagrams (all 3 must directly implement BooleanFunction).
 * 
 * @author MarkoB
 * @version 1.0
 */
public class IndexedBF implements BooleanFunction  {
	
	private String name;
	private List<BooleanVariable> domain;
	private boolean indexesAreMinterms;
	private List<Integer> indexes;
	private List<Integer> dontCares;
	private List<Integer> minterms;
	private List<Integer> maxterms;

	/**
	 * Class constructor. Constructor takes a name of the function, list of domain variables, flag defining whether
	 * the given indexes are minterms, list of indexes and list of don't cares. Constructor 1st calls a private
	 * method argumentChecker which checks whether all of the arguments are legal, if some are not it throws an instance
	 * of IllegalArgumentException class. Constructor also creates private lists of minterms and maxterms based on 
	 * the given flag and indexes.
	 * 
	 * @param name name of the function, can not be null or empty
	 * @param domain list of domain variables, can not be null, empty or contain null elements
	 * @param indexesAreMinterms flag defining whether the given indexes are minterms, boolean variable
	 * @param indexes list of indexes, minterms or maxterms based on the indexesAreMinterms, can not be null,
	 * contain null elements or repeat them, each index can not be lower than 0 or greater than domainSize^2
	 * @param dontCares list of dontCares, can not be null, contain null elements or repeat them, 
	 * each dontCares can not be lower than 0 or greater than domainSize^2
	 */
	public IndexedBF(String name, List<BooleanVariable> domain, boolean indexesAreMinterms, 
			List<Integer> indexes, List<Integer> dontCares) {
		
		argumentChecker(name, domain, indexes, dontCares);
		
		this.name = name;
		this.domain = new ArrayList<>(domain);
		this.indexesAreMinterms = indexesAreMinterms;
		this.indexes = new ArrayList<>(indexes);
		this.dontCares = new ArrayList<>(dontCares);
		
		if(indexesAreMinterms) {
			this.minterms = new ArrayList<>(this.indexes);
			this.maxterms = new ArrayList<>(restOfTheIndexes());
		} else {
			this.minterms = new ArrayList<>(restOfTheIndexes());
			this.maxterms = new ArrayList<>(this.indexes);
		}
	}
	
	/**
	 * Private method used in constructor for checking arguments.
	 * 
	 * @param all of the arguments are the arguments of the constructor just passed on to this method
	 */
	private void argumentChecker(String name, List<BooleanVariable> domain, List<Integer> indexes, 
			List<Integer> dontCares) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if(domain == null || domain.contains(null) || domain.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if(indexes == null || indexes.contains(null)) {
			throw new IllegalArgumentException();
		}
		if(dontCares == null || dontCares.contains(null)) {
			throw new IllegalArgumentException();
		}
		for(int index: indexes) {
			if(index < 0 || index > Math.pow(2, domain.size())) {
				throw new IllegalArgumentException();
			}
			if(dontCares.contains(index)) {
				throw new IllegalArgumentException();
			}
		}
		for(int dontCare: dontCares) {
			if(dontCare < 0 || dontCare > Math.pow(2, domain.size())) {
				throw new IllegalArgumentException();
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
	 * Method returns a boolean value computed as the sum of minterms/product of maxterms, depending on the flag
	 * indexesAreMinterms and function domain.
	 */
	@Override
	public BooleanValue getValue() {
		BooleanValue value;
		List<BooleanSource> listOfvariables = computeOperatorValue();
		if(indexesAreMinterms) {  			// sum of minterms
			value = new BooleanOperatorOR(listOfvariables).getValue();
		} else {							// product of maxterms
			value = new BooleanOperatorAND(listOfvariables).getValue();
		}					
		return value;
	}
	
	/**
	 * Private method used to compute the product of each minterm/sum of each maxterm, based on the flag indexesAreMinterms
	 * and function domain.
	 * 
	 * @return returns the list of boolean sources which are always boolean variables in this method. The code could also be
	 * changed so that it returns the list of boolean constants, it would be the same.
	 */
	private List<BooleanSource> computeOperatorValue() {
		List<BooleanSource> listOfVariables = new ArrayList<BooleanSource>();
		for(int index: indexes) {
			BooleanVariable newVariable = new BooleanVariable("AnyName");
			if(indexesAreMinterms) {
				newVariable.setValue(new BooleanOperatorAND(createBoolSourceListFromIndex(index)).getValue());
			} else {
				newVariable.setValue(new BooleanOperatorOR(createBoolSourceListFromIndex(index)).getValue());
			}
			listOfVariables.add(newVariable);
		}
		return listOfVariables;
	}
	
	/**
	 * Private method used to create boolean variables (sources) from indexes based on function domain. The list is
	 * meant for use in a boolean operator.
	 * 
	 * @param index the index form which boolean variables are supposed to be created
	 * @return
	 */
	private List<BooleanSource> createBoolSourceListFromIndex(int index) {
		List<BooleanSource> boolSourceList = new ArrayList<BooleanSource>();
		char[] indexAsBinaryCharArray = Integer.toBinaryString(index).toCharArray();
		int indexStartingPoint = this.domain.size() - indexAsBinaryCharArray.length;
		if(indexAsBinaryCharArray.length < this.domain.size()) {
			for(int i = 0; i < indexStartingPoint; i++) {
				BooleanVariable newVariable = new BooleanVariable(domain.get(i).getName());
				newVariable.setValue(new BooleanOperatorNOT(domain.get(i)).getValue());
				boolSourceList.add(newVariable);
			}
		}
		for(int i = 0; i < indexAsBinaryCharArray.length; i++) {
			BooleanVariable newVariable = new BooleanVariable(domain.get(i + indexStartingPoint).getName());
			if(indexAsBinaryCharArray[i] == '1') {
				newVariable.setValue(domain.get(i + indexStartingPoint).getValue());
			} else if(indexAsBinaryCharArray[i] == '0') {
				newVariable.setValue(new BooleanOperatorNOT(domain.get(i + indexStartingPoint)).getValue());
			} else {
				throw new IllegalArgumentException("Index is invalid!");
			}
			boolSourceList.add(newVariable);
		}
		return boolSourceList;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 * 
	 * * Method returns the list of the domain variables of the function.
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		return Collections.unmodifiableList(this.domain);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#hasMinterm(int)
	 * 
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
	 * Method creates a list made of the rest of the indexes, the ones that weren't given in the class constructor.
	 * Union of indexes given in the class constructor, dontCares and indexes returned by this method is a total index
	 * space set of the current class instance.
	 * 
	 * @return returns a list of maxterms if the flag indexesAreMinterms is true, a list of minterms otherwise
	 */
	private List<Integer> restOfTheIndexes() {
		List<Integer> restOfTheIndexes = new ArrayList<Integer>();
		int totalIndexSpace = (int) Math.pow(2, this.domain.size());
		for(int i = 0; i < totalIndexSpace; i++) {
			restOfTheIndexes.add(i);
		}
		restOfTheIndexes.removeAll(indexes);
		restOfTheIndexes.removeAll(dontCares);
		return restOfTheIndexes;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#dontcareIterable()
	 */
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableList(this.dontCares);
	}

}
