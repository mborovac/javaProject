/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

/**
 * Class used to create and maintain a view of a vector as a matrix.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MatrixVectorView extends AbstractMatrix {
	
	private IVector original;
	private boolean asRowMatrix;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, the matrix that should be viewed
	 * as a vector and a flag determining whether the matrix is a single-row matrix or a 
	 * single-column matrix.
	 * 
	 * @param original the original matrix
	 * @param asRowMatrix a flag determining whether the matrix is a single-row matrix or a 
	 * single-column matrix; true = single-row matrix, false = single-column matrix
	 * @throws IncompatibleOperandException
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		if(original == null) {
			throw new IncompatibleOperandException();
		}
		this.original = original;
		this.asRowMatrix = asRowMatrix;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getRowsCount()
	 * 
	 * Method returns the number of rows of the matrix.
	 */
	@Override
	public int getRowsCount() {
		if(asRowMatrix) {
			return 1;
		} else {
			return this.original.getDimension();
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getColsCount()
	 * 
	 * Method returns the number of columns of the matrix.
	 */
	@Override
	public int getColsCount() {
		if(asRowMatrix) {
			return this.original.getDimension();
		} else {
			return 1;
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#get(int, int)
	 * 
	 * Method returns the element at the specified position in the matrix.
	 * 
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int row, int col) {
		if(row < 0 || col < 0) {
			throw new IncompatibleOperandException();
		}
		if(asRowMatrix) {
			if(row > 0 || col > this.original.getDimension()) {
				throw new IncompatibleOperandException();
			}
			return this.original.get(col);
		} else {
			if(row > this.original.getDimension() || col > 0) {
				throw new IncompatibleOperandException();
			}
			return this.original.get(row);
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#set(int, int, double)
	 * 
	 * Method sets the element at the specified position in the matrix to the given value.
	 * 
	 * @throws IncompatibleOperandException
	 * @throws UnmodifiableObjectException
	 */
	@Override
	public IMatrix set(int row, int col, double value) throws UnmodifiableObjectException {
		if(row < 0 || col < 0) {
			throw new IncompatibleOperandException();
		}
		if(asRowMatrix) {
			if(row > 0 || col > this.original.getDimension()) {
				throw new IncompatibleOperandException();
			}
			this.original.set(col, value);
		} else {
			if(row > this.original.getDimension() || col > 0) {
				throw new IncompatibleOperandException();
			}
			this.original.set(row, value);
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#copy()
	 * 
	 * Method creates a copy of the current matrix.
	 */
	@Override
	public IMatrix copy() {
		return new MatrixVectorView(this.original, this.asRowMatrix);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#newInstance(int, int)
	 * 
	 * Method creates a new instance of the class Matrix of the given dimensions.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return LinAlgDefaults.defaultMatrix(rows, cols);
	}
}
