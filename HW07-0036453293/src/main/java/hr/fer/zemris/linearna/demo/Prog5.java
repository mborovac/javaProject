/**
 * Package holding all the short programs for Java 7th homework
 */
package hr.fer.zemris.linearna.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.linearna.AbstractVector;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

/**
 * Class representing a short program used to calculate a reflected vector.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Prog5 {

	/**
	 * Method main used to execute the program. Program takes 2 arguments, the vectors to be used.
	 * If user doesn't provide the vectors as arguments the program asks user to input the vectors.
	 * 
	 * @param args the vectors to be used
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		AbstractVector n;
		AbstractVector m;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		if(args.length != 0) {
			if(args.length != 2) {
				throw new IncompatibleOperandException("Program takes 2 arguments.");
			}
			else {
				n = Vector.parseSimple(args[0]);
				m = Vector.parseSimple(args[1]);
			}
		} else {
			System.out.println("Please input 1st vector: ");
			n = Vector.parseSimple(br.readLine());
			System.out.println("Please input 2nd vector: ");
			m = Vector.parseSimple(br.readLine());
		}
		double a = m.scalarProduct(n)/n.norm();
		System.out.println("norm: " + n.norm());
		System.out.println(a);
		IVector b = (n.nScalarMultiply(1.0/n.norm()));
		System.out.println(b);
		IVector ba = b.nScalarMultiply(a).nScalarMultiply(2.0);
		System.out.println(ba);
		IVector r = ba.sub(m);
		System.out.println("Result: ");
		System.out.println(r);
	}
}
