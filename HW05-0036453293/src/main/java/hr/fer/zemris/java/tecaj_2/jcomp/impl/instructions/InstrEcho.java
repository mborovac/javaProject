/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used to support the microprocessor operation echo. It prints the contents of the selected 
 * register to standard output.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrEcho implements Instruction {
	
	private int registerIndex;
	
	/**
	 * Class constructor. Constructor takes one argument register.
	 * 
	 * @param arguments register of the processor whose value is to be printed
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
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
	 * Method prints the constents of the selected register to standard output.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		System.out.print(value.toString());
		return false;
	}
}
