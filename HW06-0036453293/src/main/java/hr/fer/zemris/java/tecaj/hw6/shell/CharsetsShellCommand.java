/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map.Entry;

/**
 * Class used to implement a charsets shell command. Command writes all the available charsets to the
 * selected stream. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method writes all the available charsets to the selected stream.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments.length != 0) {
			out.write("Command charsets takes no arguments.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		for(Entry<String, Charset> charset: Charset.availableCharsets().entrySet()) {
			out.write(charset.getValue().toString() + "\n");
		}
		out.flush();
		return ShellStatus.CONTINUE;
	}
}
