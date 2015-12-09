/**
 * Package holding all the classes needed to support multiple file formats in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes.formatchecking;


import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Class used to detect the format of the given file and open the file.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FileOpener {
	
	private File file;
	private String type;
	
	 private static final byte[] MAGIC = {'P', 'K', 0x3, 0x4};
	
	/**
	 * Class constructor. Constructor takes 2 arguments, a file and the type of file needed,
	 * file or directory.
	 * 
	 * @param file the file being checked/opened
	 * @param type file or directory
	 */
	public FileOpener(File file, String type) {
		this.file = file;
		this.type = type;
	}
	
	/**
	 * Method used to call the adequate file opener.
	 * 
	 * @return returns a list of file names found in the file that suit the file type
	 * given in the constructor
	 */
	public List<String> useAdequateOpener() {
		if(isZipFile(this.file)) {
			new ZipOpener(this.file, this.type).execute();
		}
		return null;
	}
	
	/**
	 * Method used to detect the format of the given file.
	 * 
	 * @param requestedFormat string representing the requested format
	 * @return returns true if the file is of the requested format, false otherwise
	 */
	public boolean detectFormat(String requestedFormat) {
		if(requestedFormat.compareTo("zip") == 0) {
			return isZipFile(this.file);
		}
		return false;
	}
	
	/**
	 * Method used to check whether the given file is a .zip archive.
	 * 
	 * @param file file that is to be checked
	 * @return returns true if the file is a .zip archive, false otherwise
	 */
	private static boolean isZipFile(File file) {
		 
		  boolean isZip = true;
		  byte[] buffer = new byte[MAGIC.length];
		  try {
		   RandomAccessFile raf = new RandomAccessFile(file, "r");
		   raf.readFully(buffer);
		   for (int i = 0; i < MAGIC.length; i++) {
		    if (buffer[i] != MAGIC[i]) {
		     isZip = false;
		     break;
		    }
		   }
		   raf.close();
		  } catch (Throwable e) {
		   isZip = false;
		  }
		  return isZip;
		 }
}
