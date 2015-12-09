package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanOperatorNOTTest {
	
	@Test
	public void AllRoundTest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanOperator operator = new BooleanOperatorNOT(varA);
		assertEquals(BooleanValue.TRUE, operator.getValue());
		assertEquals(true, operator.getDomain().contains(varA));
		assertEquals(false, operator.getDomain().contains(varB));
		varA.setValue(BooleanValue.TRUE);
		assertEquals(BooleanValue.FALSE, operator.getValue());
		varA.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.DONT_CARE, operator.getValue());
	}
}
