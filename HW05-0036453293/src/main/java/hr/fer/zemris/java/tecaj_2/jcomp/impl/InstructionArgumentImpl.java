/**
 * Package holding all the implementation classes for java 5th homework
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class used only for testing other classes.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstructionArgumentImpl implements InstructionArgument {
	
	private Object value;
	private boolean isRegister;
	
	/**
	 * Constructor. Constructor takes 2 arguments, a value and a flag whether the value is from a register.
	 * 
	 * @param value value
	 * @param isRegister flag whether the value is from a register
	 */
	public InstructionArgumentImpl(Object value, boolean isRegister) {
		
		this.value = value;
		this.isRegister = isRegister;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument#isRegister()
	 */
	@Override
	public boolean isRegister() {
		return this.isRegister;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument#isString()
	 */
	@Override
	public boolean isString() {
		return value instanceof String;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument#isNumber()
	 */
	@Override
	public boolean isNumber() {
		if(!isRegister) {
			if(!isString()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}
}
