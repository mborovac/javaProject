/**
 * Package holding all the classes used in 2nd assignment of 6th Java homework.
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class used to create and maintain a value wrapper. It provides the user with methods for
 * incrementing or decrementing the current value, multiplying it or dividing by some other value.
 * Class works with null values, strings, doubles or integers. Null values are considered integers with
 * the value of 0.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ValueWrapper {
	
	private Object value;
	
	/**
	 * Class constructor. COnstructor takes a single object and stores it as a value.
	 * 
	 * @param initialValue the starting value of the wrapper
	 */
	public ValueWrapper(Object initialValue) {
		checkArgument(initialValue);
		this.value = initialValue;
	}
	
	/**
	 * Method used to increment the current value of the wrapper.
	 * 
	 * @param incValue the value to be added to the current value
	 */
	public void increment(Object incValue) {
		checkArgument(incValue);
		this.value = prepareObjects(this.value);
		Object givenValue = prepareObjects(incValue);
		if(this.value instanceof Double && givenValue instanceof Double) {
			this.value = (Double) this.value + (Double) givenValue;
		} else if(this.value instanceof Double && givenValue instanceof Integer) { 
			this.value = (Double) this.value + (Integer) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Double) { 
			this.value = (Integer) this.value + (Double) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Integer) {
			this.value = (Integer) this.value + (Integer) givenValue;
		}
	}
	
	/**
	 * Method used to decrement the current value of the wrapper.
	 * 
	 * @param decValue the value to be subtracted from the current value
	 */
	public void decrement(Object decValue) {
		checkArgument(decValue);
		this.value = prepareObjects(this.value);
		Object givenValue = prepareObjects(decValue);
		if(this.value instanceof Double && givenValue instanceof Double) {
			this.value = (Double) this.value - (Double) givenValue;
		} else if(this.value instanceof Double && givenValue instanceof Integer) { 
			this.value = (Double) this.value - (Integer) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Double) { 
			this.value = (Integer) this.value - (Double) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Integer) {
			this.value = (Integer) this.value - (Integer) givenValue;
		}
	}
	
	/**
	 * Method used to multiply the current value by another value.
	 * 
	 * @param mulValue value that the current value is to be multiplied by
	 */
	public void multiply(Object mulValue) {
		checkArgument(mulValue);
		this.value = prepareObjects(this.value);
		Object givenValue = prepareObjects(mulValue);
		if(this.value instanceof Double && givenValue instanceof Double) {
			this.value = (Double) this.value * (Double) givenValue;
		} else if(this.value instanceof Double && givenValue instanceof Integer) { 
			this.value = (Double) this.value * (Integer) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Double) { 
			this.value = (Integer) this.value * (Double) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Integer) {
			this.value = (Integer) this.value * (Integer) givenValue;
		}
	}
	
	/**
	 * Method used to divide the current value by another value.
	 * 
	 * @param mulValue value that the current value is to be divided by
	 */
	public void divide(Object divValue) {
		checkArgument(divValue);
		this.value = prepareObjects(this.value);
		Object givenValue = prepareObjects(divValue);
		if(givenValue.equals(0)) {
			throw new IllegalArgumentException("Can not divide by null");
		}
		if(this.value instanceof Double && givenValue instanceof Double) {
			this.value = (Double) this.value / (Double) givenValue;
		} else if(this.value instanceof Double && givenValue instanceof Integer) { 
			this.value = (Double) this.value / (Integer) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Double) { 
			this.value = (Integer) this.value / (Double) givenValue;
		} else if(this.value instanceof Integer && givenValue instanceof Integer) {
			this.value = (Integer) this.value / (Integer) givenValue;
		}
	}
	
	/**
	 * Method used to compare the current value with another value. Method returns an integer 
	 * smaller than 0 if the current value is smaller, 0 if they are equal or an integer
	 * greater than 0 if the current value is smaller than the given one.
	 * 
	 * @param withValue value that the current value is to be compared to
	 * @return returns an integer smaller than 0 if the current value is smaller, 0 if they are 
	 * equal or an integer greater than 0 if the current value is smaller than the given one
	 */
	public int numCompare(Object withValue) {
		checkArgument(withValue);
		if(this.value == null && withValue == null) {
			return 0;
		} else if(this.value == null && withValue instanceof Integer){
			return -((Integer) withValue);
		} else if(this.value instanceof Integer && withValue == null) {
			return ((Integer) this.value);
		} else {
			this.value = prepareObjects(this.value);
			Object givenValue = prepareObjects(withValue);
			if(this.value instanceof Double && givenValue instanceof Double) {
				return (int) ((Double) this.value - (Double) givenValue);
			} else if(this.value instanceof Double && givenValue instanceof Integer) { 
				return (int) ((Double) this.value - (Integer) givenValue);
			} else if(this.value instanceof Integer && givenValue instanceof Double) { 
				return (int) ((Integer) this.value - (Double) givenValue);
			} else {
				return (int) ((Integer) this.value - (Integer) givenValue);
			}
		}
	}
	
	/**
	 * Method used to check all the arguments of other methods.
	 * 
	 * @param argument the argument that is to be checked.
	 */
	private void checkArgument(Object argument) {
		if(argument != null && !(argument instanceof Integer) && 
				!(argument instanceof Double) && !(argument instanceof String)) {
			throw new IllegalArgumentException("Given value can only be null, an Integer, a Double or a String");
		}
	}
	
	/**
	 * Method used to cast the given object to its proper class.
	 * 
	 * @param givenValue the object that is to be cast
	 * @return returns an instance of the appropriate class containing the object's value
	 */
	private Object prepareObjects(Object givenValue) {
		if(givenValue == null) {
			return new Integer(0);
		} else if(givenValue instanceof String) {
			return convertStringToNumber((String) givenValue);
		} else if(givenValue instanceof Double) {
			return ((Double) givenValue);
		} else {
			return ((Integer) givenValue);
		}
	}
	
	/**
	 * Method used to convert a string in to an instance of an appropriate number class.
	 * 
	 * @param string the string that is to be converted
	 * @return returns and instance of the Integer or Double class, depending on the given string
	 */
	private Object convertStringToNumber(String string) {
		if(string.contains(".") || string.contains("E")) {
			try {
				return Double.parseDouble(string);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Can not convert given string to Double " + string);
			}
		} else {
			try {
				return Integer.parseInt(string);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Can not convert given string to Integer " + string);
			}
		}
	}

	/**
	 * Getter for current value.
	 * 
	 * @return returns the current value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for the new current value.
	 * 
	 * @param value the value that is to be set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
