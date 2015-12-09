package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlusStrategyTest {
	
	@Test
	public void PlusTest1() {
		PlusStrategy plus = new PlusStrategy();
		assertEquals("expected = 8", 8, plus.calculateResult(5, 3, false), 1.e-5);
	}
	
	@Test
	public void PlusTest2() {
		PlusStrategy plus = new PlusStrategy();
		assertEquals("expected = 8", 8, plus.calculateResult(5, 3, true), 1.e-5);
	}
	
	@Test
	public void PlusTest3() {
		PlusStrategy plus = new PlusStrategy();
		assertEquals("expected = 8.3", 8.3, plus.calculateResult(5.2, 3.1, false), 1.e-5);
	}
	
	@Test
	public void PlusTest4() {
		PlusStrategy plus = new PlusStrategy();
		assertEquals("expected = 8.3", 8.3, plus.calculateResult(5.2, 3.1, true), 1.e-5);
	}
}
