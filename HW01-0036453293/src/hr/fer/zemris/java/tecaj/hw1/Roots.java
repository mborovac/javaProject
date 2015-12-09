/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class for computing and printing all requested roots of a given complex number. Complex number is given as a
 * command-line argument.
 * 
 * @author Marko
 * @version 1.0
 * 
 */
public class Roots {

	/**
	 * Method main which calls all other needed methods.
	 * @param args three command-line arguments: real part of complex number, imaginary part of complex number
	 * and required root to calculate(natural number greater than 1)
	 */
	public static void main(String[] args) {
		
		if(args.length != 3) {
			System.err.println("The program requires 3 arguments!");
			System.exit(1);
		}
		double realPart = Double.parseDouble(args[0]);
		double imaginaryPart = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);
		
		if(root <= 1) {
			System.err.println("Required root needs to be a natural number greater than 1");
			System.exit(1);
		}
		if(Math.abs(realPart) < 1e-6 && Math.abs(imaginaryPart) < 1e-6) {
			System.err.println("Both real and imaginary part of a complex number can not be zero at the same time");
			System.exit(1);
		}
		
		double r = Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
		double theta = Math.atan2(imaginaryPart, realPart);
		double alpha = 0;
		double[] rootRealPart = new double[root];
		double[] rootImaginaryPart = new double[root];
		for(int i = 0; i < root; i++) {
			alpha = (theta + 2*Math.PI*i) / root;
			rootRealPart[i] = Math.pow(r, 1.0/root)*Math.cos(alpha);
			rootImaginaryPart[i] = Math.pow(r, 1.0/root)*Math.sin(alpha);
		}
		System.out.println("You requested calculation of " + root + ". roots. Solutions are:");
		for(int i = 0; i < root; i++) {
			if(rootImaginaryPart[i] < 0) {
				System.out.printf("%d) %f%fi\n", i+1, rootRealPart[i], rootImaginaryPart[i]);
			} else {
				System.out.printf("%d) %f+%fi\n", i+1, rootRealPart[i], rootImaginaryPart[i]);
			}
		}
	}

}
