/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

/**
 * Class used to create and maintain objects representing a normal vector.
 * Class extends AbstractVector class.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Vector extends AbstractVector {
	
	private double[] elements;
	private int dimension;
	private boolean readOnly;
	
	/**
	 * Class constructor. Constructor takes a single argument, an array of elements.
	 * 
	 * @param elements array of vector elements
	 */
	public Vector(double[] elements) {
		this(false, false, elements);
	}
	
	/**
	 * Class constructor. Constructor takes 3 arguments, 2 flags and an array of elements.
	 * 1st flag determines whether the vector is read only, and the 2nd one determines whether 
	 * the given array of elements can be used or it should be copied before use.
	 * 
	 * @param readOnly flag determines whether the vector is read only
	 * @param useGiven flag determines whether the given array of elements can be used or it 
	 * should be copied before use
	 * @param elements array of vector elements
	 */
	public Vector(boolean readOnly, boolean useGiven, double[] elements) {
		if(useGiven) {
			this.elements = elements;
		} else {
			double[] newElements = new double[elements.length];
			for(int i = 0; i < elements.length; i++) {
				newElements[i] = elements[i];
			}
			this.elements = newElements;
		}
		this.dimension = this.elements.length;
		this.readOnly = readOnly;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#get(int)
	 * 
	 * Method returns the elements at the given position in the vector.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int index) {
		if(index < 0 || index >= this.dimension) {
			throw new IncompatibleOperandException();
		}
		return this.elements[index];
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#set(int, double)
	 * 
	 * Method sets the specified element to the desired value.
	 * @throws IncompatibleOperandException
	 * @throws UnmodifiableObjectException
	 */
	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if(this.readOnly) {
			throw new UnmodifiableObjectException();
		}
		if(index < 0 || index >= this.dimension) {
			throw new IncompatibleOperandException();
		}
		this.elements[index] = value;
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#getDimension()
	 * 
	 * Method returns the dimension of the vector(number of elements).
	 */
	@Override
	public int getDimension() {
		return this.dimension;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#copy()
	 * 
	 * Method creates a copy of the current vector.
	 */
	@Override
	public IVector copy() {
		return new Vector(this.readOnly, false, this.elements);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#newInstance(int)
	 * 
	 * Method creates a new instance of the class Vector of the given dimension.
	 * Elements are set to 0.
	 * 
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector newInstance(int dimension) {
		if(dimension < 0) {
			throw new IncompatibleOperandException();
		}
		double[] newElements = new double[dimension];
		return new Vector(false, true, newElements);
	}
	
	/**
	 * Method for creating a vector from a string.
	 * 
	 * Vector elements must be separated by at least one space character.
	 * 
	 * @param stringVector the string to be parsed
	 * @return returns a new instance of the Vector class created from the given string
	 * @throws IncompatibleOperandException
	 */
	public static Vector parseSimple(String stringVector) {
		if(stringVector == null || stringVector.isEmpty()) {
			throw new IncompatibleOperandException();
		}
		String[] stringElements = stringVector.split("\\s+");
		double[] elements = new double[stringElements.length];
		for(int i = 0; i < stringElements.length; i++) {
			elements[i] = Double.parseDouble(stringElements[i]);
		}
		return new Vector(false, true, elements);
	}
}
