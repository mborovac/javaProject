/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation halt. It stops the microprocessor.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Class constructor. Constructor takes no arguments.
	 * 
	 * @param arguments empty list
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(arguments.size() != 0) {
			throw new IllegalArgumentException("Expected no arguments!");
		}
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method stops the microprocessor.
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}
}
