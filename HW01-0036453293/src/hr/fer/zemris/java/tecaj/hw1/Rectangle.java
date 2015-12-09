/**
 * Package holding all the classes and methods for 1st Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for calculating rectangle's area and perimeter.
 * 
 * @author Marko
 * @version 1.0
 * 
 */
public class Rectangle {

	/**
	 * Method main which calls all other needed methods.
	 * @param args command line arguments, 2 real numbers: width and height of a rectangle
	 * @throws IOException thrown if BufferedReader can not be created
	 */
	public static void main(String[] args) throws IOException {
		
		double width = 0;
		double height = 0;
		double area = 0;
		double perimeter = 0;
		
		if(args.length > 0) {
			if(args.length != 2) {
				System.err.println("Invalid number of arguments was provided.");
				System.exit(1);
			}
			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
		} else {
				BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
			while(Math.abs(width) < 1E-6) {
				System.out.print("Please provide width: ");
				width = citajJedanRedak(reader, "Width");
			}
			while(Math.abs(height) < 1E-6) {
				System.out.print("Please provide height: ");
				height = citajJedanRedak(reader, "Height");
			}
		}
		area = width * height;
		perimeter = 2*(width + height);
		System.out.println("You have specified a rectangle with width " + width + " and height "
				+ height + ". Its area is " + area + " and its perimeter is " + perimeter);

	}
	
	/**
	 * Method for reading a single line from reader
	 * @param reader input stream
	 * @param element string to be printed by the method
	 * @return returns 0 if nothing was given, otherwise returns one number type double read from the stream
	 * @throws IOException
	 */
	static double citajJedanRedak(BufferedReader reader, String element) throws IOException {
		double broj = 0;
		String redak = reader.readLine();
		if(redak != null) {
			redak = redak.trim();
			if(redak.isEmpty()) {
				System.out.println("Nothing was given");
			} else {
				broj = Double.parseDouble(redak);
				if(broj < 0.0) {
					System.out.println(element + " is negative.");
					broj = 0.0;
				}
			}
		}
		return broj;
	}
}
