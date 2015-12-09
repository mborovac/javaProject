package hr.fer.zemris.bool.fimpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;

import org.junit.Test;

public class MaskBasedBFTest {
	
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException1() {
		new MaskBasedBF(
				null,
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException2() {
		new MaskBasedBF(
				"",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException3() {
		new MaskBasedBF(
				"f1",
				null,
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException4() {
		List<BooleanVariable> emptyList = new ArrayList<BooleanVariable>();
		new MaskBasedBF(
				"f1",
				emptyList,
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException5() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				null,
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException6() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", null),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException7() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x11"),
				Masks.fromStrings("10x0")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException8() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				null
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException9() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0", null)
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException10() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x01")
				);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException11() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("00x0")
				);
	}
	
	@Test
	public void getNameTest() {
		BooleanFunction f1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals("f1", f1.getName());
	}
	
	@Test
	public void getValueTest() {		
		BooleanFunction f1 = new MaskBasedBF(
		"f1",
		Arrays.asList(varA, varB, varC, varD),
		true,
		Masks.fromStrings("00x0", "11x1"),
		Masks.fromStrings("10x0")
		);
		
		BooleanFunction f2 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		
		BooleanValue val2 = f2.getValue();
		BooleanValue val = f1.getValue();
		assertEquals(BooleanValue.TRUE, val);
		assertEquals(BooleanValue.FALSE, val2);
		varB.setValue(BooleanValue.TRUE);	
		val = f1.getValue();
		assertEquals(BooleanValue.FALSE, val);
		f1.getDomain().get(1).setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		val = f1.getValue();
		assertEquals(BooleanValue.TRUE, val);
		varB.setValue(BooleanValue.FALSE);
		val = f1.getValue();
		assertEquals(BooleanValue.TRUE, val);
		varA.setValue(BooleanValue.TRUE);
		val = f1.getValue();
		assertEquals(BooleanValue.FALSE, val);
		varA.setValue(BooleanValue.FALSE);		// reseting the changes on variables
		varB.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.FALSE);
	}
	
	@Test
	public void getDomainTest() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.getDomain();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException1() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasMinterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMintermTestException2() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasMinterm(56);
	}
	
	@Test
	public void hasMintermTest1() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(true, operator1.hasMinterm(0));
		assertEquals(false, operator1.hasMinterm(1));
	}
	
	@Test
	public void hasMintermTest2() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(false, operator1.hasMinterm(0));
		assertEquals(true, operator1.hasMinterm(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException1() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasMaxterm(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasMaxtermTestException2() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasMaxterm(56);
	}
	
	@Test
	public void hasMaxtermTest1() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(true, operator1.hasMaxterm(0));
		assertEquals(false, operator1.hasMaxterm(1));
	}
	
	@Test
	public void hasMaxtermTest2() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(false, operator1.hasMaxterm(0));
		assertEquals(true, operator1.hasMaxterm(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException1() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasDontCare(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hasDontCareTestException2() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		operator1.hasDontCare(56);
	}
	
	@Test
	public void hasDontCareTest() {
		BooleanFunction operator1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "11x1"),
				Masks.fromStrings("10x0")
				);
		assertEquals(true, operator1.hasDontCare(8));
		assertEquals(false, operator1.hasDontCare(12));
	}
	
	////////////////////////////////////////////////////////////
	// XYZIterable methods don't need testing on their own //
	////////////////////////////////////////////////////////////	
	
}
