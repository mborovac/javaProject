/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

/**
 * Class used to create and maintain a live view of a transposed matrix.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MatrixTransposeView extends AbstractMatrix {
	
	private IMatrix originalMatrix;
	
	/**
	 * Class constructor. Constructor takes a single argument, the original matrix.
	 * 
	 * @param originalMatrix the original matrix
	 * @throws IncompatibleOperandException
	 */
	public MatrixTransposeView(IMatrix originalMatrix) {
		if(originalMatrix == null) {
			throw new IncompatibleOperandException("Can not create a live view of a null element.");
		}
		this.originalMatrix = originalMatrix;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getRowsCount()
	 * 
	 * Method returns the number of rows of the transposed matrix.
	 */
	@Override
	public int getRowsCount() {
		return this.originalMatrix.getColsCount();
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getColsCount()
	 * 
	 * Method returns the number of columns of the transposed matrix.
	 */
	@Override
	public int getColsCount() {
		return this.originalMatrix.getRowsCount();
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#get(int, int)
	 * 
	 * Method returns the element at the specified position in the transposed matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int row, int col) {
		checkArguments(row, col);
		return this.originalMatrix.get(col, row);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#set(int, int, double)
	 * 
	 * Method sets the specified element of the transposed matrix to the given value.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		checkArguments(row, col);
		return this.originalMatrix.set(col, row, value);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#copy()
	 * 
	 * Method returns a copy of the current matrix.
	 */
	@Override
	public IMatrix copy() {
		return new MatrixTransposeView(originalMatrix);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#newInstance(int, int)
	 * 
	 * Method creates a new instance of the original matrix.
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return this.originalMatrix.newInstance(rows, cols);
	}
	
	/**
	 * Method used to check the arguments of another method. It only checks the 
	 * indexes of rows and columns for the matrix. 
	 * 
	 * @param row index of the row
	 * @param col index of the column
	 * @throws IncompatibleOperandException
	 */
	private void checkArguments(int row, int col) {
		if(row < 0 || row > this.originalMatrix.getColsCount()) {
			throw new IncompatibleOperandException("Row is too great or too low.");
		}
		if(col < 0 || col > this.originalMatrix.getRowsCount()) {
			throw new IncompatibleOperandException("Column is too great or too low.");
		}
	}
}
