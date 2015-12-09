package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpStrategyTest {
	
	@Test
	public void ExpTest1() {
		ExpStrategy exp = new ExpStrategy();
		assertEquals("expected = 16", 16, exp.calculateResult(2, 4, false), 1.e-5);
	}
	
	@Test
	public void ExpTest2() {
		ExpStrategy exp = new ExpStrategy();
		assertEquals("expected = 1.1892071", 1.1892071, exp.calculateResult(2, 4, true), 1.e-5);
	}
}
