package hr.fer.zemris.bool.qmc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

import org.junit.Test;

public class QMCMinimizerTest {
	
	BooleanVariable varA = new BooleanVariable("A");
	BooleanVariable varB = new BooleanVariable("B");
	BooleanVariable varC = new BooleanVariable("C");
	BooleanVariable varD = new BooleanVariable("D");
	
	// HW example 1
	@Test
	public void MinimizerTest1() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
				new ArrayList<Integer>()
				);
		boolean products = false;
		List<MaskBasedBF> functions = QMCMinimizer.minimize(bf, products);
		assertEquals("6 minimal forms", 6, functions.size());
	}
	
	// HW 1st small example
	@Test
	public void MinimizerTest2() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Arrays.asList(0,1,4,5,11,15),
				new ArrayList<Integer>()
				);
		boolean products = true;
		List<MaskBasedBF> functions = QMCMinimizer.minimize(bf, products);
		assertEquals("1 minimal form", 1, functions.size());
		assertEquals("2 masks in the product", 2, functions.get(0).getMasks().size());
		Mask m1 = Mask.parse("0x0x");
		Mask m2 = Mask.parse("1x11");
		assertEquals("true", true, functions.get(0).getMasks().contains(m1));
		assertEquals("true", true, functions.get(0).getMasks().contains(m2));
	}
	
	// HW last example
		@Test
		public void MinimizerTest3() {
			BooleanFunction bf = new IndexedBF(
					"f1",
					Arrays.asList(varA, varB, varC, varD),
					true,
					Arrays.asList(0,1,4,5,11,15),
					Arrays.asList(2,6,10)
					);
			boolean products = false;
			List<MaskBasedBF> functions = QMCMinimizer.minimize(bf, products);
			assertEquals("1 minimal form", 1, functions.size());
			assertEquals("2 masks in the product", 2, functions.get(0).getMasks().size());
			Mask m1 = Mask.parse("0x0x");
			Mask m2 = Mask.parse("1x11");
			assertEquals("true", true, functions.get(0).getMasks().contains(m1));
			assertEquals("true", true, functions.get(0).getMasks().contains(m2));
		}
}
