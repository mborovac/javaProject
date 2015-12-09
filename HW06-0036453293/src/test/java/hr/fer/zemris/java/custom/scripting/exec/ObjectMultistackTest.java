package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void homeworkTest() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper empty = new ValueWrapper(new Integer(4));
		multistack.push("empty", empty);
		multistack.pop("empty");
		assertEquals(true, multistack.isEmpty("empty"));
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(false, multistack.isEmpty("year"));
		assertEquals(true, multistack.isEmpty("price"));
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(200.51, multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(1900, multistack.peek("year").getValue());
		multistack.peek("year").setValue(
		((Integer)multistack.peek("year").getValue()).intValue() + 50
		);
		assertEquals(1950, multistack.peek("year").getValue());
		multistack.pop("year");
		assertEquals(2000, multistack.peek("year").getValue());
		multistack.peek("year").increment("5");
		assertEquals(2005, multistack.peek("year").getValue());
		multistack.peek("year").increment(5);
		assertEquals(2010, multistack.peek("year").getValue());
		multistack.peek("year").increment(5.0);
		assertEquals(2015.0, multistack.peek("year").getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PushTestException1() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push(null, year);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PushTestException2() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("", year);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PushTestException3() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = null;
		multistack.push("year", year);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PopTestException1() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.pop(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PopTestException2() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.pop("");
	}
	
	@Test(expected=EmptyStackException.class)
	public void PopTestException3() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.pop("year");
		multistack.pop("year");
	}
	
	@Test
	public void PopTest() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(null, multistack.pop("notInMap"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void PeekTestException1() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PeekTestException2() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek("");
	}
	
	@Test(expected=EmptyStackException.class)
	public void PeekTestException3() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.pop("year");
		multistack.peek("year");
	}
	
	@Test
	public void PeekTest() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(null, multistack.peek("notInMap"));
	}
}
