package hr.fer.zemris.java.tecaj.hw3;

import static org.junit.Assert.*;
import org.junit.Test;

public class CStringTest {
	
	/**
	 * Given string is empty
	 */
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException1() {
		String string = "";
		new CString(string);
	}
	
	/**
	 * Offset is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException2() {
		char[] data = {'M','a','r','k','o'};
		CString c1 = new CString(data);
		c1.substring(-2, 3);
	}
	
	/**
	 * Offset is negative
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void constructorTestException3() {
		char[] data = {'M','a','r','k','o'};
		new CString(data, -1, 4);
	}
	
	/**
	 * Offset + length is too high
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void constructorTestException4() {
		char[] data = {'M','a','r','k','o'};
		new CString(data, 0, 9);
	}
	
	/**
	 * Parameter is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException5() {
		CString c1 = null;
		new CString(c1);
	}

	@Test
	public void constructorTest1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data, 2, 4);
		assertEquals("rkov", c1.toString());
	}
	
	@Test
	public void constructorTest2() {
		char[] data = {'M','a','r','k','o'};
		CString c1 = new CString(data);
		CString c2 = new CString(c1);
		assertEquals(c2.toString(), c1.toString());
	}
	
	@Test
	public void constructorTest3() {
		char[] data = {'M','a','r','k','o'};
		CString c1 = new CString(data);
		CString c2 = c1.substring(2, 4);
		CString c3 = new CString(c2);
		assertEquals(c2.toString(), c3.toString());
	}
	
	@Test
	public void lengthTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		int length = c1.length();
		assertEquals("Length should be 7", 7, length, 1e-6);
	}
	
	/**
	 * Index is negative
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void charAtTestException1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.charAt(-1);
	}
	
	/**
	 * Index is higher than the length of the string
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void charAtTestException2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.charAt(7);
	}
	
	@Test
	public void charAtTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		char requestedChar = c1.charAt(1);
		assertEquals("Requested chararter should be 'a'", 'a', requestedChar);
	}
	
	@Test
	public void toCharArrayTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		char[] requestedCharArray = c1.toCharArray();
		assertEquals("Requested chararter should be 'M'", 'M', requestedCharArray[0]);
		assertEquals("Requested chararter should be 'a'", 'a', requestedCharArray[1]);
		assertEquals("Requested chararter should be 'r'", 'r', requestedCharArray[2]);
		assertEquals("Requested chararter should be 'k'", 'k', requestedCharArray[3]);
		assertEquals("Requested chararter should be 'o'", 'o', requestedCharArray[4]);
		assertEquals("Requested chararter should be 'v'", 'v', requestedCharArray[5]);
		assertEquals("Requested chararter should be 'o'", 'o', requestedCharArray[6]);
	}
	
	@Test
	public void toStringTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		assertEquals("Method should print Markovo", "Markovo", c1.toString());
	}
	
	@Test
	public void indexOfTest1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		int requestedIndex = c1.indexOf('r');
		assertEquals("Index of requested char should be 2", 2, requestedIndex);
	}
	
	@Test
	public void indexOfTest2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		int requestedIndex = c1.indexOf('b');
		assertEquals("Index of requested char should be -1", -1, requestedIndex);
	}
	
	@Test
	public void indexOfTest3() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = c1.substring(3, 5);
		int requestedIndex = c2.indexOf('b');
		assertEquals("Index of requested char should be -1", -1, requestedIndex);
	}
	
	/**
	 * Argument string is longer than the current one
	 */
	@Test(expected=IllegalArgumentException.class)
	public void startsWithTestException() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'M','a','r'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		c2.startsWith(c1);
	}
	
	@Test
	public void startsWithTest1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'M','a','r'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1StartsWithc2 = c1.startsWith(c2);
		assertTrue(c1StartsWithc2);
	}
	
	@Test
	public void startsWithTest2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1StartsWithc2 = c1.startsWith(c2);
		assertFalse(c1StartsWithc2);
	}
	
	/**
	 * Argument string is longer than the current one
	 */
	@Test(expected=IllegalArgumentException.class)
	public void endsWithTestException() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'M','a','r'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		c2.endsWith(c1);
	}
	
	@Test
	public void endsWithTest1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1EndsWithc2 = c1.endsWith(c2);
		assertTrue(c1EndsWithc2);
	}
	
	@Test
	public void endsWithTest2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'M','a','r'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1EndsWithc2 = c1.endsWith(c2);
		assertFalse(c1EndsWithc2);
	}
	
	/**
	 * Argument string is longer than the current one
	 */
	@Test(expected=IllegalArgumentException.class)
	public void containsTestException() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		c2.contains(c1);
	}
	
	@Test
	public void containsTest1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1Containsc2 = c1.contains(c2);
		assertTrue(c1Containsc2);
	}
	
	@Test
	public void containsTest2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		char[] data2 = {'k','a', 'v', 'a'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1Containsc2 = c1.contains(c2);
		assertFalse(c1Containsc2);
	}
	
	@Test
	public void containsTest3() {
		char[] data = {'M','a','r','k','o', 'v'};
		char[] data2 = {'k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		boolean c1Containsc2 = c1.contains(c2);
		assertFalse(c1Containsc2);
	}
	
	/**
	 * Starting index is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void substringTestException1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.substring(-2, 5);
	}
	
	/**
	 * Ending index is lower than the starting one
	 */
	@Test(expected=IllegalArgumentException.class)
	public void substringTestException2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.substring(4, 2);
	}
	
	/**
	 * Ending index is greater than the length of the current string
	 */
	@Test(expected=IllegalArgumentException.class)
	public void substringTestException3() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.substring(4, 9);
	}
	
	@Test
	public void substringTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = c1.substring(2, 5);
		assertEquals(c2.length(), 3);
		assertEquals(c2.charAt(0), c1.charAt(2));
		assertEquals(c2.charAt(1), c1.charAt(3));
		assertEquals(c2.charAt(2), c1.charAt(4));
	}
	
	/**
	 * Index is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void leftTestException1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.left(-3);
	}
	
	/**
	 * Index is greater than the length of the current string
	 */
	@Test(expected=IllegalArgumentException.class)
	public void leftTestException2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.left(8);
	}
	
	@Test
	public void leftTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = c1.left(3);
		assertEquals(c2.length(), 3);
		assertEquals(c2.charAt(0), c1.charAt(0));
		assertEquals(c2.charAt(1), c1.charAt(1));
		assertEquals(c2.charAt(2), c1.charAt(2));
	}
	
	/**
	 * Index is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void rightTestException1() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.right(-3);
	}
	
	/**
	 * Index is greater than the length of the current string
	 */
	@Test(expected=IllegalArgumentException.class)
	public void rightTestException2() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		c1.right(8);
	}
	
	@Test
	public void rightTest() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = c1.right(3);
		assertEquals(c2.length(), 3);
		assertEquals(c2.charAt(0), c1.charAt(4));
		assertEquals(c2.charAt(1), c1.charAt(5));
		assertEquals(c2.charAt(2), c1.charAt(6));
	}
	
	/**
	 * Adding null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void addTestException() {
		char[] data = {'M','a','r','k','o', 'v', 'o'};
		CString c1 = new CString(data);
		CString c2 = null;
		c1.add(c2);
	}
	
	@Test
	public void addTest() {
		char[] data = {'M','a'};
		char[] data2 = {'r','k','o'};
		CString c1 = new CString(data);
		CString c2 = new CString(data2);
		CString c3 = c1.add(c2);
		assertEquals(c3.length(), 5);
		assertEquals(c3.charAt(0), c1.charAt(0));
		assertEquals(c3.charAt(1), c1.charAt(1));
		assertEquals(c3.charAt(2), c2.charAt(0));
		assertEquals(c3.charAt(3), c2.charAt(1));
		assertEquals(c3.charAt(4), c2.charAt(2));
	}
	
	@Test
	public void replaceAllCharTest() {
		CString c1 = new CString("MaMamaMAMaM");
		CString c4 = c1.replaceAll('M', 'T');
		assertEquals(c4.length(), 11);
		assertEquals(c4.charAt(0), 'T');
		assertEquals(c4.charAt(1), 'a');
		assertEquals(c4.charAt(2), 'T');
		assertEquals(c4.charAt(3), 'a');
		assertEquals(c4.charAt(4), 'm');
		assertEquals(c4.charAt(5), 'a');
		assertEquals(c4.charAt(6), 'T');
		assertEquals(c4.charAt(7), 'A');
		assertEquals(c4.charAt(8), 'T');
		assertEquals(c4.charAt(9), 'a');
		assertEquals(c4.charAt(10), 'T');
	}
	
	/**
	 * Old string is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void replaceAllStringTestException1() {
		CString c1 = new CString("MaMamaMAMaM");
		CString c2 = null;
		CString c3 = new CString("Ta");
		c1.replaceAll(c2, c3);
	}
	
	/**
	 * New string is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void replaceAllStringTestException2() {
		CString c1 = new CString("MaMamaMAMaM");
		CString c2 = new CString("Ma");
		CString c3 = null;
		c1.replaceAll(c2, c3);
	}
	
	/**
	 * New string is too big
	 */
	@Test(expected=IllegalArgumentException.class)
	public void replaceAllStringTestException3() {
		CString c1 = new CString("Ma");
		CString c2 = new CString("MaMa");
		CString c3 = new CString("Ta");
		c1.replaceAll(c2, c3);
	}
	
	@Test
	public void replaceAllStringTest() {
		CString c1 = new CString("MaMamaMAMaM");
		CString c2 = new CString("Ma");
		CString c3 = new CString("Ta");
		CString c4 = c1.replaceAll(c2, c3);
		assertEquals(c4.length(), 11);
		assertEquals(c4.charAt(0), 'T');
		assertEquals(c4.charAt(1), 'a');
		assertEquals(c4.charAt(2), 'T');
		assertEquals(c4.charAt(3), 'a');
		assertEquals(c4.charAt(4), 'm');
		assertEquals(c4.charAt(5), 'a');
		assertEquals(c4.charAt(6), 'M');
		assertEquals(c4.charAt(7), 'A');
		assertEquals(c4.charAt(8), 'T');
		assertEquals(c4.charAt(9), 'a');
		assertEquals(c4.charAt(10), 'M');
	}
	
//	@Test(expected=IllegalArgumentException.class)
//	public void indexOfStringTestException() {
//		CString c1 = new CString("Ma");
//		CString c2 = new CString("MaMa");
//		int indexOfString = c1.indexOfString(c2);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void indexOfStringTest() {
//		CString c1 = new CString("MaMa");
//		CString c2 = new CString("Ma");
//		int indexOfString = c1.indexOfString(c2);
//		assertEquals(indexOfString, 7);
//	}
}
