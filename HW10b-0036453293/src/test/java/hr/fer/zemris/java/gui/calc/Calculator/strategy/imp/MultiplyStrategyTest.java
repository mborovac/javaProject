package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplyStrategyTest {
	
	@Test
	public void MulTest1() {
		MultiplyStrategy mul = new MultiplyStrategy();
		assertEquals("expected = 6", 6, mul.calculateResult(2, 3, true), 1.e-5);
	}
	
	@Test
	public void MulTest2() {
		MultiplyStrategy mul = new MultiplyStrategy();
		assertEquals("expected = 6", 6, mul.calculateResult(2, 3, true), 1.e-5);
	}
}
