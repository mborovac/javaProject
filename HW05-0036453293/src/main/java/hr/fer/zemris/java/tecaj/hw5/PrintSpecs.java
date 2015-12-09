/**
 * Package holding all the classes needed to implement the operation dir for Java 5th homework
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class used to support the printing of different file parameters.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class PrintSpecs {
	
	private static final int SIZE_OF_TIME_FORMAT = 19;
	
	/**
	 * List of supported file parameter specifications.
	 * n - name
	 * t - type
	 * s - size in bytes
	 * m - time of last modification
	 * h - hidden
	 */
	private static List<String> listOfAcceptedSpecs = Arrays.asList(new String[]{
											"n", 
											"t",
											"s",
											"m", 
											"h"
											});
	private static List<Integer> sizeOfPrintedSpecs = new ArrayList<>();
	
	/**
	 * Method used to set the elements of the private list sizeOfPrintedSpecs to the biggest needed value.
	 * This method is needed so the printed field would be aligned.
	 * 
	 * @param files list of files that will be printed
	 * @param listOfPrintSpecs list of user-required printing specifications
	 */
	public static void setSizeOfPrintedSpecs(List<File> files, List<String> listOfPrintSpecs) {
		for(int i = 0; i < listOfAcceptedSpecs.size(); i++) {
			sizeOfPrintedSpecs.add(null);
		}
		for(int i = 0; i < listOfAcceptedSpecs.size(); i++) {
			if(!listOfPrintSpecs.contains(listOfAcceptedSpecs.get(i))) {
				sizeOfPrintedSpecs.set(i, 0);
			} else {
				switch (listOfPrintSpecs.get(i)) {
						case "n":	int fileNameLength = 0;
									for(int f = 0; f < files.size(); f++) {
										int currFileNameLength = files.get(f).getName().length();
										if(currFileNameLength > fileNameLength) {
											fileNameLength = currFileNameLength;
										}
									}
									sizeOfPrintedSpecs.set(0, fileNameLength);
									break;
							
						case "t": 	sizeOfPrintedSpecs.set(1, 1);
							
						case "s":	int fileSizeLength = 0;
									for(int f = 0; f < files.size(); f++) {
										int currFileSizeLength = Long.toString(files.get(f).length()).length();
										if(currFileSizeLength > fileSizeLength) {
											fileSizeLength = currFileSizeLength;
										}
									}
									sizeOfPrintedSpecs.set(2, fileSizeLength);
									break;
							
						case "m":	sizeOfPrintedSpecs.set(3, SIZE_OF_TIME_FORMAT);
									break;
							
						case "h":	sizeOfPrintedSpecs.set(4, 1);
									break;
				}
			}
		}
	}
	
	/**
	 * Method used to print the required file parameter.
	 * 
	 * @param file the file whose parameter should be printed
	 * @param spec the string representation of the specification
	 */
	private static void printFileSpec(File file, String spec) {
		switch(spec) {
		case "n": 	System.out.print(String.format("%-" + sizeOfPrintedSpecs.get(0) + "s", file.getName()));
					break;
					
		case "t": 	if(file.isFile()) {
						System.out.print("f");
					}
					if(file.isDirectory()) {
						System.out.print("d");
					}
					break;
		
		case "s": 	System.out.print(String.format("%" + sizeOfPrintedSpecs.get(2) + "s", file.length()));
					break;
		
		case "m": 	Date date = new Date(file.lastModified());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					System.out.print(sdf.format(date));
					break;
		
		case "h": 	if(file.isHidden()) {
						System.out.print("h");
					} else {
						System.out.print(" ");
					}
					break;
		}
	}
	
	/**
	 * Method used to print the required files and all of their parameters
	 *  
	 * @param listOfSubFiles list of files to be printed
	 * @param listOfPrintSpecs list of parameters that need to be printed for each file
	 */
	public static void printFiles(List<File> listOfSubFiles, List<String> listOfPrintSpecs) {
		for(File fileFromList: listOfSubFiles) {
			for(String spec: listOfPrintSpecs) {
				System.out.print("| ");
				PrintSpecs.printFileSpec(fileFromList, spec);
				System.out.print(" ");
			}
			System.out.print(" |");
			System.out.println();
		}
	}
	
	/**
	 * Method used to print the border of the printed field.
	 * 
	 * @param listOfPrintSpecs list of the user-required file parameters that need to be printed
	 */
	public static void printBorder(List<String> listOfPrintSpecs) {
		for(String spec: listOfPrintSpecs) {
			int printSpecIndex = 0;
			switch(spec) {
					case "n": 	printSpecIndex = 0;
								break;
					
					case "t": 	printSpecIndex = 1;
								break;
					
					case "s": 	printSpecIndex = 2;
								break;
					
					case "m": 	printSpecIndex = 3;
								break;
					
					case "h": 	printSpecIndex = 4;
								break;
			}
			if(sizeOfPrintedSpecs.get(printSpecIndex) == 0) {
				continue;
			} else {
				System.out.print("+-");
				for(int i = 0; i < sizeOfPrintedSpecs.get(printSpecIndex); i++) {
					System.out.print("-");
				}
			}
			System.out.print("-");
		}
		System.out.print("-+");
	}
	
	/**
	 * Method used to determine which specification was requested by the user.
	 * 
	 * @param argument string representation of the specification
	 * @return returns the requested specification
	 */
	public static String createPrintSpec(String argument) {
		String spec = argument.split(":")[1];		// extracts the part after the ":"
		if(!listOfAcceptedSpecs.contains(spec)) {
			System.out.println("Unsupported specification: -w:" + spec);
			System.exit(-1);
		}
		return spec;
	}
}
