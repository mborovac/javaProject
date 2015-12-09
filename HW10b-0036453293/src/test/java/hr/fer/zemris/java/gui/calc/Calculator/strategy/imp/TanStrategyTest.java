package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TanStrategyTest {
	
	@Test
	public void TanTest1() {
		TanStrategy tan = new TanStrategy();
		assertEquals("expected = -3.3805150", -3.3805150, tan.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void TanTest2() {
		TanStrategy tan = new TanStrategy();
		assertEquals("expected = 1.3734007", 1.3734007, tan.calculate(5, true), 1.e-5);
	}
}
