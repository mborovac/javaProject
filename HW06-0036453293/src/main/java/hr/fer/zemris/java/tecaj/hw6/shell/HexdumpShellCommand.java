/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class used to implement a hexdump shell command. Command writes the hexdump of a file to the
 * selected stream. Class implements ShellCommand interface.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {
	
	final protected static int MAX_READ = 16;
	final protected static int OFFSET_SIZE = 8;
	final protected static char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.shell.ShellCommand#executeCommand(java.io.BufferedReader, 
	 * java.io.BufferedWriter, java.lang.String[])
	 * 
	 * Method used to write the hexdump of a file to the selected stream.
	 */
	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) throws IOException {
		if(arguments == null) {
			out.write("Command cat can not take null as an argument");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(arguments.length != 1) {
			out.write("Command hexdump takes 1 argument.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		Path givenPath;
		try {
			givenPath = Paths.get(arguments[0]);
		} catch(InvalidPathException e) {
			out.write("Argument of the hexdump command must be a path leading to a file.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		if(!Files.isReadable(givenPath)) {
			out.write("Given path does not lead to a readable file.");
			out.flush();
			return ShellStatus.CONTINUE;
		}
		File sourceFile = givenPath.toFile();
		BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(sourceFile));
		long length = sourceFile.length();
		long currentByte = 0; // for keeping track of where we are in the file, as well as progress monitoring
		int counter = 0;
		
		while (currentByte < length) { // while more of the file can be read
			int amountLeft = (int) (length - currentByte);
			int blockUnit = Math.min(MAX_READ, amountLeft);
			byte[] readBytes = new byte[blockUnit]; // read either MAX_READ, or however much is left in the file
			fileReader.read(readBytes);
			printHexadecimalView(readBytes, counter, in, out);
			currentByte += blockUnit;
			counter += 16;
		}
		fileReader.close();
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method prints the specific format of the hexdump.
	 * 
	 * @param readBytes the bytes read from the source file
	 * @param counter the number of bytes read so far
	 */
	private static void printHexadecimalView(byte[] readBytes, int counter, BufferedReader in, 
			BufferedWriter out) throws IOException {
		String offset = Integer.toString(counter, 16);
		StringBuffer buf = new StringBuffer(offset);
		while (buf.length() < OFFSET_SIZE) {
		  buf.insert(0, '0');
		}
		String output = buf.toString();
		out.write(String.format("%" + OFFSET_SIZE + "s: ", output));
//		System.out.printf("%" + OFFSET_SIZE + "s: ", output);
		String hexRepresentation = bytesToHex(readBytes);
		int length = hexRepresentation.length();
		String firstPartOfHexLine = "";
		String secondPartOfHexLine = "";
		for(int i = 0; i < length; i++) {
			if(i < MAX_READ) {
				firstPartOfHexLine += hexRepresentation.charAt(i);
				firstPartOfHexLine += hexRepresentation.charAt(i += 1);
				if(i != MAX_READ - 1) {
					firstPartOfHexLine += " ";
				}
				
			} else {
				secondPartOfHexLine += hexRepresentation.charAt(i);
				secondPartOfHexLine += hexRepresentation.charAt(i += 1);
				if(i != length - 1) {
					secondPartOfHexLine += " ";
				}
			}
		}
		out.write(String.format("%-23s", firstPartOfHexLine));
		out.write("|");
		out.write(String.format("%-23s", secondPartOfHexLine));
		out.write(" | ");
//		System.out.printf("%-23s", firstPartOfHexLine);
//		System.out.print("|");
//		System.out.printf("%-23s", secondPartOfHexLine);
//		System.out.print(" | ");
		for(int i = 0; i < readBytes.length; i++) {
			if(readBytes[i] < 32 || readBytes[i] > 127) {
				String dot = ".";
				readBytes[i] = dot.getBytes("UTF-8")[0];
			}
		}
		String decoded = new String(readBytes, "UTF-8");
		out.write(decoded + "\n");
		out.flush();
	}
	
	/**
	 * Method used to transform an array of bytes in to a hexadecimal string.
	 * 
	 * @param byteArray the array of bytes
	 * @return returns the hexadecimal string representing the given array of bytes
	 */
	private static String bytesToHex(byte[] byteArray) {
		int arraylength = byteArray.length;
	    char[] hexChars = new char[arraylength * 2];
	    for (int i = 0; i < arraylength; i++) {
	        int v = byteArray[i] & 0xFF;
	        hexChars[i * 2] = HEX_DIGITS[v >>> 4];
	        hexChars[i * 2 + 1] = HEX_DIGITS[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
