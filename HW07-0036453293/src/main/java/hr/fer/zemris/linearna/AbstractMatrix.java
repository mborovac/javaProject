/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

import java.util.Locale;

/**
 * Abstract class used to implement all of the general matrix methods. Specific methods must be
 * implemented in the classes extending this one. Class implements IMatrix interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public abstract class AbstractMatrix implements IMatrix {
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#getRowsCount()
	 */
	public abstract int getRowsCount();
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#getColsCount()
	 */
	public abstract int getColsCount();
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#get(int, int)
	 */
	public abstract double get(int row, int col);
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#set(int, int, double)
	 */
	public abstract IMatrix set(int row, int col, double value);
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#copy()
	 */
	public abstract IMatrix copy();
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#newInstance(int, int)
	 */
	public abstract IMatrix newInstance(int rows, int cols);
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nTranspose(boolean)
	 * 
	 * Method transposes the matrix. Depending on the argument given, it can be a live view of
	 * the current matrix or a completely new matrix.
	 */
	@Override
	public IMatrix nTranspose(boolean liveView) {
		IMatrix newMatrix;
		if(liveView) {
			return new MatrixTransposeView(this);
		} else {
			newMatrix = newInstance(this.getRowsCount(), this.getColsCount());
			for(int i = 0; i < this.getRowsCount(); i++) {
				for(int j = 0; j < this.getColsCount(); j++) {
					newMatrix.set(i, j, this.get(j, i));
				}
			}
			return newMatrix;
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#add(hr.fer.zemris.linearna.IMatrix)
	 * 
	 * Method adds the given matrix to the current matrix. Method returns the current matrix
	 * after the addition.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix add(IMatrix other) {
		checkBasicCompatibility(other);
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < this.getColsCount(); j++) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nAdd(hr.fer.zemris.linearna.IMatrix)
	 * 
	 * Method adds the given matrix to the current matrix. Method returns a new matrix, the
	 * resulting matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#sub(hr.fer.zemris.linearna.IMatrix)
	 * 
	 * Method subtracts the given matrix from the current matrix. Method returns the current matrix
	 * after the subtraction.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		checkBasicCompatibility(other);
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < this.getColsCount(); j++) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nSub(hr.fer.zemris.linearna.IMatrix)
	 * 
	 * Method subtracts the given matrix from the current matrix. Method returns a new matrix, the
	 * resulting matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nMultiply(hr.fer.zemris.linearna.IMatrix)
	 * 
	 * Method multiplies 2 matrices. Method returns a new matrix, the resulting matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {
		checkMulCompatibility(other);
		IMatrix newMatrix = this.newInstance(this.getRowsCount(), other.getColsCount());
		for(int i = 0; i < newMatrix.getRowsCount(); i++) {
			for(int j = 0; j < newMatrix.getColsCount(); j++) {
				newMatrix.set(i, j, 0);
			}
		}
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < other.getColsCount(); j++) {
				for(int k = 0; k < this.getColsCount(); k++) {
					newMatrix.set(i, j, newMatrix.get(i, j) + this.get(i, k) * other.get(k, j));
				}
			}
		}
		return newMatrix;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#determinant()
	 * 
	 * Method computes the determinant of the matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double determinant() throws IncompatibleOperandException {
		if(this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException("Method determinant works only for square matrices.");
		}
		double det;
		int sign = 1;
		int size = this.getRowsCount();
		if (size == 1) {
			return this.get(0, 0);
		}
		else if (size == 2) {
			return this.get(0, 0)*this.get(1, 1) - this.get(1, 0)*this.get(0, 1);
		}
		else {
			det = 0;
			for (int i = 0; i < size; i++) {
				IMatrix subM = subMatrix(0, i, false);
				det += sign * this.get(0, i) * subM.determinant();
				sign = -sign;
			}
		}
		return det;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#subMatrix(int, int, boolean)
	 * 
	 * Method creates a sub-matrix of the current matrix. Depending on the given argument, it can be
	 * a live view of the current matrix or a completely new matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if(row < 0 || row > this.getRowsCount()) {
			throw new IncompatibleOperandException("Index of row to be removed is too great or too low.");
		}
		if(col < 0 || col > this.getColsCount()) {
			throw new IncompatibleOperandException("Index of column to be removed is too great or too low.");
		}
		IMatrix newMatrix;
		if(liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			newMatrix = newInstance(this.getRowsCount() - 1, this.getColsCount() - 1);
			int rowCounter = 0;
			for(int i = 0; i < this.getRowsCount(); i++) {
				if(i == row) {
					continue;
				}
				int colCounter = 0;
				for(int j = 0; j < this.getColsCount(); j++) {
					if(j == col) {
						continue;
					}
					newMatrix.set(rowCounter, colCounter, this.get(i, j));
					colCounter++;
				}
				rowCounter++;
			}
			return newMatrix;
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nInvert()
	 * 
	 * Method inverts the current matrix. Method returns a new matrix, the inverse of the current one.
	 * 
	 * This method can be found here http://www.mathwords.com/i/inverse_of_a_matrix.htm
	 * 
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix nInvert() {
		if(this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException("Method determinant works only for square matrices.");
		}
		if(this.determinant() == 0) {
			throw new IncompatibleOperandException("This matrix has no inverse.");
		}
		return this.cofactor().nScalarMultiply(1.0/this.determinant());
	}
	
	/**
	 * Method calculates the cofactor matrix of the current matrix.
	 * 
	 * @return returns the cofactor matrix
	 */
	private IMatrix cofactor() {
		IMatrix cofactorMatrix = newInstance(this.getRowsCount(), this.getColsCount());
	    for (int i = 0; i < cofactorMatrix.getRowsCount(); i++) {
	        for (int j = 0; j < cofactorMatrix.getColsCount(); j++) {
	        	cofactorMatrix.set(j, i, (int)(Math.pow(-1, i + j) * this.subMatrix(i, j, false).determinant()));
	        }
	    }
	    return cofactorMatrix;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#toArray()
	 * 
	 * Method creates and returns a 2D array filled with matrix elements.
	 */
	@Override
	public double[][] toArray() {
		double[][] newValue = new double[this.getRowsCount()][this.getColsCount()];
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < this.getColsCount(); j++) {
				newValue[i][j] = this.get(i, j);
			}
		}
		return newValue;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#toVector(boolean)
	 * 
	 * Method creates a vector out of a one-row or one-column matrix. The resulting vector can be
	 * a live view of the current matrix or a new instance of the Vector class, depending on the given
	 * argument.
	 * 
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IVector toVector(boolean liveView) {
		if(this.getRowsCount() != 1 || this.getColsCount() != 1) {
			throw new IncompatibleOperandException();
		}
		if(liveView) {
			return new VectorMatrixView(this);
		} else {
			if(this.getRowsCount() == 1) {
				return LinAlgDefaults.defaultVector(this.getColsCount());
			} else {
				return LinAlgDefaults.defaultVector(this.getRowsCount());
			}
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#nScalarMultiply(double)
	 * 
	 * Method multiplies the current matrix by a scalar. Method returns a new matrix, the
	 * resulting matrix.
	 */
	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#scalarMultiply(double)
	 * 
	 * Method multiplies the current matrix by a scalar. Method returns the modified current matrix.
	 */
	@Override
	public IMatrix scalarMultiply(double value) {
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < this.getColsCount(); j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.IMatrix#makeIdentity()
	 * 
	 * Method modifies the current matrix so that it is an identity matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix makeIdentity() {
		if(this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException("Identity matrix needs to be square.");
		}
		for(int i = 0; i < this.getRowsCount(); i++) {
			for(int j = 0; j < this.getColsCount(); j++) {
				if(i == j) {
					this.set(i, j, 1);
				} else {
					this.set(i, j, 0);
				}
			}
		}
		return this;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * 
	 * Creates a String representation of the matrix.
	 * 
	 * Format of a 3x3 matrix would be:
	 * 		[element1, element2, element3]
	 * 		[element4, element5, element6]
	 * 		[element7, element8, element9]
	 */
	public String toString() {
		return toString(3);
	}
	
	/**
	 * More specific version of the toString() method. Method specifies the precision of the
	 * printed matrix' elements.
	 * 
	 * Format of a 3x3 matrix would be:
	 * 		[element1, element2, element3]
	 * 		[element4, element5, element6]
	 * 		[element7, element8, element9]
	 * 
	 * @param precision the specified precision of the printed elements
	 * @return returns the string representation of the matrix with desired precision
	 */
	public String toString(int precision) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.getRowsCount(); i++) {
			sb.append("[");
			for(int j = 0; j < this.getColsCount(); j++) {
				if(j == this.getColsCount() - 1) {
					sb.append(String.format(Locale.ENGLISH, "%." + precision + "f]", this.get(i, j)));
				} else {
					sb.append(String.format(Locale.ENGLISH, "%." + precision + "f, ", this.get(i, j)));
				}
			}
			if(i != this.getRowsCount() - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Method used to check whether the number of rows and columns of both matrices is equal.
	 * 
	 * @param other the 2nd matrix
	 * @throws IncompatibleOperandException
	 */
	private void checkBasicCompatibility(IMatrix other) {
		if(this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException("Matrices are incompatible for adding/subtracting.");
		}
	}
	
	/**
	 * Method used to check whether the matrices are compatible for multiplication.
	 *  
	 * @param other the 2nd matrix
	 * @throws IncompatibleOperandException
	 */
	private void checkMulCompatibility(IMatrix other) {
		if(this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException("Matrices are incompatible for multiplying.");
		}
	}
}
