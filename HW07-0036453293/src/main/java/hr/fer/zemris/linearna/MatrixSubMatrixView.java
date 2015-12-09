/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create and maintain a live view of a sub-matrix.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MatrixSubMatrixView extends AbstractMatrix {
	
	private IMatrix originalMatrix;
	private int[] removedRows;
	private int[] removedCols;
	
	/**
	 * Class constructor. Constructor takes 3 arguments, the original matrix and indexes
	 * of row and column that ARE NOT part of the sub-matrix.
	 * 
	 * This constructor doesn't delegate the job to the 2nd constructor because it would
	 * require creating the arrays of row and column indexes that ARE the part of the sub-matrix
	 * and later on the 2nd constructor would again create arrays of row and column indexes that ARE NOT
	 * the part of the sub-matrix. It is less time-consuming like this.
	 * 
	 * @param originalMatrix the original matrix
	 * @param rowIndex index of the original matrix row that isn't the part of the sub-matrix
	 * @param colIndex index of the original matrix column that isn't the part of the sub-matrix
	 * @throws IncompatibleOperandException
	 */
	public MatrixSubMatrixView(IMatrix originalMatrix, int rowIndex, int colIndex) {
		if(rowIndex < 0 || rowIndex > originalMatrix.getRowsCount() - 1) {
			throw new IncompatibleOperandException("Index of removed row is too great or too low.");
		}
		if(colIndex < 0 || colIndex > originalMatrix.getColsCount() - 1) {
			throw new IncompatibleOperandException("Index of removed column is too great or too low.");
		}
		this.originalMatrix = originalMatrix;
		this.removedRows = new int[]{rowIndex};
		this.removedCols = new int[]{colIndex};
	}
	
	/**
	 * Class constructor. Constructor takes 3 arguments, the original matrix and indexes
	 * of rows and columns that the sub-matrix is made of.
	 * 
	 * @param originalMatrix the original matrix
	 * @param rowIndex indexes of the original matrix rows that the sub-matrix is made of
	 * @param colIndex indexes of the original matrix columns that the sub-matrix is made of
	 * @throws IncompatibleOperandException
	 */
	public MatrixSubMatrixView(IMatrix originalMatrix, int[] rowIndexes, int[] colIndexes) {
		if(originalMatrix == null) {
			throw new IncompatibleOperandException();
		}
		for(int rowIndex: rowIndexes) {
			if(rowIndex < 0 || rowIndex > originalMatrix.getRowsCount() - 1) {
				throw new IncompatibleOperandException("Index of removed row is too great or too low.");
			}
		}
		for(int colIndex: colIndexes) {
			if(colIndex < 0 || colIndex > originalMatrix.getColsCount() - 1) {
				throw new IncompatibleOperandException("Index of removed column is too great or too low.");
			}
		}
		this.originalMatrix = originalMatrix;
		List<Integer> listOfRemovedRowIndexes = new ArrayList<>();
		List<Integer> listOfRowIndexes = new ArrayList<>();
		for(int i = 0; i < rowIndexes.length; i++) {
			listOfRowIndexes.add(rowIndexes[i]);
		}
		for(int i = 0; i < originalMatrix.getRowsCount(); i++) {
			if(!listOfRowIndexes.contains(i)) {
				listOfRemovedRowIndexes.add(i);
			}
		}
		List<Integer> listOfRemovedColIndexes = new ArrayList<>();
		List<Integer> listOfColIndexes = new ArrayList<>();
		for(int i = 0; i < colIndexes.length; i++) {
			listOfColIndexes.add(colIndexes[i]);
		}
		for(int i = 0; i < originalMatrix.getColsCount(); i++) {
			if(!listOfColIndexes.contains(i)) {
				listOfRemovedColIndexes.add(i);
			}
		}
		this.removedRows = new int[listOfRemovedRowIndexes.size()];
		for(int i = 0; i < listOfRemovedRowIndexes.size(); i++) {
			this.removedRows[i] = listOfRemovedRowIndexes.get(i);
		}
		this.removedCols = new int[listOfRemovedColIndexes.size()];
		for(int i = 0; i < listOfRemovedColIndexes.size(); i++) {
			this.removedCols[i] = listOfRemovedColIndexes.get(i);
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getRowsCount()
	 * 
	 * Method returns the number of rows of the sub-matrix.
	 */
	@Override
	public int getRowsCount() {
		return this.originalMatrix.getRowsCount() - this.removedRows.length;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getColsCount()
	 * 
	 * Method returns the number of columns of the sub-matrix.
	 */
	@Override
	public int getColsCount() {
		return this.originalMatrix.getColsCount() - this.removedCols.length;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#get(int, int)
	 * 
	 * Method returns the element at the specified position in the sub-matrix.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int row, int col) {
		checkArguments(row, col);
		int rowIndex = row;
		for(int removedRow: this.removedRows) {
			if(removedRow <= row) {
				rowIndex++;
			}
		}
		int colIndex = col;
		for(int removedCol: this.removedCols) {
			if(removedCol <= col) {
				colIndex++;
			}
		}
		return this.originalMatrix.get(rowIndex, colIndex);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#set(int, int, double)
	 * 
	 * Method sets the specified element of the sub-matrix to the given value.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		checkArguments(row, col);
		int rowIndex = row;
		for(int removedRow: this.removedRows) {
			if(removedRow <= row) {
				rowIndex++;
			}
		}
		int colIndex = col;
		for(int removedCol: this.removedCols) {
			if(removedCol <= col) {
				colIndex++;
			}
		}
		this.originalMatrix.set(rowIndex, colIndex, value);
		return this;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#copy()
	 * 
	 * Method returns the current sub-matrix. Creating a copy is pointless
	 * since both of the sub-matrices are live views of the same original matrix and
	 * change in one matrix affects all 3 matrices.
	 */
	@Override
	public IMatrix copy() {
		return this;
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
	 * @see hr.fer.zemris.linearna.AbstractMatrix#subMatrix(int, int, boolean)
	 * 
	 * Method creates a sub-matrix of this sub-matrix. It can be a live view of the
	 * current sub-matrix or an independent sub-matrix, depending on the given argument.
	 * @throws IncompatibleOperandException
	 */
	public IMatrix subMatrix(int rowIndex, int colIndex, boolean liveView) {
		if(rowIndex < 0 || rowIndex > this.getRowsCount()) {
			throw new IncompatibleOperandException("Index of row to be removed is too great or too low.");
		}
		if(colIndex < 0 || colIndex > this.getColsCount()) {
			throw new IncompatibleOperandException("Index of column to be removed is too great or too low.");
		}
		IMatrix newMatrix;
		if(liveView) {
			return new MatrixSubMatrixView(this, rowIndex, colIndex);
		} else {
			newMatrix = newInstance(this.getRowsCount() - 1, this.getColsCount() - 1);
			int rowCounter = 0;
			for(int i = 0; i < this.getRowsCount(); i++) {
				if(i == rowIndex) {
					continue;
				}
				int colCounter = 0;
				for(int j = 0; j < this.getColsCount(); j++) {
					if(j == colIndex) {
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
	 * Method used to check the arguments of another method. It only checks the 
	 * indexes of rows and columns for the matrix. 
	 * 
	 * @param row index of the row
	 * @param col index of the column
	 * @throws IncompatibleOperandException
	 */
	private void checkArguments(int row, int col) {
		if(row < 0 || row > this.originalMatrix.getRowsCount()) {
			throw new IncompatibleOperandException("Row is too great or too low.");
		}
		if(col < 0 || col > this.originalMatrix.getColsCount()) {
			throw new IncompatibleOperandException("Column is too great or too low.");
		}
	}
}
