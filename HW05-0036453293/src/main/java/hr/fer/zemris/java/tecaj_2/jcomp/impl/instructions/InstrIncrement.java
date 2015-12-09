/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation increment. Only constructor parameter register is the 
 * destination register. Its value is increased by 1.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrIncrement implements Instruction {
	
	private int registerIndex;
	
	/**
	 * Class constructor. Constructor takes one register argument.
	 * 
	 * @param arguments register of the processor whose value is to be increased
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.registerIndex = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method increases the value of the selected register by 1.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(registerIndex, (((Integer) value).intValue() + new Integer(1)));
		return false;
	}
}
