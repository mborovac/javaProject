package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void CheckArgumentTestException() {
		new ValueWrapper((long) 2.3);
	}
	
	@Test(expected=RuntimeException.class)
	public void convertStringToNumberTestException1() {
		ValueWrapper wrapper = new ValueWrapper("2.3asd");
		wrapper.increment(new Integer(5));
	}
	
	@Test(expected=RuntimeException.class)
	public void convertStringToNumberTestException2() {
		ValueWrapper wrapper = new ValueWrapper("2asd");
		wrapper.increment(new Integer(5));
	}
	
	@Test
	public void IncrementTest1() {
		ValueWrapper wrapper = new ValueWrapper("3.2");
		wrapper.increment(new Integer(5));
		assertEquals(8.2, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest2() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.increment(new Integer(5));
		assertEquals(5, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest3() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.increment(null);
		assertEquals(5, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest4() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.increment("2E1");
		assertEquals(25.0, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest5() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.increment(new Double(2.3));
		assertEquals(7.3, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest6() {
		ValueWrapper wrapper = new ValueWrapper(new Double(5.4));
		wrapper.increment(new Double(2.3));
		assertEquals(7.7, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest7() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.increment(new Integer(2));
		assertEquals(7, wrapper.getValue());
	}
	
	@Test
	public void IncrementTest8() {
		ValueWrapper wrapper = new ValueWrapper("");
		wrapper.setValue(5);
		wrapper.increment("3");
		assertEquals(8, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest1() {
		ValueWrapper wrapper = new ValueWrapper("3.0");
		wrapper.decrement(new Integer(5));
		assertEquals(-2.0, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest2() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.decrement(new Integer(5));
		assertEquals(-5, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest3() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.decrement(null);
		assertEquals(5, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest4() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.decrement("2E1");
		assertEquals(-15.0, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest5() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.decrement(new Double(2.3));
		assertEquals(2.7, wrapper.getValue());
	}
	
	@Test
	public void DecrementTest6() {
		ValueWrapper wrapper = new ValueWrapper(new Double(5.4));
		wrapper.decrement(new Double(2.3));
		assertEquals(3.1, (Double) wrapper.getValue(), 1e-6);
	}
	
	@Test
	public void DecrementTest7() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.decrement(new Integer(2));
		assertEquals(3, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest1() {
		ValueWrapper wrapper = new ValueWrapper("3.0");
		wrapper.multiply(new Integer(5));
		assertEquals(15.0, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest2() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.multiply(new Integer(5));
		assertEquals(0, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest3() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.multiply(null);
		assertEquals(0, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest4() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.multiply("2E1");
		assertEquals(100.0, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest5() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.multiply(new Double(2.3));
		assertEquals(11.5, wrapper.getValue());
	}
	
	@Test
	public void MultiplyTest6() {
		ValueWrapper wrapper = new ValueWrapper(new Double(5.4));
		wrapper.multiply(new Double(2.3));
		assertEquals(12.42, (Double) wrapper.getValue(), 1e-6);
	}
	
	@Test
	public void MultiplyTest7() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.multiply(new Integer(2));
		assertEquals(10, wrapper.getValue());
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void DivideTest1() {
		ValueWrapper wrapper = new ValueWrapper("15.0");
		wrapper.divide(new Integer(5));
		assertEquals(3.0, wrapper.getValue());
	}
	
	@Test
	public void DivideTest2() {
		ValueWrapper wrapper = new ValueWrapper(null);
		wrapper.divide(new Integer(5));
		assertEquals(0, wrapper.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void DivideTest3() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.divide(null);
//		assertEquals(0, wrapper.getValue());
	}
	
	@Test
	public void DivideTest4() {
		ValueWrapper wrapper = new ValueWrapper("2E1");
		wrapper.divide(new Integer(5));
		assertEquals(4.0, wrapper.getValue());
	}
	
	@Test
	public void DivideTest5() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.divide(new Double(2.5));
		assertEquals(2.0, wrapper.getValue());
	}
	
	@Test
	public void DivideTest6() {
		ValueWrapper wrapper = new ValueWrapper(new Double(4.4));
		wrapper.divide(new Double(2.2));
		assertEquals(2.0, wrapper.getValue());
	}
	
	@Test
	public void DivideTest7() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		wrapper.divide(new Integer(2));
		assertEquals(2, wrapper.getValue());
	}
	
	@Test
	public void NumCompareTest1() {
		ValueWrapper wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare(null));
	}
	
	@Test
	public void NumCompareTest2() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		assertEquals(5, wrapper.numCompare(null));
	}
	
	@Test
	public void NumCompareTest3() {
		ValueWrapper wrapper = new ValueWrapper(null);
		assertEquals(-2, wrapper.numCompare(new Integer(2)));
	}
	
	@Test
	public void NumCompareTest4() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		assertEquals(3, wrapper.numCompare(new Integer(2)));
	}
	
	@Test
	public void NumCompareTest5() {
		ValueWrapper wrapper = new ValueWrapper(new Double(5.0));
		assertEquals(3, wrapper.numCompare(new Integer(2)));
	}
	
	@Test
	public void NumCompareTest6() {
		ValueWrapper wrapper = new ValueWrapper(new Integer(5));
		assertEquals(3, wrapper.numCompare(new Double(2.0)));
	}
	
	@Test
	public void NumCompareTest7() {
		ValueWrapper wrapper = new ValueWrapper(new Double(5.0));
		assertEquals(3, wrapper.numCompare(new Double(2.0)));
	}
}
