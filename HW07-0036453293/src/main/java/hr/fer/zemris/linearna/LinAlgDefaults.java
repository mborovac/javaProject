/**
 * Package holding all the matrix and vector classes for java 7th homework
 */
package hr.fer.zemris.linearna;

/**
 * Class used to create default linear algebra classes. It should be used when 
 * the type of the class to be created is unknown or unspecified.
 * 
 * @author MarkoB
 * version 1.0
 */
public class LinAlgDefaults {
	
	/**
	 * Method creates an instance of the Matrix class.
	 * 
	 * @param rows number of rows
	 * @param cols number of columns
	 * @return returns a new instance of the Matrix class
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}
	
	/**
	 * Method creates an instance of the Vector class.
	 * 
	 * @param dimension dimension of the vector
	 * @return returns a new instance of the Vector class
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(false, true, new double[dimension]);
	}
}
