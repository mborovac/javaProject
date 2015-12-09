/**
 * Package holding all the classes and methods for the student database assignment of 4th Java HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating and managing a student database read from a text file.
 * 
 * Croatian letters š,đ,č,ć,ž don't work well with the program even though the input reader is set to 
 * read UTF-8 characters. It could be a local problem.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class StudentDB {
	
	/**
	 * Class constructor.
	 */
	private StudentDB() {
		
	}

	/**
	 * Method main for executing the program. Method takes no arguments, all given arguments will be ignored.
	 * Method reads the database.txt file from the current directory and creates a new student database
	 * from the file info. After that it starts and infinite loop awaiting for user input. Program accepts
	 * 2 types of input: > query jmbag="0123456789" or > query lastName="Smith". If the input is valid, the
	 * program will check the database and print out all the records that fit the requested query. Program
	 * stops once the user types "Quit".
	 * 
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
				);
		StudentDatabase database = new StudentDatabase(lines);
		
		runLoop(database);
	}
		
	/**
	 * Private method used to run the loop on user input until "Quit" is typed. Method checks the given input
	 * and calls the appropriate method.
	 * 
	 * @param database database upon which the program works
	 * @throws IOException
	 */
	private static void runLoop(StudentDatabase database) throws IOException {
		
		if(database == null) {
			throw new IllegalArgumentException("Database is invalid!");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in), "UTF-8"));
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		String input;
		do {
			System.out.println("Awaiting user input:");
			input = br.readLine();
			if(input == null) {
				continue;
			}
			input = input.trim();
			if(input.compareToIgnoreCase("Quit") == 0) {
				System.out.println("Finishing...");
				break;
			}
			List<StudentRecord> queryRecords = new ArrayList<StudentRecord>();
			if(input.matches("> query[\\s]+[jJ][mM][bB][aA][gG][\\s]*=[\\s]*\"[0-9]{10}\"")) {
				String[] splitInput = input.split("[\"]+");
				queryRecords.add(database.forJMBAG(splitInput[1]));
			} else if(input.matches(
					"> query[\\s]+[lL][aA][sS][tT][nN][aA][mM][eE][\\s]*=[\\s]*\"[\\p{L}]+[*]?[\\p{L}]*\"")) {
				String[] splitInput = input.split("[\"]+");
				String lastNameRegex = splitInput[1].replace("*", ".*");
				queryRecords = database.filter(new LastNameFilter(lastNameRegex));
			} else {
				System.err.println("Invalid command!");
				System.err.println("Example of usage: > query jmbag=\"0123456789\" or > query lastName=\"Smith\"");
				continue;
			}
			if(queryRecords.isEmpty()) {
				System.out.println("No results found.");
				continue;
			} else {
				printQueryResults(queryRecords);
			}
		} while(true);
	}
	
	/**
	 * Private method used to print the results of the requested query.
	 * 
	 * @param queryRecords records to be printed.
	 */
	private static void printQueryResults(List<StudentRecord> queryRecords) {
		int lastNameSize = 0;
		int firstNameSize = 0;
		for(StudentRecord student: queryRecords) {
			if(student.getLastName().length() > lastNameSize) {
				lastNameSize = student.getLastName().length();
			}
			if(student.getFirstName().length() > firstNameSize) {
				firstNameSize = student.getFirstName().length();
			}
		}
		printBorder(lastNameSize, firstNameSize);
		for(StudentRecord student: queryRecords) {
			System.out.printf("\n| " + String.format("%010d", student.getJmbag()) + " | " + 
					String.format("%-" + lastNameSize +"s", student.getLastName()) + " | " + 
					String.format("%-" + firstNameSize +"s", student.getFirstName()) + " | " 
					+ student.getFinalGrade() + " |");
		}
		System.out.println();
		printBorder(lastNameSize, firstNameSize);
		System.out.println();
	}
	
	/**
	 * Method used to print the border of the results.
	 * 
	 * @param lastNameSize length of the longest last name to be printed
	 * @param firstNameSize length of the longest first name to be printed
	 */
	private static void printBorder(int lastNameSize, int firstNameSize) {
		if(lastNameSize < 1 || firstNameSize < 1) {
			throw new IllegalArgumentException("Last name or first name can not have length 0");
		}
		System.out.print("+============+=");
		for(int i = 0; i < lastNameSize; i++) {
			System.out.print("=");
		}
		System.out.print("=+=");
		for(int i = 0; i < firstNameSize; i++) {
			System.out.print("=");
		}
		System.out.print("=+===+");
	}
}
