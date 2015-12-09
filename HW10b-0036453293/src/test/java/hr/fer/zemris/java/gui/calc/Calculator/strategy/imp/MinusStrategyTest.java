package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MinusStrategyTest {
	
	@Test
	public void MinusTest1() {
		MinusStrategy minus = new MinusStrategy();
		assertEquals("expected = 2", 2, minus.calculateResult(5, 
				3, true), 1.e-5);
	}
	
	@Test
	public void MinusTest2() {
		MinusStrategy minus = new MinusStrategy();
		assertEquals("expected = 2", 2, minus.calculateResult(5, 
				3, true), 1.e-5);
	}
	
	@Test
	public void MinusTest3() {
		MinusStrategy minus = new MinusStrategy();
		assertEquals("expected = 2.6", 2.6, minus.calculateResult(5.8, 
				3.2, false), 1.e-5);
	}
	
	@Test
	public void MinusTest4() {
		MinusStrategy minus = new MinusStrategy();
		assertEquals("expected = 2.6", 2.6, minus.calculateResult(5.8, 
				3.2, true), 1.e-5);
	}
}
