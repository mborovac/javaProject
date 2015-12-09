/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * Class for calculating and printing the decomposition of a given number onto prime factors. This number is
 * given as a command-line argument.
 * 
 * @author Marko
 * @version 1.0
 *
 */
public class NumberDecomposition {

	/**
	 * Method main which calls all other needed methods.
	 * @param args single command-line argument: a natural number greater than 1
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.err.println("The program requires 1 argument!");
			System.exit(1);
		}
		int number = Integer.parseInt(args[0]);
		if(number <= 1) {
			System.err.println("Given number must be greater than 1!");
			System.exit(1);
		}
		int[] primeNumbersSmallerThanArgument = SieveOfErathostenes(number);
		int[] factors = new int[number];
		int counter = 0;
		for(int i: primeNumbersSmallerThanArgument) {
			while(number%i == 0) {
				number /= i;
				factors[counter] = i;
				counter++;
			}
		}
		System.out.println("You requested decomposition of number " + args[0] + " onto prime factors. Here they are:");
		for(int i = 0; i < factors.length; i++) {
			if(factors[i] != 0) {
				System.out.println((i+1) + ": " + factors[i]);
			}
		}
	}
	
	/**
	 * Method for calculating all the prime numbers that are smaller than the given number.
	 * @param n natural number greater than 1
	 * @return returns all the prime numbers that are smaller than the given number as a field of integers.
	 */
	static int[] SieveOfErathostenes(int n) {
		int[] poljeA = new int[n-1];
		int[] poljeB = new int[n-1];
		for(int i = 0; i < n-1; i++) {
			poljeA[i] = i+2;
		}
		boolean gotovo = false;
		int brojPrimBrojeva = 0;
		while(!gotovo) {
			for(int i = 0; i <= brojPrimBrojeva; i++) {
				poljeB[i] = poljeA[i];
			}
			for(int i = 0+brojPrimBrojeva+1, j = brojPrimBrojeva+1; i < poljeA.length; i++) {
				if(poljeA[i]%poljeA[brojPrimBrojeva] != 0) {
					poljeB[j] = poljeA[i];
					j++;
				}
			}
			brojPrimBrojeva++;
			int brojElemenataPoljaB = 0;
			for(int i = 0; i < poljeB.length; i++) {
				if(poljeB[i] != 0) {
					brojElemenataPoljaB++;
				}
			}
			if(brojElemenataPoljaB == brojPrimBrojeva) {
				gotovo = true;
			}
			poljeA = new int[brojElemenataPoljaB];
			for(int i = 0; i < brojElemenataPoljaB; i++) {
				poljeA[i] = poljeB[i];
			}
			poljeB = new int[brojElemenataPoljaB];
		}
		return poljeA;
	}
}
