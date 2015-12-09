package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class CopyTest {
	
	@Test
	public void executeTest() throws IOException {
		CopyShellCommand copy = new CopyShellCommand();
		copy.executeCommand(new BufferedReader( new InputStreamReader(System.in)), 
				new BufferedWriter(new OutputStreamWriter(System.out)),
				new String[] {"./config.properties", "./testFile"});
	}
}
