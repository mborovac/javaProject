package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class DivideStrategyTest {
	
	@Test
	public void DivTest1() {
		DivideStrategy div = new DivideStrategy();
		assertEquals("expected = 2.5", 2.5, div.calculateResult(5, 2, false), 1.e-5);
	}
	
	@Test
	public void DivTest2() {
		DivideStrategy div = new DivideStrategy();
		assertEquals("expected = 2.5", 2.5, div.calculateResult(5, 2, true), 1.e-5);
	}
}
