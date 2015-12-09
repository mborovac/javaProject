/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation jump. It is used to change the course of operation
 * execution.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrJump implements Instruction {
	
	private int location;
	
	/**
	 * class constructor. Constructor takes one argument, the memory location to which the program counter
	 * should be set.
	 * 
	 * @param arguments memory location to which the program counter should be set
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.location = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method sets the program counter to the specified value.
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}
}
