/**
 * Package holding all the classes needed to implement the operation dir for Java 5th homework
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class used to support the printing of the files that fulfill only the given filters. Class also supports negative
 * specifications.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilterSpecs {
	
	/**
	 * List of supported filter specifications that must be followed by numbers.
	 * s - size, only files with size lower or equal to the number following this specification fulfill it
	 * l - name length, only files with name length lower or equal to the number following this specification 
	 * 		fulfill it
	 */
	private static List<String> listOfAcceptedFiltersWithNumbers = Arrays.asList(new String[]{
											"s", 
											"l"
											});
	
	/**
	 * List of supported filter specifications that must stand alone.
	 * f - file type, only files fulfill it, directories don't
	 * e - extension, only files with extension fulfill it, directories don't by default
	 */
	private static List<String> listOfAcceptedFiltersWithoutNumbers = Arrays.asList(new String[]{
											"f",
											"e"
											});
	
	/**
	 * Method used to determine which filter was requested by the user.
	 * 
	 * @param argument string representing the requested filter
	 * @param filterSpecs map of all of the filter specifications processed until now
	 * @return returns the same given map of the processed filter specifications with the new one added
	 */
	public static Map<String, Integer> createFilterSpec(String argument, Map<String, Integer> filterSpecs) {
		String filter = argument.split(":")[1];
		boolean negative = false;
		String shortFilter = new String(filter);
		if(filter.matches("-[a-zA-Z]+[0-9]*")) {
			negative = true;
			shortFilter = filter.substring(1);
		}
		if(shortFilter.matches("[a-zA-Z]")) {
			if(!listOfAcceptedFiltersWithoutNumbers.contains(shortFilter)) {
				System.out.println("Unsupported specification: -f:" + filter);
				System.exit(-1);
			} else {
				if(negative) {
					filterSpecs.put("-" + shortFilter, 0);
				} else {
					filterSpecs.put(shortFilter, 0);
				}
			}
		} else if(shortFilter.matches("[a-zA-Z][0-9]+")) {
			if(!listOfAcceptedFiltersWithNumbers.contains(shortFilter.substring(0, 1))) {
				System.out.println("Unsupported specification: -f:" + filter);
				System.exit(-1);
			} else {
				if(negative) {
					filterSpecs.put("-" + shortFilter.substring(0, 1), 
							Integer.parseInt(shortFilter.substring(1)));
				} else {
					filterSpecs.put(shortFilter.substring(0, 1), 
						Integer.parseInt(shortFilter.substring(1)));
				}
			}
		}
		return filterSpecs;
	}
	
	/**
	 * Method used to filter the subfiles of the given directory.
	 * 
	 * @param listOfSubFiles the list of subfiles to be filtered
	 * @param listOfFilterSpecs list of user-given filter specifications
	 * @return returns the filtered list of subfiles
	 */
	public static List<File> filterSubFiles(List<File> listOfSubFiles, Map<String, Integer> listOfFilterSpecs) {
		List<File> listOfAcceptedFiles = new ArrayList<>();
		for(File subFile: listOfSubFiles) {
			boolean fulfilsAll = true;
			for(Entry<String, Integer> filterSpec: listOfFilterSpecs.entrySet()) {
				if(!fileFulfilsFilterSpec(subFile, filterSpec)) {
					fulfilsAll = false;
				}
			}
			if(fulfilsAll) {
				listOfAcceptedFiles.add(subFile);
			}
		}
		return listOfAcceptedFiles;
	}
	
	/**
	 * Method used to check whether the given file fulfills the given filter specification.
	 * 
	 * @param file the file to be checked
	 * @param filterSpec the filter specification the file needs to fulfill
	 * @return returns true if the file fulfills it, false otherwise
	 */
	private static boolean fileFulfilsFilterSpec(File file, Entry<String, Integer> filterSpec) {
		boolean negative = false;
		String filter = filterSpec.getKey();
		if(filterSpec.getKey().startsWith("-")) {
			negative = true;
			filter = filterSpec.getKey().substring(1);
		}
		boolean returnValue = false;
		switch(filter) {
				case "s":	if(file.length() <= filterSpec.getValue()) {
								returnValue = true;
							} else {
								returnValue = false;
							}
							break;
					
				case "f":	if(file.isFile()) {
								returnValue = true;
							} else {
								returnValue = false;
							}
							break;
					
				case "l":	if(file.getName().length() <= filterSpec.getValue()) {
								returnValue = true;
							} else {
								returnValue = false;
							}
							break;
					
				case "e":	if(file.isFile() && file.getName().contains(".")) {
								returnValue = true;
							} else {
								returnValue = false;
							}
							break;
		}
		if(negative) {
			return !returnValue;
		}
		return returnValue;
	}
}
