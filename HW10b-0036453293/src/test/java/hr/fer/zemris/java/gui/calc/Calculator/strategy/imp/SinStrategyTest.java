package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class SinStrategyTest {
	
	@Test
	public void SinTest1() {
		SinStrategy sin = new SinStrategy();
		assertEquals("expected = -0.9589242", -0.9589242, sin.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void SinTest2() {
		SinStrategy sin = new SinStrategy();
		assertEquals("expected = NaN", Double.NaN, sin.calculate(5, true), 1.e-5);
	}
}
