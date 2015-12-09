package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class CharsetsTest {
	
	@Test
	public void executeTest() throws IOException {
		CharsetsShellCommand charsets = new CharsetsShellCommand();
		charsets.executeCommand(new BufferedReader( new InputStreamReader(System.in)), 
				new BufferedWriter(new OutputStreamWriter(System.out)),
				new String[0]);
	}
}
