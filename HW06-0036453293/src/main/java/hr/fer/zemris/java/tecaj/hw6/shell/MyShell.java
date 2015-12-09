/**
 * Package holding all the classes used to implement MyShall in Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to implement a custom shell. Class supports commands: help, exit, charsets, cat, 
 * ls, tree, copy, mkdir, hexdump and symbol.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class MyShell {

	private static String[] supportedOperations = {"exit", "charsets", "cat", "ls", "tree", "copy", 
													"mkdir", "hexdump", "symbol"};
	
	/**
	 * Main method used to run the program.
	 * 
	 * @param args program arguments, 1st one must be a command, rest command parameters
	 * @throws IOException if something is wrong with one of the streams
	 */
	public static void main(String[] args) throws IOException {
		
		Map<String, ShellCommand> commands = setup();
		
		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status = null;
		do {
			String prompt = ((SymbolShellCommand) commands.get("symbol")).getPrompt();
			String morelines = ((SymbolShellCommand) commands.get("symbol")).getMorelines();
			String multiline = ((SymbolShellCommand) commands.get("symbol")).getMultiline();
			System.out.print(prompt + " ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			while(s.endsWith(morelines)) {
				System.out.print(multiline + " ");
				s += br.readLine();
			}
			s = s.replace(morelines, " ");
			String[] splitS = s.split("\\s+");
			String commandName = splitS[0];
			if(commandName.matches("[hH][eE][lL][pP]")) {
				System.out.println("Supported commands: ");
				for(String operation: supportedOperations) {
					System.out.print(operation + " ");
				}
				System.out.println();
				status = ShellStatus.CONTINUE;
				continue;
			}
			String[] arguments = new String[splitS.length - 1];
			for(int i = 1; i < splitS.length; i++) {
				arguments[i - 1] = splitS[i];
			}
			ShellCommand command = commands.get(commandName);
			if(command == null) {
				System.out.println("Unsupported command!");
				status = ShellStatus.CONTINUE;
				continue;
			}
			try {
				status = command.executeCommand(new BufferedReader(new InputStreamReader(System.in, "UTF-8")), 
						new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8")), arguments);
			} catch (IOException e) {
				System.out.println("Something is wrong with one of the streams.");
				status = ShellStatus.CONTINUE;
			}
		} while(status != ShellStatus.TERMINATE);
	}
	
	/**
	 * Method fills the private map of commands with supported commands.
	 * 
	 * @return returns the map of supported commands
	 */
	private static Map<String, ShellCommand> setup() {
		
		Map<String, ShellCommand> commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		return commands;
	}
}
