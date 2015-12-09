/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Class used to implement a tree shell command. Command writes the full subtree of the given folder to the
 * selected stream. Class implements ShellCommand and FileVisitor interfaces.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand, FileVisitor<Path> {
	
	private int indent = 0;

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method used to write the full subtree of the given folder to the selected stream.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		
		if(arguments.length != 1) {
			out.write("Expected 1 argument!");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path givenPath;
		try {
			givenPath = Paths.get(arguments[0]);
		} catch (InvalidPathException e) {
			out.write("Argument of the tree command needs to be a valid path");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(!Files.isDirectory(givenPath)) {
			out.write("Argument of the tree command needs to be a path to a directory.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Files.walkFileTree(givenPath, new TreeShellCommand());
		return ShellStatus.CONTINUE;
	}

	/**
	 * @see java.nio.file.FileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
	 * 
	 * Method prints the name of the directory and increases the indentation.
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		if(indent == 0) {
			System.out.printf("%s%n", dir);
		} else {
			System.out.printf("%"+indent+"s%s%n", "", dir.getName(dir.getNameCount() - 1));
		}
		indent += 3;
		return FileVisitResult.CONTINUE;
	}

	/**
	 * @see java.nio.file.FileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
	 * 
	 * Method prints the name of the file.
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		if(Files.isRegularFile(file)) {
			System.out.printf("%"+indent+"s%s%n", "", file.getName(file.getNameCount() - 1));
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * @see java.nio.file.FileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
	 * 
	 * Method makes sure the process doesn't stop if the file can not be visited.
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	/**
	 * @see java.nio.file.FileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
	 * 
	 * Method decreases the indentation.
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		indent -= 3;
		return FileVisitResult.CONTINUE;
	}
}
