package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BooleanOperatorORTest {
	
	@Test
	public void AllRoundTest1() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		List<BooleanSource> sourceList = new ArrayList<BooleanSource>();
		sourceList.add(varA);
		sourceList.add(varB);
		sourceList.add(varC);
		BooleanOperator operator = new BooleanOperatorOR(sourceList);
		assertEquals(BooleanValue.FALSE, operator.getValue());
		assertEquals(true, operator.getDomain().contains(varA));
		varC.setValue(BooleanValue.DONT_CARE);
		assertEquals(BooleanValue.DONT_CARE, operator.getValue());
		varC.setValue(BooleanValue.TRUE);
		assertEquals(BooleanValue.TRUE, operator.getValue());
	}
}
