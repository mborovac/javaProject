/**
 * Package holding all the implementation classes for java 5th homework
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;

/**
 * Class used to create and use a simple execution unit of a computer. Class implements ExecutionUnit interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	/**
	 * Class constructor.
	 */
	public ExecutionUnitImpl() {
		
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit#go(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method executes the program in the computer memory. Program counter is set to 0.
	 */
	@Override
	public boolean go(Computer computer) {
		try {
			computer.getRegisters().setProgramCounter(0);
			while(true) {
				Instruction instruction = (Instruction) computer.getMemory().getLocation(
						computer.getRegisters().getProgramCounter());
				computer.getRegisters().setProgramCounter(computer.getRegisters().getProgramCounter() + 1);
				if(instruction.execute(computer) == true) {
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
