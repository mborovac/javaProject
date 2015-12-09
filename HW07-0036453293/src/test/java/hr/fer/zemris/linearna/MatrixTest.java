package hr.fer.zemris.linearna;

import org.junit.Test;

public class MatrixTest {
	
	@Test
	public void Test() {
		Matrix m1 = Matrix.parseSimple(" 1 2 3 | 4 5 6 | 7 8 9");
		System.out.println(m1);
		System.out.println();
		System.out.println(new Matrix(2, 3));
		System.out.println();
//		m1.set(0, 0, 9);
		System.out.println(m1);
		System.out.println(m1.determinant());
		Matrix m2 = Matrix.parseSimple(" 1 2 3 | 3 2 1 | 2 1 3");
		System.out.println(m2);
		System.out.println(m2.determinant());
		Matrix m3 = Matrix.parseSimple(" 9 3 5 2 | -6 -9 7 -1 | -1 -8 1 4 | 11 2 9 7");
		System.out.println(m3.determinant());
		System.out.println(m3.nInvert());
	}
}
