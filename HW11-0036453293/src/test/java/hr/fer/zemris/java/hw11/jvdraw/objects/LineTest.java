package hr.fer.zemris.java.hw11.jvdraw.objects;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class LineTest {
	
	@Test
	public void toFileTest() {
		Line line = new Line("Line 1", 20, 20, 55, 55, Color.BLACK);
		assertEquals("expected LINE 20 20 55 55 0 0 0",
				new String("LINE 20 20 55 55 0 0 0"), line.toFile());
	}
}
