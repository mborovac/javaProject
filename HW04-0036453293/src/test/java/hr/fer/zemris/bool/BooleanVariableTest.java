package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanVariableTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void ConstructorTestxception1() {
		new BooleanVariable("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ConstructorTestxception2() {
		new BooleanVariable(null);
	}
	
	@Test
	public void AllRoundTest() {
		BooleanVariable varA = new BooleanVariable("A");
		assertEquals("A", varA.getName());
		assertEquals(BooleanValue.FALSE, varA.getValue());
		varA.setValue(BooleanValue.TRUE);
		assertEquals(BooleanValue.TRUE, varA.getValue());
		varA.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.DONT_CARE, varA.getValue());
	}
}
