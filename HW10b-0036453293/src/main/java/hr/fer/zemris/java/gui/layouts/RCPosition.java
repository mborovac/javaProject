package hr.fer.zemris.java.gui.layouts;

/**
 * Class used to represent the constraint and the position of an element in the CalcLayout.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RCPosition {
	
	private int row;
	private int column;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, index of the row and index of the column the 
	 * element should be placed at.
	 * 
	 * @param row index of the row
	 * @param column index of the column
	 */
	public RCPosition(int row, int column) {
		if(row < 1 || row > 7) {
			throw new IllegalArgumentException("Number of rows can not be lower than 1 or greater than 7.");
		}
		if(column < 1) {
			throw new IllegalArgumentException("Number of columns can not be lower than 1 or greater than 7.");
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Row getter.
	 * 
	 * @return returns index of row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Column getter.
	 * 
	 * @return returns index of column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof RCPosition)) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if(column != other.column) {
			return false;
		}
		if(row != other.row) {
			return false;
		}
		return true;
	}
}
