package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {
	
	
	
	@Test(expected=IncompatibleOperandException.class)
	public void getTestException1() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		v1.get(-2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void getTestException2() {
		IVector v1 = new Vector(new double[]{-2, 4, 1});
		v1.get(7);
	}
	
	@Test
	public void getTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		assertEquals("Expected 4.0", 4.0, v1.get(1), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void setTestException1() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		v1.set(-1, 2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void setTestException2() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		v1.set(7, 2);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void setTestException3() {
		IVector v1 = new Vector(true, true, new double[]{2, 3, 4});
		v1.set(1, 2);
	}
	
	@Test
	public void setTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		v1.set(1, 2);
		assertEquals("Expected 2.0", 2.0, v1.get(1), 1e-6);
	}
	
	@Test
	public void getDimensionTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		assertEquals("Expected 3", 3, v1.getDimension());
	}
	
	@Test
	public void copyTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = v1.copy();
		v2.set(1, 2);
		assertEquals("Expected 2.0", 2.0, v2.get(1), 1e-6);
		assertEquals("Expected 4.0", 4.0, v1.get(1), 1e-6);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void copyTestImmutableV1() {
		IVector v1 = new Vector(true, true, new double[]{2, 3, 4});
		IVector v2 = v1.copy();
		v2.set(1, 2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void newInstanceTestException() {
		IVector v1 = new Vector(true, true, new double[]{2, 3, 4});
		v1.newInstance(-2);
	}
	
	@Test
	public void newInstanceTest() {
		IVector v1 = new Vector(true, true, new double[]{2, 3, 4});
		IVector v2 = v1.newInstance(2);
		assertEquals("Expected 2", 2, v2.getDimension());
		assertEquals("Expected 0.0", 0.0, v2.get(1), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void parseTestException1() {
		Vector.parseSimple("");
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void parseTestException2() {
		Vector.parseSimple(null);
	}
	
	@Test
	public void copyPartTest() {
		 IVector v1 = Vector.parseSimple("-2 4 1");
		 IVector v2 = v1.copyPart(2);
		 IVector v3 = v1.copyPart(5);
		 System.out.println(v1);
		 System.out.println(v2);
		 System.out.println(((AbstractVector) v3).toString(5));
	}
}
