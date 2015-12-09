/**
 * Package holding the demonstration class for QMC minimizer of Java 9th homework.
 */
package hr.fer.zemris.bool.qmc.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.qmc.QMCMinimizer;

/**
 * Class used to demonstrate the function minimization with the QMCMiinimizer.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class QMCDemo {
	
	/**
	 * Method main used to run the program.
	 * 
	 * @param args program takes no arguments, all arguments will be ignored
	 */
	public static void main(String[] args) {
		
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		
//		BooleanFunction bf = new IndexedBF(
//						"f1",
//						Arrays.asList(varA, varB, varC, varD),
//						true,
//						Arrays.asList(0,1,4,5,9,15),
//						Arrays.asList(11)
//						);
		BooleanFunction bf = new IndexedBF(
						"f1",
						Arrays.asList(varA, varB, varC, varD),
						true,
						Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
						new ArrayList<Integer>()
						);
		boolean želimoDobitiProdukte = false;
		long t0 = System.currentTimeMillis();
		List<MaskBasedBF> fje = QMCMinimizer.minimize(bf, želimoDobitiProdukte);
		long t1 = System.currentTimeMillis();
		System.out.println("Minimalnih oblika ima: " + fje.size());
		for(MaskBasedBF f : fje) {
			System.out.println("Mogući minimalni oblik:");
			for(Mask m : f.getMasks()) {
				System.out.println(" " + m);
			}
		}
		System.out.println("Vrijeme: " + (t1-t0)+" ms");
	}
}
