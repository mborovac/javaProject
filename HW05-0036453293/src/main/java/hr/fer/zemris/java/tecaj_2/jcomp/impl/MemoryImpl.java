/**
 * Package holding all the implementation classes for java 5th homework
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

/**
 * Class used to create and use the memory of a microprocessor. Class implements interface Memory.
 * 
 * @author MarkoB
 * version 1.0
 */
public class MemoryImpl implements Memory {
	
	private Object[] memory;
	
	/**
	 * Class constructor. Constructor takes a single integer argument representing the size of the memory.
	 * 
	 * @param size the size of the memory
	 */
	public MemoryImpl(int size) {
		if(size < 1) {
			throw new IllegalArgumentException("Memory size can not be lower than 1!");
		}
		this.memory = new Object[size];
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Memory#setLocation(int, java.lang.Object)
	 * 
	 * Method used to put a given object to given memory location. Locations start at index 0.
	 */
	@Override
	public void setLocation(int location, Object value) {
		if(location < 0 || location >= this.memory.length) {
			throw new IllegalArgumentException("Location is invalid!");
		}
		if(value == null) {
			throw new IllegalArgumentException("Can not add null elements to memory!");
		}
		
		this.memory[location] = value;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Memory#getLocation(int)
	 * 
	 * Method used to acquire object from the given memory location. Locations start at index 0.
	 */
	@Override
	public Object getLocation(int location) {
		if(location < 0 || location >= this.memory.length) {
			throw new IllegalArgumentException("Location is invalid!");
		}
		return this.memory[location];
	}
}
