package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class InverseStrategyTest {
	
	@Test
	public void InverseTest1() {
		InverseStrategy inv = new InverseStrategy();
		assertEquals("expected = 1/5", 1.0/5.0, inv.calculate(5, false), 1.e-5);
	}
}
