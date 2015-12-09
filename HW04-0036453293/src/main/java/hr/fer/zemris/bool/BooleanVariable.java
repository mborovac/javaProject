/**
 * Package holding all the boolean classes and methods for 4th Java homework, except for BooleanOperators
 * 
 */
package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is used to create and maintain a single boolean variable. Class implements NamedBooleanSource interface 
 * implementing its functions getValue(), getDomain() and getName().
 * 
 * @author MarkoB
 * @version 1.0
 */
public class BooleanVariable implements NamedBooleanSource {
	
	private String name;
	private BooleanValue value = BooleanValue.FALSE;
	
	/**
	 * Class constructor. Constructor takes a single string representing the name of the variable.
	 * Variable's value is set to false by default, it needs to be changed using the setValue() method
	 * if any other value is required.
	 * 
	 * @param name the name of the variable, can not be null or an empty string
	 */
	public BooleanVariable(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Variable name can not be null or an empty string");
		}
		this.name = name;
	}
	
	/**
	 * Method used to set the value of the variable.
	 * 
	 * @param value boolean value that should be set as the variable value
	 */
	public void setValue(BooleanValue value) {
		this.value = value;
	}
	
	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getValue()
	 * 
	 * Method returns the value of the variable.
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}

	/**
	 * @see hr.fer.zemris.bool.BooleanSource#getDomain()
	 * 
	 * Method returns the list containing only one element: the variable itself
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		ArrayList<BooleanVariable> variableDomain = new ArrayList<BooleanVariable>();
		variableDomain.add(this);
		return variableDomain;
	}

	/**
	 * @see hr.fer.zemris.bool.NamedBooleanSource#getName()
	 * 
	 * Method returns the name of the variable.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * 
	 * Hash value of the instance is the hash value of its value field and name field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Variables are equal if their values are equal and their names are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BooleanVariable))
			return false;
		BooleanVariable other = (BooleanVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
}
