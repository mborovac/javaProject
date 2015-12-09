package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsStrategyTest {
	
	@Test
	public void EqualsTest1() {
		EqualsStrategy equals = new EqualsStrategy();
		assertEquals("expected = 2.0", 2.0, equals.calculate(4, 2, new DivideStrategy(), false), 1.e-5);
	}
	
	@Test
	public void EqualsTest2() {
		EqualsStrategy equals = new EqualsStrategy();
		assertEquals("expected = 2.0", 2.0, equals.calculate(4, 2, new DivideStrategy(), true), 1.e-5);
	}
	
	@Test
	public void EqualsTest3() {
		EqualsStrategy equals = new EqualsStrategy();
		assertEquals("expected = 4", 4, equals.calculate(4, 2, null, true), 1.e-5);
	}
}
