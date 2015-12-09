/**
 * Package holding all the implementation classes for java 5th homework
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Class used to create and use the registers of a microprocessor. Class implements interface Registers.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class RegistersImpl implements Registers {
	
	private Object[] registers;
	private int programCounter;
	private boolean flag;
	
	/**
	 * Class constructor. Constructor takes a single integer value representing the number of the registers.
	 * 
	 * @param regsLen the number of the registers, an integer not lower than 1
	 */
	public RegistersImpl(int regsLen) {
		if(regsLen < 1) {
			throw new IllegalArgumentException("A microprocessor can not have less than 1 register");
		}
		this.registers = new Object[regsLen];
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#getRegisterValue(int)
	 * 
	 * Method used to retrieve the information from a specific computer register. Parameter index defines
	 * which register that is.
	 */
	@Override
	public Object getRegisterValue(int index) {
		if(index < 0 || index >= registers.length) {
			throw new IllegalArgumentException("Invalid index!");
		}
		return registers[index];
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#setRegisterValue(int, java.lang.Object)
	 * 
	 * Method used to set the information in a specific computer register. Parameter index defines
	 * which register that is.
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		if(index < 0 || index >= registers.length) {
			throw new IllegalArgumentException("Invalid index!");
		}
		if(value == null) {
			throw new IllegalArgumentException("Can not put null elements in to registers!");
		}
		registers[index] = value;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#getProgramCounter()
	 * 
	 * Method used to retrieve the information from the program counter register.
	 */
	@Override
	public int getProgramCounter() {
		return this.programCounter;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#setProgramCounter(int)
	 * 
	 * Method used to set the value in program counter register.
	 */
	@Override
	public void setProgramCounter(int value) {
		if(value < 0) {
			throw new IllegalArgumentException("Can not set program counter to value lower than 0!");
		}
		this.programCounter = value;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#incrementProgramCounter()
	 * 
	 * Method used to increase the value of the program counter by 1.
	 */
	@Override
	public void incrementProgramCounter() {
		this.programCounter++;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#getFlag()
	 * 
	 * Method used to retrieve the value of the flag register.
	 */
	@Override
	public boolean getFlag() {
		return this.flag;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Registers#setFlag(boolean)
	 * 
	 * Method used to set the value of the flag register.
	 */
	@Override
	public void setFlag(boolean value) {
		this.flag = value;
	}

}
