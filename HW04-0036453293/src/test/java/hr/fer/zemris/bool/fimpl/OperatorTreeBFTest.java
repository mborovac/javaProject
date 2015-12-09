package hr.fer.zemris.bool.fimpl;

import static org.junit.Assert.*;

import java.util.Arrays;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

import org.junit.Test;

public class OperatorTreeBFTest {
	
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	
	BooleanOperator izraz1 = BooleanOperators.or(
			BooleanConstant.FALSE,
			varC,
			BooleanOperators.and(varA, BooleanOperators.not(varB))
			);
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException1() {
		new OperatorTreeBF(
				null,
				Arrays.asList(varA, varB, varC),
				izraz1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException2() {
		new OperatorTreeBF(
				"",
				Arrays.asList(varA, varB, varC),
				izraz1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException3() {
		new OperatorTreeBF(
				"f1",
				null,
				izraz1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException4() {
		new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, null, varC),
				izraz1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException5() {
		new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException6() {
		new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varC),
				izraz1);
	}
	
	@Test
	public void getNameTest() {
		BooleanFunction f1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		assertEquals("f1", f1.getName());
	}
	
	@Test
	public void getValueTest() {
		
		BooleanFunction f1 = new OperatorTreeBF(
		"f1",
		Arrays.asList(varA, varB, varC),
		izraz1);
		
//		System.out.print("Minterms: ");
//		for(Integer i : f1.mintermIterable()) { // Print: 1, 3, 4, 5, 7
//			System.out.print(i + " ");
//		}
//		System.out.print("\nMaxterms: ");
//		for(Integer i : f1.maxtermIterable()) { // Print: 0, 2, 6
//			System.out.print(i + " ");
//		}
//		System.out.print("\nDon't cares: ");
//		for(Integer i : f1.dontcareIterable()) { // Print:
//			System.out.print(i + " ");
//		}
		
		for(BooleanVariable domainVariable: f1.getDomain()) {
			domainVariable.setValue(BooleanValue.FALSE);
		}
		assertEquals(BooleanValue.FALSE, f1.getValue());
		
		for(BooleanVariable domainVariable: f1.getDomain()) {
			if(domainVariable.getName().matches("A")) {
				domainVariable.setValue(BooleanValue.TRUE);
			}
		}
		assertEquals(BooleanValue.TRUE, f1.getValue());
	}
	
	@Test
	public void getDomainTest() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.getDomain();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException1() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasMinterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException2() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasMinterm(56);
	}
	
	@Test
	public void hasMintermTest() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		assertEquals(true, function1.hasMinterm(1));
		assertEquals(false, function1.hasMinterm(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException1() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasMaxterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException2() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasMaxterm(56);
	}
	
	@Test
	public void hasMaxtermTest() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		assertEquals(true, function1.hasMaxterm(2));
		assertEquals(false, function1.hasMaxterm(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException1() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasDontCare(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException2() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		function1.hasDontCare(56);
	}
	
	@Test
	public void hasDontCareTest1() {
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		assertEquals(false, function1.hasDontCare(2));
		assertEquals(false, function1.hasDontCare(1));
	}
	
	@Test
	public void hasDontCareTest2() {
		BooleanOperator izraz2 = BooleanOperators.or(
				BooleanConstant.DONT_CARE,
				varC,
				BooleanOperators.and(varA, BooleanOperators.not(varB))
				);
		
		BooleanFunction function1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz2);
		assertEquals(true, function1.hasDontCare(2));
		assertEquals(false, function1.hasDontCare(1));
	}
	
	////////////////////////////////////////////////////////////
	// XYZIterable methods don't need testing on their own //
	////////////////////////////////////////////////////////////
	
}
