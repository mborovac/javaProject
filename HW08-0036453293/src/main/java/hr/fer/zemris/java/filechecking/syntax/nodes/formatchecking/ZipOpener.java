/**
 * Package holding all the classes needed to support multiple file formats in Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax.nodes.formatchecking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class used to open a .zip archive.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class ZipOpener {
	
	private File file;
	private String type;
	
	/**
	 * Class constructor. Constructor takes 2 arguments, an archive that is to be opened and the
	 * type of file that fits the requirements.
	 * 
	 * @param file archive that is to be opened
	 * @param type type of file that fits the requirements
	 */
	public ZipOpener(File file, String type) {
		this.file = file;
		this.type = type;
	}
	
	/**
	 * Method used to open the file and create a list of names of files that are inside the archive
	 * and are of the requested type.
	 * 
	 * @return returns the created list of file names
	 */
	public List<String> execute() {
		List<String> listOfZipEntries = new ArrayList<>();
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
			while(zipEntries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) zipEntries.nextElement();
				if(this.type == "dir") {
					if(entry.isDirectory()) {
						listOfZipEntries.add(entry.getName());
					}
				} else if (this.type == "file") {
					if(!entry.isDirectory()) {
						listOfZipEntries.add(entry.getName());
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("An error occured during zip archive checking: " + e.getMessage());
		} finally {
			try {
				zipFile.close();
			} catch (IOException ignore) {
				
			}
		}
		return listOfZipEntries;
	}
}
