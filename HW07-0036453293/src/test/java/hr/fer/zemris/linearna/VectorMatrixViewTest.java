package hr.fer.zemris.linearna;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorMatrixViewTest {
	
	@Test(expected=IncompatibleOperandException.class)
	public void constructorTestException1() {
		new VectorMatrixView(null);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void constructorTestException2() {
		IMatrix a = Matrix.parseSimple(" 1 2 3 | 4 5 6 | 7 8 9");
		new VectorMatrixView(a);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void getTestException1() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.get(-2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void getTestException2() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.get(5);
	}
	
	@Test
	public void getTest1() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		assertEquals("Expected 1.0", 1.0, vm.get(0), 1e-6);
	}
	
	@Test
	public void getTest2() {
		IMatrix a = Matrix.parseSimple("1 4 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		assertEquals("Expected 7.0", 7.0, vm.get(2), 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void setTestException1() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.set(-2, 2);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void setTestException2() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.set(5, 5);
	}
	
	@Test
	public void setTest1() {
		IMatrix a = Matrix.parseSimple("1 | 4 | 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.set(0, 5);
		assertEquals("Expected 5.0", 5.0, vm.get(0), 1e-6);
	}
	
	@Test
	public void setTest2() {
		IMatrix a = Matrix.parseSimple("1 4 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		vm.set(2, 2);
		assertEquals("Expected 2.0", 2.0, vm.get(2), 1e-6);
	}
	
	@Test
	public void getDimensionTest() {
		IMatrix a = Matrix.parseSimple("1 4 7");
		VectorMatrixView vm = new VectorMatrixView(a);
		assertEquals("Expected 3", 3, vm.getDimension());
	}
	
	@Test
	public void copyTest() {
		IMatrix a = Matrix.parseSimple("1 4 7");
		IVector vm = new VectorMatrixView(a);
		IVector vm2 = vm.copy();
		vm.set(1, 2);
		assertEquals("Expected 2.0", 2.0, vm.get(1), 1e-6);
		assertEquals("Expected 2.0", 2.0, vm2.get(1), 1e-6);
	}
	
	@Test
	public void newInstanceTest() {
		IMatrix a = Matrix.parseSimple("1 4 7");
		VectorMatrixView vm1 = new VectorMatrixView(a);
		IVector b = vm1.newInstance(2);
		assertEquals("Expected 0.0", 0.0, b.get(1), 1e-6);
	}
}
