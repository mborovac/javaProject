/**
 * Package holding all the classes and methods for 3rd Java homework
 * 
 */
package hr.fer.zemris.java.tecaj.hw3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to create and maintain a complex number.
 * 
 * @author MarkoB
 * @version 1.0
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, both doubles.
	 * 
	 * @param real real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Factory method for creating a complex number out of the real part.
	 * 
	 * @param real real (and only) part of the complex number
	 * @return returns a new complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Factory method for creating a complex number out of the imaginary part.
	 * 
	 * @param imaginary imaginary (and only) part of the complex number, only the number, not the "i"
	 * @return returns a new complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Factory method for creating a complex number out of its magnitude and angle. Both parameters are doubles.
	 * 
	 * @param magnitude magnitude of the new complex number
	 * @param angle angle of the new complex number, must be in radians, to convert degrees to radians multiply
	 * them by (π/180)
	 * @return returns a new complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Factory method for creating a complex number out of a string.
	 * 
	 * @param s string from which the complex number is to be created, can not be an empty string, must only contain
	 * double numbers and an "i" to indicate the imaginary part of the number
	 * @return returns a new complex number
	 * @throws IllegalArgumentException
	 */
	public static ComplexNumber parse(String s) {
		if(s.isEmpty()) {
			throw new IllegalArgumentException("Can not create a complex number out of an empty string!");
		}
		String[] stringArray = s.split("\\+|\\-");
		if(stringArray.length > 3) {
			throw new IllegalArgumentException("Complex number has only 2 parts!");
		}
		String givenString = s.replaceAll(" ", "");
		double real = 0;
		double imaginary = 0;
		if(givenString.contains("i")) {
			if(givenString.matches("(\\+[0-9]+[\\.]*[0-9]*i)|([0-9]+[\\.]*[0-9]*i)|(\\-[0-9]+[\\.]*[0-9]*i)")) {
				// only imaginary part was given in the string
				real = 0;
				String imaginaryPart = givenString.trim();
				imaginaryPart = imaginaryPart.substring(0, imaginaryPart.length() - 1);
				imaginary = Double.parseDouble(imaginaryPart);
			} else {									// both real and imaginary part were given in the string
				Pattern p = Pattern.compile("(\\-[0-9]+[\\.]*[0-9]*i)|(\\+[0-9]+[\\.]*[0-9]*i)|([0-9]+[\\.]*[0-9]*)|"
						+ "(\\-[0-9]+[\\.]*[0-9]*)");
				Matcher m = p.matcher(givenString);
				int matches = 0;
				while(m.find()) {
					matches++;
					if(matches == 1) {
						if(m.group().contains("i")) {
							throw new IllegalArgumentException("Given string does not form a valid complex number");
						}
						real = Double.parseDouble(m.group());
					}
					if(matches == 2) {
						if(!m.group().contains("i")) {
							throw new IllegalArgumentException("Given string does not form a valid complex number");
						}
						String imaginaryPart = m.group().trim();
						imaginaryPart = imaginaryPart.substring(0, imaginaryPart.length() - 1);
						imaginary = Double.parseDouble(imaginaryPart);
					}
				}
			}
		} else if(givenString.matches("[0-9]+[\\.]*[0-9]*|\\-[0-9]+[\\.]*[0-9]*")){	
			// only real part was given in the string
			real = Double.parseDouble(givenString);
			imaginary = 0;
		} else {
			throw new IllegalArgumentException("Can not form a complex number out of the given string");
		}
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Method for acquiring the real part of the complex number.
	 * 
	 * @return returns the value of the real part of the complex number, a double value
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Method for acquiring the imaginary part of the complex number.
	 * 
	 * @return returns the value of the imaginary part of the complex number, a double value, without the "i"
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * Method for acquiring the magnitude of the complex number.
	 * 
	 * @return returns the magnitude of the complex number, a double value
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Method for acquiring the angle of the complex number. The angle is in radians. To convert radians to
	 * degrees multiply them by (180/π)
	 * 
	 * @return returns the angle of the complex number in radians
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}
	
	/**
	 * Method for adding a complex number to the current complex number. Equivalent to the "+" operator.
	 * 
	 * @param c complex number to be added, can not be null
	 * @return returns a new complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Given complex number can not be null");
		}
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Method for subtracting another complex number from the current complex number. Equivalent to the "-" operator.
	 * 
	 * @param c complex number to be subtracted, can not be null
	 * @return returns a new complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Given complex number can not be null");
		}
		ComplexNumber newComplexNumber = new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
		return newComplexNumber;
	}
	
	/**
	 * Method for multiplying the current complex number by another complex number. Equivalent to the "*" operator.
	 * 
	 * @param c complex number to be multiplied by, can not be null
	 * @return returns a new complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Given complex number can not be null");
		}
		ComplexNumber newComplexNumber = new ComplexNumber((this.real * c.real) - (this.imaginary * c.imaginary), 
				(this.real * c.imaginary) + (this.imaginary * c.real));
		return newComplexNumber;
	}
	
	/**
	 * Method for dividing the current complex number by another complex number. Equivalent to the "/" operator.
	 * 
	 * @param c complex number to be divided by, can not be null
	 * @return returns a new complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("Given complex number can not be null");
		}
		if(c.real < 1e-6 && c.imaginary < 1e-6) {
			throw new IllegalArgumentException("Can not divide by 0");
		}
		double denominator = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		ComplexNumber conjugate = new ComplexNumber(c.real, -c.imaginary);
		ComplexNumber numerator = this.mul(conjugate);
		ComplexNumber newComplexNumber = new ComplexNumber(numerator.real / denominator, 
				numerator.imaginary / denominator);
		return newComplexNumber;
	}
	
	/**
	 * Method for raising the current complex number to the given power.
	 * 
	 * @param n power to be raised to, can not be lower than 0
	 * @return returns a new complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Requested power must not be lower than 0! " + n);
		}
		if(n == 0) {
			return new ComplexNumber(1, 0);
		}
		ComplexNumber newComplexNumber = new ComplexNumber(this.real, this.imaginary);
		for(int i = 1; i < n; i++) {
			newComplexNumber = newComplexNumber.mul(this);
		}
		return newComplexNumber;
	}
	
	/**
	 * Method for calculating the roots of the current complex number.
	 * 
	 * @param n root to be calculated, higher the n, more the complex numbers the method will return, can not be 
	 * lower than 1
	 * @return returns an array of new complex numbers which are the result of the operation, index 0 is the 
	 * principal root
	 * @throws IllegalArgumentException
	 */
	public ComplexNumber[] root(int n) {
		if(n < 1) {
			throw new IllegalArgumentException("Requested root needs to be a natural number greater than 1! " + n);
		}
		ComplexNumber[] arrayOfNewComplexNumbers = new ComplexNumber[n];
		if(n == 1) {
			arrayOfNewComplexNumbers[0] = this;
			return arrayOfNewComplexNumbers;
		}
		double magnitude = this.getMagnitude();
		double theta = this.getAngle();
		double alpha = 0;
		double[] rootRealPart = new double[n];
		double[] rootImaginaryPart = new double[n];
		for(int i = 0; i < n; i++) {
			alpha = (theta + 2*Math.PI*i) / n;
			rootRealPart[i] = Math.pow(magnitude, 1.0/n)*Math.cos(alpha);
			rootImaginaryPart[i] = Math.pow(magnitude, 1.0/n)*Math.sin(alpha);
		}
		for(int i = 0; i < n; i++) {
			ComplexNumber newComplexNumber = new ComplexNumber(rootRealPart[i], rootImaginaryPart[i]);
			arrayOfNewComplexNumbers[i] = newComplexNumber;
		}
		return arrayOfNewComplexNumbers;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * Method returns a string representing the complex number in the form of realPart + imaginaryPart"i" 
	 * (example: "2-3i")
	 */
	public String toString() {
		String returnString;
		if(this.real == 0) {
			returnString = Double.toString(this.imaginary) + "i";
			return returnString;
		}
		if(this.imaginary == 0) {
			returnString = Double.toString(this.real);
			return returnString;
		}
		if(this.imaginary < 0) {
			returnString = Double.toString(this.real) + Double.toString(this.imaginary) + "i";
		} else {
			returnString = Double.toString(this.real) + "+" + Double.toString(this.imaginary) + "i";
		}
		return returnString;
	}
}
