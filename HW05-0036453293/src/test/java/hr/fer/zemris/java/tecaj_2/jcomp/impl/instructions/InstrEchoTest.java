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

public class InstrEchoTest {
	
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	Memory m = Mockito.mock(Memory.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void EchoTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrEcho(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EchoTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		new InstrEcho(arguments).execute(c);
	}
	
	@Test
	public void EchoTest() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(4);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		new InstrEcho(arguments).execute(c);
		Mockito.verify(r).getRegisterValue(0);
	}
}
