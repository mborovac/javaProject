/**
 * Package holding all the short programs for Java 7th homework
 */
package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Class representing a short program solving a system of equations.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Prog3 {

	/**
	 * Method main used to execute the program.
	 * 
	 * @param args program takes no arguments, all will be ignored
	 */
	public static void main(String[] args) {
		
		IMatrix a = Matrix.parseSimple("3 5 | 2 10");
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		System.out.println("Rjesenje sustava je: ");
		System.out.println(v);
	}
}
