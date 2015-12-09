package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class CosStrategyTest {
	
	@Test
	public void CosTest1() {
		CosStrategy cos = new CosStrategy();
		assertEquals("expected = 0.2836621", 0.2836621, cos.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void CosTest2() {
		CosStrategy cos = new CosStrategy();
		assertEquals("expected = NaN", Double.NaN, cos.calculate(5, true), 1.e-5);
	}
}
