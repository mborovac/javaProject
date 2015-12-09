/**
 * Package holding all the classes implementing the Observer pattern for Java 6th homework
 */
package hr.fer.zemris.java.tecaj.hw6.problem1a;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;


/**
 * Class used as an Observer which keeps a log of all the Subject changes since the registration.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class LogValue implements IntegerStorageObserver {
	
	private Path path;
	
	/**
	 * Class constructor. Constructor takes the path to the log file.
	 * 
	 * @param path path to the log file, can not be null
	 */
	public LogValue(Path path) {
		if(path == null) {
			throw new IllegalArgumentException("Given path can not be null.");
		}
		this.path = path;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorageObserver#valueChanged(
	 * hr.fer.zemris.java.tecaj.hw6.problem1a.IntegerStorage)
	 * 
	 * Method keeps a log of all the Subject changes since the registration. If the file does not
	 * exist, it creates it, otherwise it only appends the new value.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(istorage == null) {
			throw new IllegalArgumentException("Given storage can not be null.");
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.path.toString(), true)))) {
		    out.println(istorage.getValue());
		}catch (IOException e) {
			System.out.println("There's a problem with the stream.");
		}
	}
}
