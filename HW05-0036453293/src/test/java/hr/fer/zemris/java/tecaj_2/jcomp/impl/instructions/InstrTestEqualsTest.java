package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.InstructionArgumentImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrTestEqualsTest {
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	Memory m = Mockito.mock(Memory.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void TestEqualsTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		arguments.add(new InstructionArgumentImpl(2, true));
		new InstrTestEquals(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestEqualsTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrTestEquals(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestEqualsTestException3() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, false));
		new InstrTestEquals(arguments).execute(c);
	}
	
	@Test
	public void TestEqualsTest1() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(0);
		Mockito.when(r.getRegisterValue(1)).thenReturn(0);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrTestEquals(arguments).execute(c);
		Mockito.verify(r).setFlag(true);
	}
	
	@Test
	public void TestEqualsTest2() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(0);
		Mockito.when(r.getRegisterValue(1)).thenReturn(5);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrTestEquals(arguments).execute(c);
		Mockito.verify(r).setFlag(false);
	}
}
