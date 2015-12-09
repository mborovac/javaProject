package hr.fer.zemris.java.sorters.demo;

import hr.fer.zemris.java.sorters.QSortParallel2;

import java.util.Random;

/**
 * Class used to demonstrate sorting with the QSortParallel2 class.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Glavni {
	
	/**
	 * Method main used to run the program.
	 * 
	 * @param args program takes no arguments, all arguments will be ignored
	 */
	public static void main(String[] args) {
		
		final int SIZE = 150_000;
		Random rand = new Random();
		int[] data = new int[SIZE];
		for(int i = 0; i < data.length; i++) {
		data[i] = rand.nextInt();
		}
		long t0 = System.currentTimeMillis();
		QSortParallel2.sort(data);
		long t1 = System.currentTimeMillis();
		System.out.println("Sortirano: " + QSortParallel2.isSorted(data));
		System.out.println("Vrijeme: " + (t1-t0)+" ms");
	}
}
