package hr.fer.zemris.bool.fimpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import org.junit.Test;

public class IndexedBFTest {
	
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException1() {
		new IndexedBF(null,
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException2() {
		new IndexedBF("",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException3() {
		new IndexedBF("f1",
				null,
				true,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException4() {
		new IndexedBF("f1",
				Arrays.asList(varA, null, varC),
				true,
				Arrays.asList(0,1),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException5() {
		List<BooleanVariable> emptyList = new ArrayList<BooleanVariable>();
		new IndexedBF("f1",
				emptyList,
				true,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException6() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				null,
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException7() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, null, 1),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException8() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(-2, 1),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException9() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(28, 1),
				Arrays.asList(2,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException10() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(1,3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException11() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				null
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException12() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, null)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException13() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(-2, 3)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException14() {
		new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(28, 3)
				);
	}
	
	@Test
	public void getNameTest() {
		BooleanFunction operator1 = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals("f1", operator1.getName());
	}
		
	@Test
	public void getValueTest() {
		BooleanFunction f1 = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
		
		BooleanFunction f2 = new IndexedBF(
				"f2",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(1,5,7),
				Arrays.asList(2,3)
				);
		
		BooleanFunction f3 = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				false,
				Arrays.asList(0,1,5),
				Arrays.asList(2,3)
				);
		
		assertEquals(BooleanValue.TRUE, f1.getValue());
		assertEquals(BooleanValue.FALSE, f2.getValue());
		assertEquals(BooleanValue.TRUE, f3.getValue());
	}
	
	@Test
	public void getDomainTest() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.getDomain();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException1() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasMinterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException2() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasMinterm(56);
	}
	
	@Test
	public void hasMintermTest1() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals(true, operator1.hasMinterm(1));
		assertEquals(false, operator1.hasMinterm(2));
	}
	
	@Test
	public void hasMintermTest2() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				false,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals(false, operator1.hasMinterm(1));
		assertEquals(false, operator1.hasMinterm(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException1() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				false,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasMaxterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException2() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				false,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasMaxterm(56);
	}
	
	@Test
	public void hasMaxtermTest1() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				false,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals(true, operator1.hasMaxterm(1));
		assertEquals(false, operator1.hasMaxterm(2));
	}
	
	@Test
	public void hasMaxtermTest2() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals(false, operator1.hasMaxterm(1));
		assertEquals(false, operator1.hasMaxterm(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException1() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasDontCare(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException2() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		operator1.hasDontCare(56);
	}
	
	@Test
	public void hasDontCareTest() {
		BooleanFunction operator1 = new IndexedBF("f1",
				Arrays.asList(varA, varB, varC),
				true,
				Arrays.asList(0, 1),
				Arrays.asList(2, 3)
				);
		assertEquals(true, operator1.hasDontCare(2));
		assertEquals(false, operator1.hasDontCare(1));
	}
	
	////////////////////////////////////////////////////////////
	// XYZIterable methods don't need testing on their own //
	////////////////////////////////////////////////////////////
}
