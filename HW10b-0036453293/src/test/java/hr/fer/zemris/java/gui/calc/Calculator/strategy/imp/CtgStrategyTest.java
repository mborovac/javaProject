package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class CtgStrategyTest {
	
	@Test
	public void CtgTest1() {
		CtgStrategy ctg = new CtgStrategy();
		assertEquals("expected = -0.2958129", -0.2958129, ctg.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void CtgTest2() {
		CtgStrategy ctg = new CtgStrategy();
		assertEquals("expected = 0.7281195", 0.728119, ctg.calculate(5, true), 1.e-5);
	}
}
