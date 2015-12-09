/**
 * Package holding all the classes and methods for 2nd and 3rd assignment of Java 4th HW
 * 
 */
package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class used to read user input (only names made of letters) until user types "Quit", save all the names and
 * at the end print out how many times each name was typed.
 * 
 * 
 * Croatian letters š,đ,č,ć,ž don't work well with the program even though the input reader is set to 
 * read UTF-8 characters. It could be a local problem.
 * 
 * @author MarkoB
 * @version 1.0
 */
public final class NamesCounter {
	
	/**
	 * Class constructor.
	 */
	private NamesCounter() {
		
	}

	/**
	 * Method main executes the program. It takes no arguments, all passed arguments will be ignored.
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Map<String, Integer> mapOfNames = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		String input;
		do {
			input = br.readLine();
			if(input == null) {
				continue;
			}
			input = input.trim();
			if(input.compareToIgnoreCase("Quit") == 0) {
				break;
			}
			if(!input.matches("[\\p{L}]+")) {
				System.out.println("That is not a name!");
				continue;
			}
			if(mapOfNames.containsKey(input)) {
				int numberOfOccurences = mapOfNames.get(input);
				mapOfNames.put(input, numberOfOccurences + 1);
			} else {
				mapOfNames.put(input, 1);
			}
		} while(true);
		
		for(Entry<String, Integer> mapElements: mapOfNames.entrySet()) {
			System.out.println(mapElements.getKey() + " was given " + mapElements.getValue() + " time(s).");
		}
	}
}
