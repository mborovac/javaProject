package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanConstantTest {
	
	@Test
	public void DomainAndValueTest() {
		BooleanConstant c1 = BooleanConstant.TRUE;
		BooleanConstant c2 = BooleanConstant.FALSE;
		assertEquals(BooleanValue.TRUE, c1.getValue());
		assertEquals(BooleanValue.FALSE, c2.getValue());
		assertEquals(true, c2.getDomain().isEmpty());
	}
}
