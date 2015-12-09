package hr.fer.zemris.java.tecaj.hw3;

import static org.junit.Assert.*;
import org.junit.Test;

public class ComplexNumberTest {
	
	@Test
	public void fromRealTest() {
		ComplexNumber c1 = ComplexNumber.fromReal(2.1);
		assertEquals("Complex number created only from real part 2.1 should be 2.1", "2.1", c1.toString());
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(2.1);
		assertEquals("Complex number created only from imaginary part 2.1 should be 2.1i", "2.1i", c1.toString());
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2, 0);
		assertEquals("Complex number created from magnitude 2 and angle 0 should be 2.0", "2.0", c1.toString());
	}
	
	/**
	 * String is empty
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException1() {
		ComplexNumber.parse("");
	}
	
	/**
	 * String has too many "+" or "-" operators
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException2() {
		ComplexNumber.parse("+2+3+4i");
	}
	
	/**
	 * String is ill-formed, with i
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException3() {
		ComplexNumber.parse("a3ki4la");
	}
	
	/**
	 * String is ill-formed, without i
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException4() {
		ComplexNumber.parse("a3k4la");
	}
	
	/**
	 * Real and imaginary parts were given in the wrong order
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException5() {
		ComplexNumber.parse("-3.41i-3.893");
	}
	
	/**
	 * 2 real parts
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException6() {
		ComplexNumber.parse("-3.41-3.893");
	}
	
	/**
	 * 2 imaginary parts
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTestException7() {
		ComplexNumber.parse("-3.41i-3.893i");
	}
	
	@Test
	public void parseTestOnlyRealPartWasGiven() {
		ComplexNumber c1 = ComplexNumber.parse("-2.98");
		assertEquals("Expected: -2.98", "-2.98", c1.toString());
	}
	
	@Test
	public void parseTestOnlyImaginaryPartWasGiven() {
		ComplexNumber c1 = ComplexNumber.parse("-3.893i");
		assertEquals("Expected: -3.893i", "-3.893i", c1.toString());
	}
	
	@Test
	public void parseTestBothPartsWereGiven() {
		ComplexNumber c1 = ComplexNumber.parse("-3.41-3.893i");
		assertEquals("Expected: -3.41-3.893i", "-3.41-3.893i", c1.toString());
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void GivenNumberIsInvalid() {
//		ComplexNumber.parse("-3.41-3.98-3.893i");
//	}
	
	@Test
	public void getRealTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		double realPart = c1.getReal();
		assertEquals("Expected: 2.0", 2.0, realPart, 1e-6);
	}
	
	@Test
	public void getImaginaryTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		double imaginaryPart = c1.getImaginary();
		assertEquals("Expected: 3.0", 3.0, imaginaryPart, 1e-6);
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		double magnitude = c1.getMagnitude();
		assertEquals("Expected: sqrt(13.0)", 3.6055513, magnitude, 1e-6);
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		double magnitude = c1.getAngle();
		assertEquals("Expected: tan^(-1)(3/2)", 0.9827937, magnitude, 1e-6);
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		ComplexNumber c3 = c2.add(c1);
		assertEquals("Expected: 5.0+7.0i", "5.0+7.0i", c3.toString());
	}
	
	/**
	 * Adding null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void addTestException() {
		ComplexNumber c1 = null;
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		c2.add(c1);
	}
	
	@Test
	public void subtractTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		ComplexNumber c3 = c2.sub(c1);
		assertEquals("Expected: 1.0+1.0i", "1.0+1.0i", c3.toString());
	}
	
	/**
	 * Subtracting null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void subtractTestException() {
		ComplexNumber c1 = null;
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		c2.sub(c1);
	}
	
	@Test
	public void multiplyTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		ComplexNumber c3 = c2.mul(c1);
		assertEquals("Expected: -6.0+17.0i", "-6.0+17.0i", c3.toString());
	}
	
	/**
	 * Multiplying by null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void multiplyTestException() {
		ComplexNumber c1 = null;
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		c2.mul(c1);
	}
	
	@Test
	public void divideTest() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		ComplexNumber c3 = c2.div(c1);
		assertEquals("Expected: 1.38461538461538", 1.38461538461538, 
				c3.getReal(), 1e-6);
		assertEquals("Expected: -0.0769230769230769", -0.0769230769230769, 
				c3.getImaginary(), 1e-6);
	}
	
	/**
	 * Dividing by null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void divideTestException1() {
		ComplexNumber c1 = null;
		ComplexNumber c2 = ComplexNumber.parse("3+4i");
		c2.div(c1);
	}
	
	/**
	 * Dividing by 0, both parts are 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void divideTestException2() {
		ComplexNumber c1 = ComplexNumber.parse("3+4i");
		ComplexNumber c2 = ComplexNumber.parse("0+0i");
		c1.div(c2);
	}
	
	/**
	 * Dividing by 0, imaginary is 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void divideTestException3() {
		ComplexNumber c1 = ComplexNumber.parse("3+4i");
		ComplexNumber c2 = ComplexNumber.parse("0i");
		c1.div(c2);
	}
	
	/**
	 * Power is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void powerTestException() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		c1.power(-1);
	}
	
	@Test
	public void powerTest1() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = c1.power(0);
		assertEquals("Expected: 1.0", "1.0", c2.toString());
	}
	
	@Test
	public void powerTest2() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = c1.power(1);
		assertEquals("Expected: 2.0+3.0i", "2.0+3.0i", c2.toString());
	}
	
	@Test
	public void powerTest3() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber c2 = c1.power(3);
		assertEquals("Expected: -46", -46.0, c2.getReal(), 1e-6);
		assertEquals("Expected: -9.00000000000001", 9.00000000000001, c2.getImaginary(), 1e-6);
	}
	
	/**
	 * Root is lower than 1
	 */
	@Test(expected=IllegalArgumentException.class)
	public void rootTestException() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		c1.root(0);
	}
	
	@Test
	public void rootTest1() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber[] arrayOfRoots = c1.root(3);
		assertEquals("Expected: 1.4519", 1.45185661, arrayOfRoots[0].getReal(), 1e-6);
		assertEquals("Expected: 0.4934",0.49340353, arrayOfRoots[0].getImaginary(), 1e-6);
		assertEquals("Expected: -1.1532", -1.15322830, arrayOfRoots[1].getReal(), 1e-6);
		assertEquals("Expected: 1.0106", 1.01064294, arrayOfRoots[1].getImaginary(), 1e-6);
		assertEquals("Expected: -0.2986", -0.29862831, arrayOfRoots[2].getReal(), 1e-6);
		assertEquals("Expected: -1.5040", -1.50404648, arrayOfRoots[2].getImaginary(), 1e-6);
	}
	
	@Test
	public void rootTest2() {
		ComplexNumber c1 = ComplexNumber.parse("2+3i");
		ComplexNumber[] arrayOfRoots = c1.root(1);
		assertEquals("Expected: 2.0", 2.0, arrayOfRoots[0].getReal(), 1e-6);
		assertEquals("Expected: 3.0", 3.0, arrayOfRoots[0].getImaginary(), 1e-6);
	}
}
