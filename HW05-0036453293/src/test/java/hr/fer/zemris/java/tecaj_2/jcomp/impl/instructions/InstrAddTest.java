package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.InstructionArgumentImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrAddTest {
	
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void AddTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		arguments.add(new InstructionArgumentImpl(1, true));
		arguments.add(new InstructionArgumentImpl(2, true));
		new InstrAdd(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void AddTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, false));
		arguments.add(new InstructionArgumentImpl(2, true));
		new InstrAdd(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void AddTestException3() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		arguments.add(new InstructionArgumentImpl(2, false));
		new InstrAdd(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void AddTestException4() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrAdd(arguments).execute(c);
	}
	
	@Test
	public void AddTest() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(0);
		Mockito.when(r.getRegisterValue(1)).thenReturn(5);
		Mockito.when(r.getRegisterValue(2)).thenReturn(4);
		r.setRegisterValue(0, 0);
		r.setRegisterValue(1, 5);
		r.setRegisterValue(2, 4);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		arguments.add(new InstructionArgumentImpl(2, true));
		new InstrAdd(arguments).execute(c);
		Mockito.verify(r).setRegisterValue(0, 9);
		}
}
