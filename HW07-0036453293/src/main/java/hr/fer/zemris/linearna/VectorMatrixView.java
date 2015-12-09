/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

/**
 * Class used to create and maintain a view of a matrix as a vector.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class VectorMatrixView extends AbstractVector {
	
	private IMatrix original;
	private int dimension;
	private boolean rowMatrix;
	
	/**
	 * Class constructor. Constructor takes a single argument, the matrix that should be viewed
	 * as a vector.
	 * 
	 * @param original the original matrix
	 * @throws IncompatibleOperandException
	 */
	public VectorMatrixView(IMatrix original) {
		if(original == null) {
			throw new IncompatibleOperandException();
		}
		this.original = original;
		if(this.original.getRowsCount() == 1) {
			this.rowMatrix = true;
		} else if(this.original.getColsCount() == 1) {
			this.rowMatrix = false;
		} else {
			throw new IncompatibleOperandException();
		}
		if(rowMatrix) {
			this.dimension = this.original.getColsCount();
		} else {
			this.dimension = this.original.getRowsCount();
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#get(int)
	 * 
	 * Method returns the element at the specified position in the vector.
	 * 
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int index) {
		if(index < 0 || index > this.dimension) {
			throw new IncompatibleOperandException();
		}
		if(rowMatrix) {
			return this.original.get(0, index);
		} else {
			return this.original.get(index, 0);
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#set(int, double)
	 * 
	 * Method sets the element at the specified position in the vector to the given value.
	 * 
	 * @throws IncompatibleOperandException
	 * @throws UnmodifiableObjectException
	 */
	@Override
	public IVector set(int index, double value) throws UnmodifiableObjectException {
		if(index < 0 || index > this.dimension) {
			throw new IncompatibleOperandException();
		}
		if(rowMatrix) {
			this.original.set(0, index, value);
		} else {
			this.original.set(index, 0, value);
		}
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
		return new VectorMatrixView(this.original);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractVector#newInstance(int)
	 * 
	 * Method creates a new instance of the class Vector of the given dimension.
	 */
	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}
}
