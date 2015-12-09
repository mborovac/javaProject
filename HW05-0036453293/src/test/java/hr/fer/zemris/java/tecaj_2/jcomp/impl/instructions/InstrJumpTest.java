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

public class InstrJumpTest {
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	Memory m = Mockito.mock(Memory.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void JumpTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		arguments.add(new InstructionArgumentImpl(1, false));
		new InstrJump(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void JumpTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		new InstrJump(arguments).execute(c);
	}
	
	@Test
	public void JumpTest() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(5, false));
		new InstrJump(arguments).execute(c);
		Mockito.verify(r).setProgramCounter(5);
	}
}
