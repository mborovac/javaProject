/**
 * Package holding all the classes of supported microprocessor methods for java 5th homework.
 */
package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to support the microprocessor operation iinput. Operation reads a single line from standard input.
 * It interprets the given info as Integer and saves it to the given memory location.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class InstrIinput implements Instruction {
	
	private int location;
	
	/**
	 * class constructor. Constructor takes one argument, the memory location at which given info should
	 * be saved.
	 * 
	 * @param arguments memory location where the given info should be saved
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.location = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * @see hr.fer.zemris.java.tecaj_2.jcomp.Instruction#execute(hr.fer.zemris.java.tecaj_2.jcomp.Computer)
	 * 
	 * Method reads from the standard input and saves the info as an Integer to the given memory location.
	 * Sets the flag to true if everything is fine, false otherwise.
	 */
	@Override
	public boolean execute(Computer computer) {
		Integer number = null;
		Scanner in = new Scanner(System.in, "UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(!computer.getRegisters().getFlag()) {
			try {
				System.out.print("Unesite početni broj: ");
				number = Integer.parseInt(br.readLine());
				computer.getRegisters().setFlag(true);
			} catch (NumberFormatException e) {
				System.out.println("Unos nije moguće protumačiti kao cijeli broj.");
				computer.getRegisters().setFlag(false);
			} catch (IOException e) {
			}
		}
		in.close();
		computer.getMemory().setLocation(location, number.intValue());
		return false;
	}
}
