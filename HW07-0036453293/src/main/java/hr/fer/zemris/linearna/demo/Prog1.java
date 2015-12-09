/**
 * Package holding all the short programs for Java 7th homework
 */
package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Class representing a short program creating a live view of a transposed matrix.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Prog1 {

	/**
	 * Method main used to execute the program.
	 * 
	 * @param args program takes no arguments, all will be ignored
	 */
	public static void main(String[] args) {
		IMatrix m1 = Matrix.parseSimple("1 2 3 | 4 5 6");
		IMatrix m2 = m1.nTranspose(true);
		System.out.println("m1:");
		System.out.println(m1.toString());
		System.out.println("m2:");
		System.out.println(m2.toString());
		System.out.println();
		m2.set(2, 1, 9);
		System.out.println("m1:");
		System.out.println(m1.toString());
		System.out.println("m2:");
		System.out.println(m2.toString());
	}
}
