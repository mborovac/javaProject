/**
 * Package holding all the classes needed to implement the operation dir for Java 5th homework
 */
package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class used to support the ordering of the files that are to be printed. Class also supports negative
 * specifications.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FileComparators {
	
	private static final int POSITIVE_CONSTANT = 10;
	private static final int NEGATIVE_CONSTANT = -10;
	
	/**
	 * List of supported ordering specifications.
	 * s - size, smaller ones 1st
	 * n - name, lexicographically lower ones 1st
	 * m - time of last modification, earlier 1st
	 * t - type of file, directories 1st
	 * l - name length, shorter 1st
	 * e - can/can't be executed, the ones that can be executed are printed 1st
	 */
	private static List<String> listOfAcceptedSpecs = Arrays.asList(new String[]{
											"s", 
											"n",
											"m",
											"t", 
											"l", 
											"e"
											});
	
	
	/**
	 * Comparator used to compare by size, specification "s".
	 */
	public static final Comparator<File> BY_SIZE = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			return Long.compare(f1.length(), f2.length());
		}
	};
	
	/**
	 * Comparator used to compare by name, specification "n".
	 */
	public static final Comparator<File> BY_NAME = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			return f1.getName().compareTo(f2.getName());
		}
	};
	
	/**
	 * Comparator used to compare by last modification, specification "m".
	 */
	public static final Comparator<File> BY_LAST_MODIFIED = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			return ((Long) f1.lastModified()).compareTo((Long)f2.lastModified());
		}
	};
	
	/**
	 * Comparator used to compare by file type, specification "t".
	 */
	public static final Comparator<File> BY_TYPE = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			if(f1.isDirectory() && f2.isFile()) {
				return NEGATIVE_CONSTANT;
			} else if(f1.isFile() && f2.isDirectory()) {
				return POSITIVE_CONSTANT;
			} else {
				return 0;
			}
		}
	};
	
	/**
	 * Comparator used to compare by name length, specification "l".
	 */
	public static final Comparator<File> BY_NAME_LENGTH = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			if(f1.getName().length() < f2.getName().length()) {
				return NEGATIVE_CONSTANT;
			} else if(f1.getName().length() > f2.getName().length()) {
				return POSITIVE_CONSTANT;
			} else {
				return 0;
			}
		}
	};
	
	/**
	 * Comparator used to compare by executability, specification "e".
	 */
	public static final Comparator<File> BY_EXE = new Comparator<File>() {
		
		@Override
		public int compare(File f1, File f2) {
			if(f1.canExecute() && !f2.canExecute()) {
				return NEGATIVE_CONSTANT;
			} else if(!f1.canExecute() && f2.canExecute()) {
				return POSITIVE_CONSTANT;
			} else {
				return 0;
			}
		}
	};

	/**
	 * Class used to define a composite comparator. This type of comparator takes a list of comparators
	 * and compares the elements using all of them in the order they were given. If objects match using all
	 * comparators, they are equal.
	 * 
	 * @author MarkoB
	 * @version 1.0
	 */
	public static class CompositeComparator implements Comparator<File> {
		
		private List<Comparator<File>> comparators;

		public CompositeComparator(List<Comparator<File>> comparators) {
			super();
			this.comparators = new ArrayList<>(comparators);
		}
		
		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 * 
		 * Compares the objects using all the given comparators.
		 */
		@Override
		public int compare(File f1, File f2) {
			for(Comparator<File> c: comparators) {
				int r = c.compare(f1, f2);
				if(r != 0) {
					return r;
				}
			}
			return 0;
		}
	}
	
	/**
	 * Method used to determine which comparator was requested by the user.
	 * 
	 * @param argument the requested specification string
	 * @return the requested comparator created based on the specification string
	 */
	public static Comparator<File> createComparator(String argument) {
		String spec = argument.split(":")[1];		// extracts the part after the ":"
		boolean negative = false;
		String shortSpec = new String(spec);
		if(spec.matches("-[a-zA-Z]+")) {
			negative = true;
			shortSpec = spec.substring(1);
		}
		if(!listOfAcceptedSpecs.contains(shortSpec)) {
			System.out.println("Unsupported specification: -s:" + spec);
			System.exit(-1);
		}
		switch(shortSpec) {
				case "s": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_SIZE);
							} else {
								return FileComparators.BY_SIZE;
							}
				case "n": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_NAME);
							} else {
								return FileComparators.BY_NAME;
							}
				case "m": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_LAST_MODIFIED);
							} else {
								return FileComparators.BY_LAST_MODIFIED;
							}
				case "t": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_TYPE);
							} else {
								return FileComparators.BY_TYPE;
							}
				case "l": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_NAME_LENGTH);
							} else {
								return FileComparators.BY_NAME_LENGTH;
							}
				case "e": if(negative) {
								return Collections.reverseOrder(FileComparators.BY_EXE);
							} else {
								return FileComparators.BY_EXE;
							}
		}
		return null;
	}
}
