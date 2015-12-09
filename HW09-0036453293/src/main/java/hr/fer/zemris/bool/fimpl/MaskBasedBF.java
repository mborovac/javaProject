/**
 * Package holding all the boolean function classes and methods for 4th Java homework
 * 
 */
package hr.fer.zemris.bool.fimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperatorAND;
import hr.fer.zemris.bool.BooleanOperatorNOT;
import hr.fer.zemris.bool.BooleanOperatorOR;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;

/**
 * Class for creating and managing a mask based boolean function. Class implements interface BooleanFunction.
 * 
 * I am well aware of the code duplication in all three boolean function classes in this package, but I do not 
 * know how to fix it without changing the class diagrams (all 3 must directly implement BooleanFunction).
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MaskBasedBF implements BooleanFunction {
	
	private String name;
	private List<BooleanVariable> domain;
	private boolean indexesAreMinterms;
	private List<Mask> masks;
	private List<Mask> dontCareMasks;
	private Set<Integer> mintermIndexes;
	private Set<Integer> maxtermIndexes;
	private Set<Integer> dontCareIndexes;
	
	/**
	 * Class constructor. Constructor takes a name of the function, list of domain variables, flag defining whether
	 * the given indexes are minterms, list of index masks and list of don't care masks. Constructor 1st calls a private
	 * method argumentChecker which checks whether all of the arguments are legal, if some are not it throws an instance
	 * of IllegalArgumentException class. At the end constructor calls another private method used to create indexes out
	 * of the given masks. Masks and DontCareMasks must be disjoint sets.
	 * 
	 * @param name name of the function, can not be null or empty
	 * @param domain list of domain variables, can not be null, empty or contain null elements
	 * @param indexesAreMinterms flag defining whether the given indexes are minterms, boolean variable
	 * @param masks list of masks used to create indexes, minterms or maxterms based on the indexesAreMinterms,
	 * can not be null, contain null elements or repeat them, each mask can not be of greater length than the
	 * domain of the function
	 * @param dontCareMasks list of masks used to create don't care indexes, can not be null, contain null elements 
	 * or repeat them, each mask can not be of greater length than the domain of the function
	 */
	public MaskBasedBF(String name, List<BooleanVariable> domain,
			boolean indexesAreMinterms, List<Mask> masks, List<Mask> dontCareMasks) {
		
		argumentChecker(name, domain, masks, dontCareMasks);
		
		this.name = name;
		this.domain = new ArrayList<>(domain);
		this.indexesAreMinterms = indexesAreMinterms;
		this.masks = new ArrayList<>(masks);
		this.dontCareMasks = new ArrayList<>(dontCareMasks);

		
		createIndexes();
	}
	
	/**
	 * Private method used in constructor for checking arguments.
	 * 
	 * @param all of the arguments are the arguments of the constructor just passed on to this method
	 */
	private void argumentChecker(String name, List<BooleanVariable> domain, List<Mask> masks, 
			List<Mask> dontCareMasks) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if(domain == null || domain.contains(null)) {
			throw new IllegalArgumentException();
		}
		if(masks == null || masks.contains(null)) {
			throw new IllegalArgumentException();
		}
		for(Mask mask: masks) {
			if(mask.getSize() != domain.size()) {
				throw new IllegalArgumentException();
			}
		}
		if(dontCareMasks == null || dontCareMasks.contains(null)) {
			throw new IllegalArgumentException();
		}
		for(Mask dontCareMask: dontCareMasks) {
			if(dontCareMask.getSize() != domain.size()) {
				throw new IllegalArgumentException();
			}
		}
		List<Integer> maskIndexes = new ArrayList<Integer>();
		for(Mask mask: masks) {
			maskIndexes.addAll(mask.getMaskIndexes());
		}
		List<Integer> dontcareIndexes = new ArrayList<Integer>();
		for(Mask mask: dontCareMasks) {
			dontcareIndexes.addAll(mask.getMaskIndexes());
		}
		for(int i = 0; i < dontcareIndexes.size(); i++) {
			if(maskIndexes.contains(dontcareIndexes.get(i))) {
				throw new IllegalArgumentException("Masks and DontCareMasks should be disjoint sets!");
			}
		}
	}
	
	/**
	 * Private method used to create indexes out of the given masks. Method creates minterm, maxterm and 
	 * don't care indexes.
	 */
	private void createIndexes() {
		Set<Integer> dontCareIndexes = new HashSet<Integer>();
		for(Mask mask: this.dontCareMasks) {
			dontCareIndexes.addAll(mask.getMaskIndexes());
		}
		this.dontCareIndexes = dontCareIndexes;
		
		Set<Integer> maskIndexes = new HashSet<Integer>();
		for(Mask mask: this.masks) {
			maskIndexes.addAll(mask.getMaskIndexes());
		}
		
		Set<Integer> allIndexes = new HashSet<Integer>();
		int totalIndexSpace = (int) Math.pow(2, this.domain.size());
		for(int i = 0; i < totalIndexSpace; i++) {
			allIndexes.add(i);
		}
		allIndexes.removeAll(dontCareIndexes);
		allIndexes.removeAll(maskIndexes);
		
		if(this.indexesAreMinterms) {
			this.mintermIndexes = maskIndexes;
			this.maxtermIndexes = allIndexes;
		} else {
			this.mintermIndexes = allIndexes;
			this.maxtermIndexes = maskIndexes;
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
		if(indexesAreMinterms) {
			value = new BooleanOperatorOR(listOfvariables).getValue();
		} else {
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
		for(Mask mask: masks) {
			BooleanVariable newVariable = new BooleanVariable("AnyName");
			if(indexesAreMinterms) {
				newVariable.setValue(new BooleanOperatorAND(createBoolSourceListFromIndex(mask)).getValue());
			} else {
				newVariable.setValue(new BooleanOperatorOR(createBoolSourceListFromIndex(mask)).getValue());
			}
			listOfVariables.add(newVariable);
		}
		return listOfVariables;
	}
	
	/**
	 * Private method used to create boolean variables (sources) from indexes based on function domain. The list is
	 * meant for use in a boolean operator.
	 * 
	 * @param mask the mask form which boolean variables are supposed to be created
	 * @return
	 */
	private List<BooleanSource> createBoolSourceListFromIndex(Mask mask) {
		List<BooleanSource> boolSourceList = new ArrayList<BooleanSource>();
		for(int i = 0; i < this.domain.size(); i++) {
			BooleanVariable newVariable = new BooleanVariable(domain.get(i).getName());
			if(mask.getValue(i) == MaskValue.ONE) {
				newVariable.setValue(domain.get(i).getValue());
			} else if(mask.getValue(i) == MaskValue.ZERO) {
				newVariable.setValue(new BooleanOperatorNOT(domain.get(i)).getValue());
			} else if(mask.getValue(i) == MaskValue.DONT_CARE) {
				continue;
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
		return Collections.unmodifiableSet(this.mintermIndexes);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#maxtermIterable()
	 */
	@Override
	public Iterable<Integer> maxtermIterable() {
		return Collections.unmodifiableSet(this.maxtermIndexes);
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanFunction#dontcareIterable()
	 */
	@Override
	public Iterable<Integer> dontcareIterable() {
		return Collections.unmodifiableSet(this.dontCareIndexes);
	}
	
	/**
	 * Method used to acquire the list of regular masks current function is based on.
	 * 
	 * @return returns the list of regular masks current function is based on
	 */
	public List<Mask> getMasks() {
		return new ArrayList<>(this.masks);
	}
	
	/**
	 * Method used to acquire the list of don't care masks current function is based on.
	 * 
	 * @return returns the list of don't care masks current function is based on
	 */
	public List<Mask> getDontCareMasks() {
		return new ArrayList<>(this.dontCareMasks);
	}
	
	/**
	 * Method used to check whether the masks in the function are products.
	 * 
	 * @return returns true if the masks are products, false otherwise
	 */
	public boolean areMasksProducts() {
		return !this.indexesAreMinterms;
	}
}
