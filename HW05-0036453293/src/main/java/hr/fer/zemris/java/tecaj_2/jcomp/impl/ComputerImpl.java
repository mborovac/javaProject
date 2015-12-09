/**
 * Package holding all the implementation classes for java 5th homework
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Class used to create and use a simple computer with some memory and registers. Class implements Computer interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ComputerImpl implements Computer{
	
	private Memory memory;
	private Registers registers;
	
	/**
	 * Class constructor. Constructor takes 2 integers representing the size of the memory and the number
	 * of the registers.
	 * 
	 * @param memoryLocations size of the memory, an integer not lower than 1
	 * @param registers number of the registers, an integer not lower than 1
	 */
	public ComputerImpl(int memoryLocations, int registers) {
		if(memoryLocations < 1) {
			throw new IllegalArgumentException("A computer can not have memory with less than 1 location!");
		}
		if(registers < 1) {
			throw new IllegalArgumentException("A computer can not have less than 1 register!");
		}
		
		this.memory = new MemoryImpl(memoryLocations);
		this.registers = new RegistersImpl(registers);
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Computer#getRegisters()
	 * 
	 * Method returns the registers of the computer.
	 */
	@Override
	public Registers getRegisters() {
		return this.registers;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Computer#getMemory()
	 * 
	 * Method returns the memory of the computer.
	 */
	@Override
	public Memory getMemory() {
		return this.memory;
	}
}
