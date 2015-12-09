/**
 * Package holding all the classes and methods for 2nd and 3rd assignment of Java 4th HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class used to read user input (only numbers) until user types "Quit", save all the numbers, calculate the average
 * value and print out all the given numbers that are at least PERCENTAGE constant % greater than the average value.
 * The numbers are printed from the smallest one to the greatest one.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class AboveAverage {

	private static final double PERCENTAGE = 20;
	private static final double ONE_HUNDRED = 100;
	
	/**
	 * Class constructor.
	 */
	private AboveAverage() {
		
	}
	
	/**
	 * Method main executes the program. It takes no arguments, all passed arguments will be ignored.
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		List<Double> listOfGivenNumbers = new ArrayList<Double>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		String input;
		do {
			input = br.readLine();
			if(input == null) {
				continue;
			}
			input = input.trim();
			if(input.compareToIgnoreCase("Quit") == 0) {
				break;
			}
			if(!input.matches("[\\d]+[\\.]*[\\d]*")) {		// if input is not a number, integer or floating point
				System.out.println("I only accept numbers!");
				continue;
			}
			listOfGivenNumbers.add(Double.parseDouble(input));
		} while(true);
		double sum = 0;
		for(Double number: listOfGivenNumbers) {
			sum += number;
		}
		double average = sum/listOfGivenNumbers.size();
		double requirement = average*(1+PERCENTAGE/ONE_HUNDRED);
		Set<Double> aboveAverageNumbers = new TreeSet<Double>();
		for(double number: listOfGivenNumbers) {
			if(number >= requirement) {
				aboveAverageNumbers.add(number);
			}
		}
		for(double number: aboveAverageNumbers) {
			System.out.println(number);
		}
	}
}
