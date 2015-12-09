package hr.fer.zemris.java.sorters;

import static org.junit.Assert.*;

import org.junit.Test;

public class QSortParallel2Test {
	
	@Test
	public void QSortTest1() {
		int[] data = new int[] {5, 21, 943, 8, 34, 12 ,83, 36, 2, 4, 31, 4};
		QSortParallel2.sort(data);
		assertEquals("true", true, QSortParallel2.isSorted(data));
	}
	
	@Test
	public void QSortTest2() {
		int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		QSortParallel2.sort(data);
		assertEquals("true", true, QSortParallel2.isSorted(data));
	}
	
	@Test
	public void QSortTest3() {
		int[] data = new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
		QSortParallel2.sort(data);
		assertEquals("true", true, QSortParallel2.isSorted(data));
	}
	
	@Test
	public void QSortTest4() {
		int[] data = new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
		QSortParallel2.sort(data);
		assertEquals("true", true, QSortParallel2.isSorted(data));
	}
}
