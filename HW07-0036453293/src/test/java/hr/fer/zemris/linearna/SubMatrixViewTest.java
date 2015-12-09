package hr.fer.zemris.linearna;

import org.junit.Test;

public class SubMatrixViewTest {
	
	@Test
	public void getTest() {
		Matrix m1 = Matrix.parseSimple("1 2 3|4 5 6|7 8 9 | 10 11 12");
		System.out.println(m1);
		IMatrix subMatrix = m1.subMatrix(2, 1, true);/*new MatrixSubMatrixView(m1, 2, 1);*/
		System.out.println();
		System.out.println(subMatrix);
		System.out.println();
		IMatrix subMatrix2 = subMatrix.subMatrix(1, 0, true);
		IMatrix subMatrix3 = subMatrix.subMatrix(1, 0, false);
		System.out.println(subMatrix2);
		subMatrix2.set(0, 0, 77);
		subMatrix3.set(0, 0, 55);
		System.out.println("\nPromjena: ");
		System.out.println(m1);
		System.out.println();
		System.out.println(subMatrix);
		System.out.println();
		System.out.println(subMatrix2);
		System.out.println();
		System.out.println(subMatrix3);
//		System.out.println();
//		m1.set(0, 0, 15);
//		System.out.println(subMatrix);
//		System.out.println("Rows: " + subMatrix.getRowsCount());	// 2
//		System.out.println("Cols: " + subMatrix.getColsCount());	// 2
//		System.out.println(subMatrix.get(0, 0));		// 1
//		System.out.println(subMatrix.get(0, 1));		// 3
//		System.out.println(subMatrix.get(1, 0));		// 4
//		System.out.println(subMatrix.get(1, 1));		// 6
//		MatrixSubMatrixView sub2 = (MatrixSubMatrixView) subMatrix.copy();
//		System.out.println("Sub2:");
//		System.out.println(sub2);
//		sub2.set(0, 0, 0);
//		System.out.println(sub2.get(0, 0));		// 0
//		System.out.println(sub2.get(0, 1));		// 3
//		System.out.println(sub2.get(1, 0));		// 4
//		System.out.println(sub2.get(1, 1));		// 6
//		System.out.println("subMatrix:");
//		System.out.println(subMatrix);
	}
}
