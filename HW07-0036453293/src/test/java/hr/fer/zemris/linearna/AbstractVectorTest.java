package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractVectorTest {
	
	@Test
	public void copyPartTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = v1.copyPart(2);
		assertEquals("Expected 2", 2, v2.getDimension());
		assertEquals("Expected -2.0", -2.0, v2.get(0), 1e-6);
		IVector v3 = v1.copyPart(5);
		assertEquals("Expected 5", 5, v3.getDimension());
		assertEquals("Expected 0.0", 0.0, v3.get(4), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void addTestException() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3");
		v1.add(v2);
	}
	
	@Test
	public void addTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3 4 2");
		IVector v3 = v1.add(v2);
		assertEquals("Expected 1.0", 1.0, v3.get(0), 1e-6);
		assertEquals("Expected 1.0", 1.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void nAddTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3 4 2");
		IVector v3 = v1.nAdd(v2);
		assertEquals("Expected 1.0", 1.0, v3.get(0), 1e-6);
		assertEquals("Expected -2.0", -2.0, v1.get(0), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void subTestException() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3");
		v1.sub(v2);
	}
	
	@Test
	public void subTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3 4 2");
		IVector v3 = v1.sub(v2);
		assertEquals("Expected -5.0", -5.0, v3.get(0), 1e-6);
		assertEquals("Expected -5.0", -5.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void nSubTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = Vector.parseSimple("3 4 2");
		IVector v3 = v1.nSub(v2);
		assertEquals("Expected -5.0", -5.0, v3.get(0), 1e-6);
		assertEquals("Expected -2.0", -2.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void scalarMultiplyTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = v1.scalarMultiply(2);
		assertEquals("Expected -4.0", -4.0, v2.get(0), 1e-6);
		assertEquals("Expected -4.0", -4.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void nScalarMultiplyTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		IVector v2 = v1.nScalarMultiply(2);
		assertEquals("Expected -4.0", -4.0, v2.get(0), 1e-6);
		assertEquals("Expected -2.0", -2.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void normTest() {
		IVector v1 = Vector.parseSimple("-2 4 1");
		assertEquals("Expected sqrt(21)", Math.sqrt(21), v1.norm(), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void normalizeTestException() {
		IVector v1 = Vector.parseSimple("0 0 0");
		v1.normalize();
	}
	
	@Test
	public void normalizeTest() {
		IVector v1 = Vector.parseSimple("2 2 1");
		IVector v2 = v1.normalize();
		assertEquals("Expected 2/3", 2.0/3.0, v2.get(0), 1e-6);
		assertEquals("Expected 2/3", 2.0/3.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void nNormalizeTest() {
		IVector v1 = Vector.parseSimple("2 2 1");
		IVector v2 = v1.nNormalize();
		assertEquals("Expected 2/3", 2.0/3.0, v2.get(0), 1e-6);
		assertEquals("Expected 2.0", 2.0, v1.get(0), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void cosineTestException1() {
		IVector v1 = Vector.parseSimple("0 0 0");
		IVector v2 = Vector.parseSimple("1 2 3");
		v1.cosine(v2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void cosineTestException2() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("0 0 0");
		v1.cosine(v2);
	}
	
	@Test
	public void cosineTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4 5 6");
		v1.cosine(v2);
		assertEquals("Expected 0.97463185", 0.97463185, v1.cosine(v2), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void scalarProductTestException() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4");
		v1.scalarProduct(v2);
	}
	
	@Test
	public void scalarProductTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4 5 6");
		assertEquals("Expected 32.0", 32.0, v1.scalarProduct(v2), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductTestException1() {
		IVector v1 = Vector.parseSimple("1");
		IVector v2 = Vector.parseSimple("4 5 6");
		v1.nVectorProduct(v2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductTestException2() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4");
		v1.nVectorProduct(v2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductTestException3() {
		IVector v1 = Vector.parseSimple("1 2 3 4 5");
		IVector v2 = Vector.parseSimple("4 5 6");
		v1.nVectorProduct(v2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductTestException4() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4 5 6 7 8");
		v1.nVectorProduct(v2);
	}
	
	@Test
	public void nVectorProductTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = Vector.parseSimple("4 5 6");
		IVector v3 = v1.nVectorProduct(v2);
		assertEquals("Expected -3.0", -3.0, v3.get(0), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nFromHomogeneusTestException1() {
		IVector v1 = Vector.parseSimple("1");
		v1.nFromHomogeneus();
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nFromHomogeneusTestException2() {
		IVector v1 = Vector.parseSimple("1 2 0");
		v1.nFromHomogeneus();
	}
	
	@Test
	public void nFromHomogeneusTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IVector v2 = v1.nFromHomogeneus();
		assertEquals("Expected 1/3", 1.0/3.0, v2.get(0), 1e-6);
	}
	
	@Test
	public void toRowMatrixTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IMatrix v2 = v1.toRowMatrix(true);
		assertEquals("Expected 2.0", 2.0, v2.get(0, 1), 1e-6);
		v2.set(0, 0, 5);
		assertEquals("Expected 5.0", 5.0, v2.get(0, 0), 1e-6);
		assertEquals("Expected 5.0", 5.0, v1.get(0), 1e-6);
		IMatrix v3 = v1.toRowMatrix(false);
		assertEquals("Expected 0.0", 0.0, v3.get(0, 0), 1e-6);
		v3.set(0, 0, 7);
		assertEquals("Expected 7.0", 7.0, v3.get(0, 0), 1e-6);
		assertEquals("Expected 5.0", 5.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void toColMatrixTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		IMatrix v2 = v1.toColumnMatrix(true);
		assertEquals("Expected 2.0", 2.0, v2.get(1, 0), 1e-6);
		v2.set(0, 0, 5);
		assertEquals("Expected 5.0", 5.0, v2.get(0, 0), 1e-6);
		assertEquals("Expected 5.0", 5.0, v1.get(0), 1e-6);
		IMatrix v3 = v1.toColumnMatrix(false);
		assertEquals("Expected 0.0", 0.0, v3.get(0, 0), 1e-6);
		v3.set(0, 0, 7);
		assertEquals("Expected 7.0", 7.0, v3.get(0, 0), 1e-6);
		assertEquals("Expected 5.0", 5.0, v1.get(0), 1e-6);
	}
	
	@Test
	public void toArrayTest() {
		IVector v1 = Vector.parseSimple("1 2 3");
		double[] array1 = v1.toArray();
		assertEquals("Expected 1.0", 1.0, array1[0], 1e-6);
		assertEquals("Expected 2.0", 2.0, array1[1], 1e-6);
		assertEquals("Expected 3.0", 3.0, array1[2], 1e-6);
	}
}
