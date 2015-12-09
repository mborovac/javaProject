/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Class used to implement a symbol shell command. Command can write the current string representation for 
 * the selected symbol or change it. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {
	
	private String morelines;
	private String multiline;
	private String prompt;
	
	/**
	 * Class constructor. Constructor initiates the string representations of the symbols to the 
	 * default values.
	 */
	public SymbolShellCommand() {
		this.morelines = "\\";
		this.multiline = "|";
		this.prompt = ">";
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method used to determine and execute what the user wanted (the representation of the symbol 
	 * or the change).
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command symbol can not take null as an argument.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length == 1) {
			if(arguments[0] == null) {
				out.write("Command symbol can not take null as an argument.");
				out.flush();
				return ShellStatus.CONTINUE;
			}
			switch(arguments[0]) {
				case "PROMPT":	out.write("Symbol for PROMPT is '" + this.prompt + "'");
								out.flush();
								return ShellStatus.CONTINUE;
				case "MORELINES":	out.write("Symbol for MORELINES is '" + this.morelines + "'");
									out.flush();
								return ShellStatus.CONTINUE;
				case "MULTILINE":	out.write("Symbol for MULTILINE is '" + this.multiline + "'");
									out.flush();
								return ShellStatus.CONTINUE;
				default:	out.write("Unsupported symbol.");
							out.flush();
							return ShellStatus.CONTINUE;
			}
		} else if(arguments.length == 2) {
			if(arguments[0] == null || arguments[1] == null) {
				out.write("Command symbol can not take null as an argument.");
				out.flush();
				return ShellStatus.CONTINUE;
			}
			switch(arguments[0]) {
				case "PROMPT":	out.write("Symbol for PROMPT changed from '" 
									+ this.prompt + "' to '" + arguments[1] + "'");
								out.flush();
								this.prompt = arguments[1];
								return ShellStatus.CONTINUE;
				case "MORELINES":	out.write("Symbol for MORELINES changed from '" 
									+ this.morelines + "' to '" + arguments[1] + "'");
									out.flush();
								this.morelines = arguments[1];
								return ShellStatus.CONTINUE;
				case "MULTILINE":	out.write("Symbol for MULTILINE changed from '" 
									+ this.multiline + "' to '" + arguments[1] + "'");
									out.flush();
								this.multiline = arguments[1];
								return ShellStatus.CONTINUE;
				default:	out.write("Unsupported symbol.");
							out.flush();
							return ShellStatus.CONTINUE;
		}
		} else {
			out.write("Command symbol takes 1 or 2 arguments.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
	}

	/**
	 * Getter for symbol MORELINES.
	 * 
	 * @return the morelines string representation
	 */
	public String getMorelines() {
		return morelines;
	}

	/**
	 * Getter for symbol MULTILINE.
	 * 
	 * @return the multiline string representation
	 */
	public String getMultiline() {
		return multiline;
	}

	/**
	 * Getter for symbol PROMPT.
	 * 
	 * @return the prompt string representation
	 */
	public String getPrompt() {
		return prompt;
	}
}
