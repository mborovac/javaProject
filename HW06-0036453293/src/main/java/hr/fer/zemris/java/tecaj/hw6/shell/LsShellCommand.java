/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class used to implement a ls shell command. Command writes the contents of a directory to the
 * selected stream. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method used to write the contents of a directory to the selected stream.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command ls can not take null as an argument");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length != 1) {
			out.write("Command ls takes a single argument");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		try {
			Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			out.write("Argument of the ls command needs to be a valid path.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File givenFile = new File(arguments[0]);
		if(!givenFile.isDirectory()) {
			out.write("Given path does not lead to a directory.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File[] children = givenFile.listFiles();
		for(File child: children) {
			printFileInfo(child, in, out);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method used to print the information of a single file to the given stream.
	 * 
	 * @param file the given source file
	 * @throws IOException if something is wrong with the stream
	 */
	private void printFileInfo(File file, BufferedReader in, BufferedWriter out) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, 
				LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		out.write(drwx(file));
		out.write(String.format(" %10d ", file.length()));
		out.write(formattedDateTime);
		out.write(" " + file.getName() + "\n");
		out.flush();
	}
	
	/**
	 * Method used to determine whether the file is a directory (d), readable (r), writeable (w) and
	 * executeable (x).
	 *  
	 * @param file given source file
	 * @return returns the string containing letters with the meaning mentioned in the method description
	 */
	private String drwx(File file) {
		String firstColumn;
		if(file.isDirectory()) {
			firstColumn = "d";
		} else {
			firstColumn = "-";
		}
		if(file.canRead()) {
			firstColumn += "r";
		} else {
			firstColumn += "-";
		}
		if(file.canWrite()) {
			firstColumn += "w";
		} else {
			firstColumn += "-";
		}
		if(file.canExecute() && !file.isDirectory()) {
			firstColumn += "x";
		} else {
			firstColumn += "-";
		}
		return firstColumn;
	}
}
