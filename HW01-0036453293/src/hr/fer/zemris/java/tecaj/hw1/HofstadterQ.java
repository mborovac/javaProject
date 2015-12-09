/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class for calculating the value of i-th number of Hofstadter's Q-sequence. The program accepts i as
 * a command line argument.
 * 
 * @author Marko
 * @version 1.0
 * 
 */
public class HofstadterQ {

	/**
	 * Method main which calls all other needed methods.
	 * @param args program takes one real number greater than 1
	 */
	public static void main(String[] args) {

		long i = Long.parseLong(args[0]);
		if(i <= 0) {
			System.err.println("Zadani i ne smije biti manji od 1!");
			System.exit(1);
		} else {
			long number = 0;
			number = Q(i);
			System.out.println("You requested calculation of " + args[0] + ". number of Hofstadter's Q-sequence. " +
					"The requested number is " + number + ".");
		}
	}
	
	/**
	 * Method for calculating-th number in Hofstadter's Q-sequence.
	 * @param i i-th number in Hofstadter's Q-sequence
	 * @return returns the value of i-th number in Hofstadter's Q-sequence
	 */
	static long Q(long i) {
		if(i == 1 || i == 2) {
			return 1;
		}
		return(Q(i - Q(i-1)) + Q(i - Q(i-2)));
	}
}
