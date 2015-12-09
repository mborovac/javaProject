/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation testEquals. Both constructor arguments are registers whose
 * content is checked to be equal.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	
	private int registerIndex1;
	private int registerIndex2;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, both must be registers.
	 * 
	 * @param arguments registers whose value is to be compared
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
	 * Method sets the register flag to true if the contents of both given registers are equal, false otherwise
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		if(value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}
}
