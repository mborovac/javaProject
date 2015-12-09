package hr.fer.zemris.bool;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BooleanOperatorTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void ConstructorTestException1() {
		new BooleanOperatorOR(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ConstructorTestException2() {
		List<BooleanSource> sourceList = new ArrayList<BooleanSource>();
		new BooleanOperatorOR(sourceList);
	}
	
	@Test
	public void DomainAndValueTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		varA.setValue(BooleanValue.TRUE);
		List<BooleanSource> sourceList = new ArrayList<BooleanSource>();
		sourceList.add(varA);
		sourceList.add(varB);
		sourceList.add(varC);
		BooleanOperator operator = new BooleanOperatorOR(sourceList);
		assertEquals(BooleanValue.TRUE, operator.getValue());
		assertEquals(true, operator.getDomain().contains(varA));
	}
}
