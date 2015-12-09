package hr.fer.zemris.java.filechecking;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Files used to test this class can be found in directory test in this project. It is an archive 
 * containing only one empty folder named testFile.
 */
public class FCFileVerifierTest {
	
	@Test
	public void VerifierTest() {
		File file = new File("./test/testFile.zip"); // ZIP arhiva
		String program = 	"format zip @\"Wrong format.\"{\n" +
				"!exists dir \"VjezbaUpisi/.settings\" @\"Exists\" { } \n" +
				"def src \"homework04/src/main/java\" \n" +
				"exists dir \"homework04\" @\"Does not exist.\" { } \n" +
				"exists d \"homework06\" \n" +
				"exists fi \"homework06/file.txt\"  @\"File does not exist.\" { }\n" +
				"!exists f \"homework/file.txt\"  @\"File exists.\" { }\n" +
				"def dir1 \"${src}:hr.fer.zemris.java.tecaj.hw5.problem1a\" \n"+
				"filename i\"${jmbag}-dz1.zip\" @\"Wrong file name.\" { } \n" +
				"fail @\"I failed.\"\n" +
				"terminate\n" +
				"}";
		String fileName = "0012345678-dz1.zip";
		Map<String,Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0012345678");
		FCFileVerifier verifier = new FCFileVerifier(file, fileName, program, initialData);
		assertEquals(true, verifier.hasErrors());
		assertEquals("Does not exist.", verifier.errors().get(0));
		assertEquals("The directory homework06/ does not exist.", verifier.errors().get(1));
		assertEquals("File does not exist.", verifier.errors().get(2));
		assertEquals("I failed.", verifier.errors().get(3));
	}
}
