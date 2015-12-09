/**
 * Package holding all the short programs for Java 7th homework
 */
package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

/**
 * Class representing a short program calculating the barycentric coordinates of a given point based on
 * a triangle. Triangle is represented by 3 vectors.  
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Prog2 {

	/**
	 * Method main used to execute the program.
	 * 
	 * @param args program takes no arguments, all will be ignored
	 */
	public static void main(String[] args) {
		
		IVector a = Vector.parseSimple("1 0 0");
		IVector b = Vector.parseSimple("5 0 0");
		IVector c = Vector.parseSimple("3 8 0");
		
		IVector t = Vector.parseSimple("3 4 0");
		
		double pov = b.nSub(a).nVectorProduct(c.nSub(a)).norm()/2.0;
		double povA = b.nSub(t).nVectorProduct(c.nSub(t)).norm()/2.0;
		double povB = a.nSub(t).nVectorProduct(c.nSub(t)).norm()/2.0;
		double povC = a.nSub(t).nVectorProduct(b.nSub(t)).norm()/2.0;
		
		double t1 = povA/pov;
		double t2 = povB/pov;
		double t3 = povC/pov;
		
		System.out.println("Baricentricne koordinate su: ("+t1+","+t2+","+t3+").");
		//(0.25,0.25,0.5)
	}
}
