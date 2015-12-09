package hr.fer.zemris.bool.opimpl;

import static org.junit.Assert.*;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import org.junit.Test;

public class BooleanOperatorsTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void andTestException() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperators.and(varA, varC, null, varB);
	}
	
	@Test
	public void andTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator operator1 = BooleanOperators.and(varA, varC, varB);
		assertEquals(BooleanValue.FALSE, operator1.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void orTestException() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperators.or(varA, varC, null, varB);
	}
	
	@Test
	public void orTest() {
		BooleanVariable varA = new BooleanVariable("A");
		varA.setValue(BooleanValue.TRUE);
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator operator1 = BooleanOperators.or(varA, varC, varB);
		assertEquals(BooleanValue.TRUE, operator1.getValue());
		varA.setValue(BooleanValue.FALSE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void notTestException() {
		BooleanOperators.not(null);
	}
	
	@Test
	public void notTest() {
		BooleanVariable varA = new BooleanVariable("A");
		varA.setValue(BooleanValue.TRUE);
		BooleanOperator operator1 = BooleanOperators.not(varA);
		assertEquals(BooleanValue.FALSE, operator1.getValue());
		varA.setValue(BooleanValue.FALSE);
		assertEquals(BooleanValue.TRUE, operator1.getValue());
	}
}
