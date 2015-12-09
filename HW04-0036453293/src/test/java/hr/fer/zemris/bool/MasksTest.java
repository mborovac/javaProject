package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MasksTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexesTestException1() {
		Masks.fromIndexes(0, 1, 2, 3, 5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexesTestException2() {
		Masks.fromIndexes(5, 2, 3, 5, -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexesTestException3() {
		Masks.fromIndexes(2, 1, 5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexesTestException4() {
		Masks.fromIndexes(5, 2, 3, 6, 3);
	}
	
	@Test
	public void fromIndexesTest() {
		List<Mask> newMasks = new ArrayList<Mask>();
		newMasks = Masks.fromIndexes(5, 2, 3, 6);
		assertEquals(5, newMasks.get(0).getSize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromStringsTestException1() {
		Masks.fromStrings("10x1", "111", null, "001x");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromStringsTestException2() {
		Masks.fromStrings("10x1", "111", "", "001x");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromStringsTestException3() {
		Masks.fromStrings("10x1", "111", "10X1", "001x");
	}
}
