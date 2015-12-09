package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.InstructionArgumentImpl;

public class InstrDecrementTest {
	
	Computer c = Mockito.mock(Computer.class); 
	Registers r = Mockito.mock(Registers.class);
	
	@Test(expected=IllegalArgumentException.class)
	public void DecrementTestException1() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		arguments.add(new InstructionArgumentImpl(1, true));
		new InstrDecrement(arguments).execute(c);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecrementTestException2() {
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, false));
		new InstrDecrement(arguments).execute(c);
	}
	
	@Test
	public void DecrementTest() {
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(5);
		r.setRegisterValue(0, 5);
		Mockito.verify(r).setRegisterValue(0, 5);
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(0, true));
		new InstrDecrement(arguments).execute(c);
		Mockito.verify(r).setRegisterValue(0, 4);
	}
}
