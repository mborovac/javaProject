package hr.fer.zemris.java.filechecking;

import static org.junit.Assert.*;

import org.junit.Test;

public class FCProgramCheckerTest {
	
	@Test
	public void CheckerTest() {
		String program = 	"format zip @\"Wrong format.\"{\n" +
				"!exists dir \"VjezbaUpisi/.settings\" @\"Does not exist\"\n" +
				"def src \"homework04/src/main/java\"\n" +
				"exists dir \"homework04\"\n" +
				"filename i\"${jmbag}-dz1.zip\"\n" +
				"fail @\"Datoteka koju ste uploadali nije dozvoljenog formata.\"\n" +
				"terminate\n" +
				"}";
		FCProgramChecker checker = new FCProgramChecker(program);
		assertEquals(false, checker.hasErrors());
	}
}
