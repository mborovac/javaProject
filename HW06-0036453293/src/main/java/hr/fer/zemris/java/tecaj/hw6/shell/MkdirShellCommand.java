/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class used to implement a mkdir shell command. Command creates a new directory and all needed 
 * parent directories. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method used to create a new directory.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command mkdir can not take null as an argument.\n");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length != 1) {
			out.write("Command mkdir takes a single argument.\n");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path givenPath;
		try {
			givenPath = Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			out.write("Argument of the mkdir command needs to be a valid path.\n");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File givenFile = givenPath.toFile();
		if(givenFile.exists()) {
			out.write("Given file already exists.\n");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(givenFile.mkdirs()) {
			out.write("Directory created.\n");
		} else {
			out.write("An error occured during file creation.\n");
		}
		out.flush();
		return ShellStatus.CONTINUE;
	}
}
