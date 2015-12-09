/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class used to implement a copy shell command. Command copies a file to the selected directory or a new file.
 * Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method copies a file to the selected directory or a new file. Arguments must be paths to 
	 * files, "." represents the current directory.
	 * 
	 * I am aware that this method is huge, but breaking it in to smaller methods would just
	 * make it unreadable.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command cat can not take null as an argument");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length != 2) {
			out.write("Command copy takes 2 arguments");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path sourcePath;
		try {
			sourcePath = Paths.get(arguments[0]);
		} catch(InvalidPathException e) {
			out.write("1st argument is not a valid path.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path destPath;
		try {
			destPath = Paths.get(arguments[1]);
		} catch(InvalidPathException e) {
			out.write("2nd argument is not a valid path.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File sourceFile = sourcePath.toFile();
		if(!sourceFile.isFile()) {
			out.write("1st argument of the copy command must be a path leading to a file.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File destFile = destPath.toFile();
		boolean overwrite = false;
		boolean isDir = false;
		if(destFile.isDirectory()) {
			isDir = true;
			for(String child: destFile.list()) {
				if(child.equalsIgnoreCase(sourceFile.getName())) {
					out.write("The selected destination file already exists, is it allowed to overwrite? " +
							"If you select no, the contents will be appended. y/n: ");
					out.flush();
					String decision = in.readLine();		// reads from the input stream given as the argument
					if(decision.matches("[yY]")) {
						out.write("Overwriting.");
						out.flush();
						overwrite = true;
					} else if(decision.matches("[nN]")) {
						out.write("Appending.");
						out.flush();
						overwrite = false;
					} else {
						out.write("Unknown command.");
						out.flush();
						return ShellStatus.CONTINUE;
					}
				}
			}
		} else if(destFile.exists() && !destFile.isDirectory()) {
			out.write("The selected destination file already exists, is it allowed to overwrite? " +
					"If you select no, the contents will be appended. y/n: ");
			out.flush();
			String decision = in.readLine();		// reads from the input stream given as the argument
			if(decision.matches("[yY]")) {
				out.write("Overwriting.");
				out.flush();
				overwrite = true;
			} else if(decision.matches("[nN]")) {
				out.write("Appending.");
				out.flush();
				overwrite = false;
			} else {
				out.write("Unknown command.");
				out.flush();
				return ShellStatus.CONTINUE;
			}
			out.flush();
		}
		copy(sourceFile, destFile, !overwrite, isDir);
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method that does the actual copying. Method reads a fixed amount of bytes from the source file
	 * and writes them in to the new file. 
	 * 
	 * @param source source file
	 * @param dest destination file
	 * @param append flag determining whether to append to the new file or not
	 * @param isDir flag determining whether the destination path is a directory
	 * @throws IOException if something is wrong with any of the streams
	 */
	private static void copy(File source, File dest, boolean append, boolean isDir) throws IOException {
		File newDestFile = dest;
		if(isDir) {
			String destPath = dest.getPath();
			destPath += "/" + source.getPath();
			Path newDestPath = Paths.get(destPath);
			newDestFile = newDestPath.toFile();
		}
	    ensureTargetDirectoryExists(newDestFile.getParentFile());
	    InputStream inStream = null;
	    OutputStream outStream = null;
	    byte[] bucket = new byte[4*1024];
	    inStream = new BufferedInputStream(new FileInputStream(source));
	    outStream = new BufferedOutputStream(new FileOutputStream(newDestFile, append));
	    int bytesRead = 0;
	    while(bytesRead != -1){
	    	bytesRead = inStream.read(bucket);
	    	if(bytesRead > 0){
	    		outStream.write(bucket, 0, bytesRead);
	    	}	
	    }
	    inStream.close();
	    outStream.close();
	}
	  
	private static void ensureTargetDirectoryExists(File aTargetDir){
		if(!aTargetDir.exists()){
	    	aTargetDir.mkdirs();
	    }
	}
}
