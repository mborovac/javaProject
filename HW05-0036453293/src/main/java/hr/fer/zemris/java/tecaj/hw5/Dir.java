/**
 * Package holding all the classes needed to implement the operation dir for Java 5th homework
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to support the operation dir used to print the contents of the given directory
 * with specified comparison, print and file specifications.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Dir implements Comparator<File> {

	/**
	 * Main class of the program. It takes at least one argument. 1st argument must be the path
	 * to the desired directory, the rest can be desired specifications.
	 * 
	 * -s:X specification sets file parameters by which they will be ordered. Supported orderings
	 * can be found in class FileComparators in this package.
	 * 
	 * -w:X specification defines which file parameters will be printed. Supported parameters
	 * can be found in class PrintSpecs in this package.
	 * 
	 * -f:X specification defines which filters the printed files must fulfill. Supported filters
	 * can be found in class FilterSpecs in this package.
	 * 
	 * -X:-Y declares a negative specification, it does the opposite of what the positive one does.
	 * 
	 * @param args 1st argument must be the path to the desired directory, the rest can be desired
	 * specifications, can not take less than 1 argument.
	 * @throws IOException if less than 1 argument was given or given specifications aren't well-formed
	 */
	public static void main(String[] args) {
		
		if(args.length < 1) {
			throw new IllegalArgumentException("Expected at least 1 argument!");
		}
		List<Comparator<File>> listOfComparators = new ArrayList<>();
		List<String> listOfPrintSpecs = new ArrayList<>();
		Map<String, Integer> filterSpecs = new LinkedHashMap<>();
		if(args.length > 1) {
			for(int i = 1; i < args.length; i++) {
				String argument = args[i];
				if(argument.matches("-s:[-]?[a-zA-Z]")) {
					Comparator<File> comparator = FileComparators.createComparator(argument);
					listOfComparators.add(comparator);
				} else if(argument.matches("-w:[a-zA-Z]")) {
					String printSpec = PrintSpecs.createPrintSpec(argument);
					listOfPrintSpecs.add(printSpec);
				} else if(argument.matches("-f:[-]?[a-zA-Z][0-9]*")) {
					filterSpecs = FilterSpecs.createFilterSpec(argument, filterSpecs);
				} else {
					System.out.println("Unknown specification: " + args[i]);
					System.exit(-1);
				}
			}
		}
		if(listOfPrintSpecs.isEmpty()) {
			listOfPrintSpecs.add("n");
		}
		printSubfileNames(new File(args[0]), listOfComparators, listOfPrintSpecs, filterSpecs);
	}

	/**
	 * Private method used to print the desired files from the given directory.
	 * 
	 * @param file directory
	 * @param listOfComparators list of file parameters by which they will be ordered
	 * @param listOfPrintSpecs list of file parameters that will be printed
	 * @param filterSpecs list of filters the printed files must fulfill to be printed
	 */
	private static void printSubfileNames(File file, List<Comparator<File>> listOfComparators,
			List<String> listOfPrintSpecs, Map<String, Integer> filterSpecs) {
		List<File> listOfSubFiles = new ArrayList<File>();
		if(!file.isDirectory()) {
			System.out.println("File at given path is not a directory!");
		} else {
			File[] subFiles = file.listFiles();
			if(subFiles.length < 1) {
				System.out.println("Given directory is empty.");
			} else {
				for(File subFile: subFiles) {
					listOfSubFiles.add(subFile);
				}
			}
		}
		
		listOfSubFiles = FilterSpecs.filterSubFiles(listOfSubFiles, filterSpecs);
		if(listOfSubFiles.isEmpty()) {
			System.out.println("No files fit the desired filters.");
			System.exit(-1);
		}
		Collections.sort(
				listOfSubFiles, 
				new FileComparators.CompositeComparator(listOfComparators)
				);
		
		PrintSpecs.setSizeOfPrintedSpecs(listOfSubFiles, listOfPrintSpecs);
		PrintSpecs.printBorder(listOfPrintSpecs);
		System.out.println();
		PrintSpecs.printFiles(listOfSubFiles, listOfPrintSpecs);
		PrintSpecs.printBorder(listOfPrintSpecs);
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(File f1, File f2) {
		return f1.compareTo(f2);
	}
}
