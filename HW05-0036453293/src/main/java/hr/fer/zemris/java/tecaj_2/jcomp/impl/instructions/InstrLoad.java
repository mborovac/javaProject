/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation load. 1st constructor parameter register is the 
 * destination register, 2nd register is the source one.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrLoad implements Instruction {
	
	private int registerIndex;
	private int memoryLocation;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, 1st must be a register, 2nd one a memory location.
	 * 
	 * @param arguments 1st is the destination register, 2nd one is the memory location from which to load 
	 * the info
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex = ((Integer) arguments.get(0).getValue()).intValue();
		this.memoryLocation = ((Integer) arguments.get(1).getValue()).intValue();
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method takes the information from the given memory location and puts the value
	 * in to the 1st class constructor parameter register.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryLocation);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}
}
