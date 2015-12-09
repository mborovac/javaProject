package hr.fer.zemris.java.gui.calc.Calculator.strategy.imp;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogStrategyTest {
	
	@Test
	public void LogTest1() {
		LogStrategy log = new LogStrategy();
		assertEquals("expected = 0.6989700", 0.6989700, log.calculate(5, false), 1.e-5);
	}
	
	@Test
	public void LogTest2() {
		LogStrategy log = new LogStrategy();
		assertEquals("expected = 100000", 100000, log.calculate(5, true), 1.e-5);
	}
}
