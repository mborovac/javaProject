/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.util.List;

/**
 * Class used to support the microprocessor operation move. 1st constructor parameter register is the 
 * destination register, 2nd register is the source one.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrMove implements Instruction {
	
	private int registerIndex1;
	private int registerIndex2;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, both must be registers.
	 * 
	 * @param arguments registers of the processor, 1st is the destination register, 
	 * 2nd is the source one.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		
		this.registerIndex1 = ((Integer) arguments.get(0).getValue()).intValue();
		this.registerIndex2 = ((Integer) arguments.get(1).getValue()).intValue();
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method takes the integer values of 2nd class constructor parameter register and puts the value
	 * in to the 1st class constructor parameter register.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setRegisterValue(registerIndex1, value);
		return false;
	}
}
