/**
 * Package holding all the classes and methods for 3rd Java homework
 */
package hr.fer.zemris.java.tecaj.hw3;

/**
 * Class used to test all the classes created as part of the 3rd Java homework.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class HW3Main {
	
	private HW3Main() {
	      //not called
	   }

	/**
	 * Main method which executes the program. Program takes no arguments.
	 * 
	 * @param args program requires no arguments, all the arguments passed to it will be ignored
	 */
	public static void main(String[] args) {
		
		complexNumberTester();
		cstringTester();
		integerSequenceTester();
	}
	
	/**
	 * Method for executing a simple code used to test the class ComplexNumber.
	 */
	private static void complexNumberTester() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
	
	/**
	 * Method for executing a simple code used to test the class CString.
	 */
	private static void cstringTester() {
		System.out.println(new CString("ananas").replaceAll(new CString("ana"), new CString("mirko")));
	}
	
	/**
	 * Method for executing a simple code used to test the class IntegerSequence.
	 */
	private static void integerSequenceTester() {
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		for(int i : range) {
			for(int j : range) {
				System.out.println("i="+i+", j="+j);
			}
		}
	}
}
