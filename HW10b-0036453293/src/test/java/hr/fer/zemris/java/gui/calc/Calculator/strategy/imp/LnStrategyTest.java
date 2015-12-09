package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class LnStrategyTest {
	
	@Test
	public void LnTest1() {
		LnStrategy ln = new LnStrategy();
		assertEquals("expected = 1.6094379", 1.6094379, ln.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void LnTest2() {
		LnStrategy ln = new LnStrategy();
		assertEquals("expected = 148.4131591", 148.4131591, ln.calculate(5, true), 1.e-5);
	}
}
