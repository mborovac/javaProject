package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to test the execution of the smart scripts.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class drugi {
	
	/**
	 * Method main used to run the program.
	 * 
	 * @param args program takes no arguments, all passed arguments will be ignored
	 */
	public static void main(String[] args) {
		
		String documentBody = readFromDisk("./lib/zbrajanje.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
	
	/**
	 * Method used to read the script contents from the disk.
	 * 
	 * @param string path to the script
	 * @return returns the document body of the script
	 */
	private static String readFromDisk(String string) {
		
		File file = new File(string);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		} catch (IOException e1) {
			throw new RuntimeException("Can not create a buffered reader for selected file, reason unknown.");
		}
		String docBody = "";
		String str;
		try {
			while ((str = reader.readLine()) != null) {
				docBody += str + "\n";
			}
		} catch (IOException e) {
			throw new RuntimeException("Can not read from selected file, reason unknown.");
		}
		try {
			reader.close();
		} catch (IOException ignore) {
		}
		return docBody;
	}
}
