package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.InstructionArgumentImpl;

public class InstrJumpIfTrueTest {
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	Memory m = Mockito.mock(Memory.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void JumpIfTrueTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		arguments.add(new InstructionArgumentImpl(1, false));
		new InstrJumpIfTrue(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void JumpIfTrueTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		new InstrJumpIfTrue(arguments).execute(c);
	}
	
	@Test
	public void JumpIfTrueTest1() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(c.getRegisters().getFlag()).thenReturn(true);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(5, false));
		new InstrJumpIfTrue(arguments).execute(c);
		Mockito.verify(r).setProgramCounter(5);
	}
	
	@Test
	public void JumpIfTrueTest2() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(c.getRegisters().getFlag()).thenReturn(false);
		Mockito.when(c.getRegisters().getProgramCounter()).thenReturn(0);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(5, false));
		Mockito.verifyNoMoreInteractions(r);
		new InstrJumpIfTrue(arguments).execute(c);
	}
}
