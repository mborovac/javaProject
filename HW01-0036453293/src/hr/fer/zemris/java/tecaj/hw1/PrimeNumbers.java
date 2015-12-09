/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class for computing and printing first n prime numbers. N is given as a command-line argument.
 * 
 * @author Marko
 * @version 1.0
 *
 */
public class PrimeNumbers {

	/**
	 * Method main which calls all other needed methods.
	 * @param args single command-line argument, one natural number
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Program requires 1 argument!");
			System.exit(1);
		}
		int n = Integer.parseInt(args[0]);
		if(n <= 0) {
			System.err.println("Argument must be greater than 0");
			System.exit(1);
		}
		int[] primeNumbers = new int[n];
		primeNumbers[0] = 2;
		int primeNumberCounter = 1;
		int numberToCheck = 3;
		while(primeNumberCounter < n) {
			boolean prime = true;
			for(int i = 0; i < primeNumberCounter; i++) {
				if(numberToCheck%primeNumbers[i] == 0) {
					prime = false;
				}
			}
			if(prime) {
				primeNumbers[primeNumberCounter] = numberToCheck;
				primeNumberCounter++;
			}
			numberToCheck++;
		}
		System.out.println("You requested calculation of " + n + " prime number(s). Here they are:");
		for(int i = 0; i < primeNumbers.length; i++) {
			System.out.println((i+1) + ". " + primeNumbers[i]);
		}
	}
}
