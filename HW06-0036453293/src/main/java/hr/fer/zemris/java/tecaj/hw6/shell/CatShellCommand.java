/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class used to implement a cat shell command. Command writes the contents of a file to the
 * selected stream. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method reads the contents of a file and writes it to the given output stream.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command cat can not take null as an argument");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length < 1 || arguments.length > 2) {
			out.write("Command cat takes 1 or 2 arguments.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path givenPath;
		try {
			givenPath = Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			out.write("1st argument of command cat needs to be a valid path.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Charset givenCharset = Charset.defaultCharset();
		if(arguments.length == 2) {
			if(!Charset.isSupported(arguments[1])) {
				out.write("Given charset is not supported: " + arguments[1]);
				out.flush();
				return ShellStatus.CONTINUE;
			}
			givenCharset = Charset.forName(arguments[1]);
		}
		if(!Files.isReadable(givenPath)) {
			out.write("Given path does not lead to a readable file.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(arguments[0]), givenCharset));
		String fileContents = fileReader.readLine();
		while(fileContents != null) {
			out.write(fileContents + "\n");
			fileContents = fileReader.readLine();
		}
		out.flush();
		fileReader.close();
		return ShellStatus.CONTINUE;
	}
}
