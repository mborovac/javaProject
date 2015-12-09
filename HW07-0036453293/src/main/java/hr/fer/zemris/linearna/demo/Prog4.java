/**
 * Package holding all the short programs for Java 7th homework
 */
package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Class representing a short program calculating the barycentric coordinates of a given point based on
 * a triangle. Triangle is represented by 3 vectors. This is an alternate approach to the 2nd programs approach.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Prog4 {

	/**
	 * Method main used to execute the program.
	 * 
	 * @param args program takes no arguments, all will be ignored
	 */
	public static void main(String[] args) {
		
		IMatrix a = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");
		
		IMatrix point = Matrix.parseSimple("3 | 4 | 1");
		
		IMatrix t = a.nInvert().nMultiply(point);
		System.out.println("Baricentricne koordinate su: ");
		System.out.println(t);
	}
}
