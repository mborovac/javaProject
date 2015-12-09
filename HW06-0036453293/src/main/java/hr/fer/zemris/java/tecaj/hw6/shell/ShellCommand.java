/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Interface defining a single method required to implement a shell command.
 * 
 * @author MarkoB
 * @version 1.0
 */
public interface ShellCommand {
	
	/**
	 * Method required to implement a shell command.
	 * 
	 * @param in input stream
	 * @param out output stream
	 * @param arguments user-given command arguments
	 * @return returns a shell status, continue if everything is ok, terminate if the shell 
	 * 		should be terminated
	 * @throws IOException if something is wrong with one of the streams
	 */
	ShellStatus executeCommand(BufferedReader in, BufferedWriter out, String[] arguments) 
			throws IOException;
}
