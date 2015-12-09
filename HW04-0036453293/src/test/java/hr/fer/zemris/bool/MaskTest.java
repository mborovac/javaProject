package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class MaskTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void costructorTestException1() {
		MaskValue[] mask = null;
		new Mask(mask);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void costructorTestException2() {
		MaskValue[] mask = {MaskValue.ONE, MaskValue.ONE, MaskValue.ZERO, null};
		new Mask(mask);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void costructorTestException3() {
		MaskValue[] mask = {MaskValue.ONE, MaskValue.ONE, null, MaskValue.ZERO};
		new Mask(mask);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseTestException1() {
		Mask.parse("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseTestException2() {
		Mask.parse(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseTestException3() {
		Mask.parse("10x01a");
	}
	
	@Test
	public void parseTest() {
		Mask mask = Mask.parse("10xX11");
		assertEquals(MaskValue.ONE, mask.getValue(0));
		assertEquals(MaskValue.ZERO, mask.getValue(1));
		assertEquals(MaskValue.DONT_CARE, mask.getValue(2));
		assertEquals(MaskValue.DONT_CARE, mask.getValue(3));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexTestException1() {
		Mask.fromIndex(0, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexTestException2() {
		Mask.fromIndex(3, -3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexTestException3() {
		Mask.fromIndex(2, 7);
	}
	
	@Test
	public void fromIndexTest() {
		Mask mask1 = Mask.fromIndex(4, 7);
		assertEquals(MaskValue.ZERO, mask1.getValue(0));
		assertEquals(MaskValue.ONE, mask1.getValue(3));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getValueTestException1() {
		Mask mask1 = Mask.fromIndex(4, 7);
		mask1.getValue(7);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getValueTestException2() {
		Mask mask1 = Mask.fromIndex(4, 7);
		mask1.getValue(-2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void isMoreGeneralThanTestException1() {
		Mask mask1 = Mask.fromIndex(4, 7);
		mask1.isMoreGeneralThan(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isMoreGeneralThanTestExcpotion2() {
		Mask mask1 = Mask.fromIndex(4, 7);
		Mask mask2 = Mask.fromIndex(5, 7);
		mask1.isMoreGeneralThan(mask2);
	}
	
	@Test
	public void isMoreGeneralThanTest() {
		Mask mask1 = Mask.parse("1X1");
		Mask mask2 = Mask.fromIndex(3, 7);
		Mask mask3 = Mask.parse("1X1");
		Mask mask4 = Mask.parse("1XX");
		assertEquals(false, mask1.isMoreGeneralThan(mask1));
		assertEquals(true, mask1.isMoreGeneralThan(mask2));
		assertEquals(false, mask1.isMoreGeneralThan(mask3));
		assertEquals(false, mask1.isMoreGeneralThan(mask4));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineTestException1() {
		Mask mask1 = null;
		Mask mask2 = Mask.parse("1X1");
		Mask.combine(mask1, mask2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineTestException2() {
		Mask mask1 = Mask.parse("1X1");
		Mask mask2 = null;
		Mask.combine(mask1, mask2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineTestException3() {
		Mask mask1 = Mask.parse("1X1");
		Mask mask2 = Mask.parse("1X110");
		Mask.combine(mask1, mask2);
	}
	
	@Test
	public void combineTest() {
		Mask mask1 = Mask.parse("1X11");
		Mask mask2 = Mask.parse("1111");
		Mask mask3 = Mask.parse("0110");
		Mask mask4 = Mask.parse("0111");
		Mask combined1 = Mask.combine(mask1, mask2);
		assertEquals(null, combined1);
		Mask combined2 = Mask.combine(mask1, mask1);
		assertEquals(null, combined2);
		Mask combined3 = Mask.combine(mask2, mask3);
		assertEquals(null, combined3);
		Mask combined4 = Mask.combine(mask3, mask4);
		assertEquals(Mask.parse("011X").getValue(3), combined4.getValue(3));
	}
	
	@Test
	public void getNumberOfZerosTest() {
		Mask mask1 = Mask.parse("1X11");
		Mask mask2 = Mask.parse("0111");
		Mask mask3 = Mask.parse("0110");
		assertEquals(0, mask1.getNumberOfZeros());
		assertEquals(1, mask2.getNumberOfZeros());
		assertEquals(2, mask3.getNumberOfZeros());
	}
	
	@Test
	public void getNumberOfOnesTest() {
		Mask mask1 = Mask.parse("0000");
		Mask mask2 = Mask.parse("0100");
		Mask mask3 = Mask.parse("0110");
		assertEquals(0, mask1.getNumberOfOnes());
		assertEquals(1, mask2.getNumberOfOnes());
		assertEquals(2, mask3.getNumberOfOnes());
	}

	@Test
	public void getSizeTest1() {
		Mask m1 = Mask.fromIndex(5, 11);
		assertEquals(m1.getSize(), 5);
	}
	
	@Test
	public void getSizeTest2() {
		Mask m1 = Mask.fromIndex(5, 11);
		int a = m1.getSize();
		assertEquals(5, a);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getSizeTest3() {
		MaskValue[] mask = new MaskValue[10];
		mask[0] = MaskValue.DONT_CARE;
		mask[1] = MaskValue.ONE;
		new Mask(mask);
	}
	
	@Test
	public void getMaskIndexesTest1() {
		Mask mask1 = Mask.parse("1x0X");
		List<Integer> maskIndexes = mask1.getMaskIndexes();
		assertEquals(true, maskIndexes.contains(new Integer(8)));
		assertEquals(true, maskIndexes.contains(new Integer(9)));
		assertEquals(true, maskIndexes.contains(new Integer(12)));
		assertEquals(true, maskIndexes.contains(new Integer(13)));
		assertEquals(false, maskIndexes.contains(new Integer(1)));
		assertEquals(false, maskIndexes.contains(new Integer(7)));
		assertEquals(false, maskIndexes.contains(new Integer(15)));
		assertEquals(false, maskIndexes.contains(new Integer(4)));
	}
	
	@Test
	public void getMaskIndexesTest2() {
		Mask mask1 = Mask.parse("1000");
		List<Integer> maskIndexes = mask1.getMaskIndexes();
		assertEquals(true, maskIndexes.contains(new Integer(8)));
	}
}
