/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create and maintain objects representing a normal matrix.
 * Class extends AbstractMatrix class.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Matrix extends AbstractMatrix {
	
	protected int rows;
	protected int cols;
	protected double[][] elements;
	
	/**
	 * Class constructor. Constructor takes 2 parameters, dimensions of the matrix.
	 * 
	 * @param rows number of rows, an integer not lower than 1
	 * @param cols number of columns, an integer not lower than 1
	 */
	public Matrix(int rows, int cols) {
		this(rows, cols, new double[rows][cols], true);
	}
	
	/**
	 * Class constructor. Constructor takes 2 parameters, dimensions of the matrix, 
	 * a 2D array of elements and a flag determining whether the given array should be copied
	 * before use.
	 * 
	 * @param rows number of rows, an integer not lower than 1
	 * @param cols number of columns, an integer not lower than 1
	 * @param elements a 2D array of elements
	 * @param useGiven flag determining whether the given array should be copied
	 * before use; true = it can be used the way it is, false = it has to be copied
	 * @throws IncompatibleOperandException
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean useGiven) {
		if(rows < 1 || cols < 1) {
			throw new IncompatibleOperandException("Number of rows or columns can not be lower than 1");
		}
		
		if(rows != elements.length) {
			throw new IncompatibleOperandException("Number of rows does not match the given array of elements.");
		}
		if(cols != elements[0].length) {
			throw new IncompatibleOperandException("Number of columns does not match the given array of elements.");
		}
		this.rows = rows;
		this.cols = cols;
		if(useGiven) {
			this.elements = elements;
		} else {
			double[][] newElements = new double[rows][cols];
			for(int i = 0; i < this.rows; i++) {
				for(int j = 0; j < this.cols; j++) {
					newElements[i][j] = elements[i][j];
				}
			}
			this.elements = newElements;
		}
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getRowsCount()
	 * 
	 * Method returns number of rows.
	 */
	@Override
	public int getRowsCount() {
		return this.rows;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#getColsCount()
	 * 
	 * Method returns number of columns.
	 */
	@Override
	public int getColsCount() {
		return this.cols;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#get(int, int)
	 * 
	 * Method returns the element at the specific location
	 * @throws IncompatibleOperandException
	 */
	@Override
	public double get(int row, int col) {
		checkArguments(row, col);
		return this.elements[row][col];
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#set(int, int, double)
	 * 
	 * Method sets the element at the specific location to the desired value.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		checkArguments(row, col);
		this.elements[row][col] = value;
		return null;
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#copy()
	 * 
	 * Method creates a copy of the current matrix.
	 */
	@Override
	public IMatrix copy() {
		double[][] newValue = new double[this.rows][this.cols];
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.cols; j++) {
				newValue[i][j] = this.elements[i][j];
			}
		}
		return new Matrix(this.rows, this.cols, newValue, true);
	}
	
	/**
	 * @see hr.fer.zemris.linearna.AbstractMatrix#newInstance(int, int)
	 * 
	 * Method creates a new instance of the class Matrix with the given dimensions.
	 * Elements are set to 0.
	 * @throws IncompatibleOperandException
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		checkArguments(rows, cols);
		double[][] newValue = new double[rows][cols];
		return new Matrix(rows, cols, newValue, true);
	}
	
	/**
	 * Method for creating a matrix from a string.
	 * 
	 * Method itself doesn't check whether the given string contains illegal characters. If it does, some exception
	 * will probably be thrown, but nothing is guaranteed. Only string input guaranteed to work is matrix
	 * elements separated by at least one space character and matrix rows separated by a | character. Single row
	 * matrices are also fine, no row separator is needed then.
	 * 
	 * @param stringMatrix the string to be parsed
	 * @return returns a new instance of the Matrix class created from the given string
	 * @throws IncompatibleOperandException
	 */
	public static Matrix parseSimple(String stringMatrix) {
		if(stringMatrix.isEmpty()) {
			throw new IncompatibleOperandException();
		}
		String[] stringMatrixRows;
		if(!stringMatrix.contains("|")) {
			stringMatrixRows = new String[1];
			stringMatrixRows[0] = stringMatrix;
		} else {
			stringMatrixRows = stringMatrix.split("\\|");
		}
		int numberOfRows = stringMatrixRows.length;
		List<ArrayList<Double>> listOfElements = new ArrayList<ArrayList<Double>>(); 
		for(int i = 0; i < stringMatrixRows.length; i++) {
			String[] rowElements = stringMatrixRows[i].trim().split("\\s+");
			ArrayList<Double> oneRow = new ArrayList<>();
			for(int j = 0; j < rowElements.length; j++) {
				oneRow.add(Double.parseDouble(rowElements[j]));
			}
			listOfElements.add(oneRow);
		}
		int numberOfCols = listOfElements.get(0).size();
		for(int i = 0; i < listOfElements.size(); i++) {
			if(listOfElements.get(0).size() != numberOfCols) {
				throw new IncompatibleOperandException("Number of column elements needs to be the same " +
						"for every row.");
			}
		}
		double[][] elements = new double[numberOfRows][numberOfCols];
		for(int i = 0; i < listOfElements.size(); i++) {
			for(int j = 0; j < listOfElements.get(i).size(); j++) {
				elements[i][j] = listOfElements.get(i).get(j);
			}
		}
		return new Matrix(numberOfRows, numberOfCols, elements, true);
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
		if(row < 0 || row > this.rows) {
			throw new IncompatibleOperandException("Row is too great or too low.");
		}
		if(col < 0 || col > this.cols) {
			throw new IncompatibleOperandException("Column is too great or too low.");
		}
	}
}
