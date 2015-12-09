package hr.fer.zemris.bool.productLists;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.bool.Mask;

import org.junit.Test;

public class MaskProductTest {
	
	private Mask m1 = Mask.fromIndex(3, 0,  false);
	private Mask m2 = Mask.fromIndex(3, 3,  false);
	private Mask m3 = Mask.fromIndex(3, 5,  false);
	private Mask m4 = Mask.fromIndex(3, 7,  false);
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException1() {
		Set<Mask> maskSet = new HashSet<>();
		new MaskProduct(maskSet);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTestException2() {
		new MaskProduct(null);
	}
	
	@Test
	public void getMasksTest() {
		Set<Mask> maskSet = new HashSet<>();
		maskSet.add(m1);
		maskSet.add(m2);
		maskSet.add(m3);
		MaskProduct mp = new MaskProduct(maskSet);
		Set<Mask> newSet = mp.getMasks();
		assertEquals("Size should be 3", 3, newSet.size());
		assertEquals("true", true, mp.getMasks().contains(m1));
		assertEquals("true", true, mp.getMasks().contains(m2));
		assertEquals("true", true, mp.getMasks().contains(m3));
		assertEquals("false", false, mp.getMasks().contains(m4));
	}
	
	@Test
	public void getCoveredIndexesTest() {
		Set<Mask> maskSet = new HashSet<>();
		maskSet.add(m1);
		maskSet.add(m2);
		maskSet.add(m3);
		MaskProduct mp = new MaskProduct(maskSet);
		Set<Integer> indexSet = mp.getCoveredIndexes();
		assertEquals("true", true, indexSet.contains(0));
		assertEquals("true", true, indexSet.contains(3));
		assertEquals("true", true, indexSet.contains(5));
		assertEquals("false", false, indexSet.contains(7));
	}
}
