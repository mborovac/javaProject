/**
 * Package holding the file checking demonstration program for Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.filechecking.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker;

/**
 * Class used to demonstrate the file checking.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FCDemo {
	
	/**
	 * Method used to execute the program. Method takes 3 arguments: path to the file that will be checked,
	 * program that will be used to check the file and the original file name.
	 * 
	 * @param args path to the file that will be checked, program that will be used to check the file and 
	 * the original file name
	 * @throws IOException if program can not be opened
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 3) {
			throw new IllegalArgumentException("Expected 3 arguments: a path to a file, path to the " +
					"program with rules and the original file name of the given file.");
		}
		File file = new File(args[0]); // ZIP arhiva
		String program = ucitaj(args[1]); // učitaj program iz datoteke
		String fileName = args[2]; // stvarno ime arhive
		FCProgramChecker checker = new FCProgramChecker(program);
		if(checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");
			for(String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}
		Map<String,Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0012345678");
		initialData.put("lastName", "Perić");
		initialData.put("firstName", "Pero");
		FCFileVerifier verifier = new FCFileVerifier(
				file, fileName, program, initialData);
		if(!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		} else {
			System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for(String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}
	/**
	 * Method used to read the program that defines the rules.
	 * 
	 * @param fileName name of the file defining the rules
	 * @return returns the string representation of the file
	 * @throws IOException if file can not be opened
	 */
	private static String ucitaj(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		String line;
		line = br.readLine();
		while(line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		br.close();
		return sb.toString();
	}
}