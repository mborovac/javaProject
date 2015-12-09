package hr.fer.zemris.java.filechecking.syntax;

import static org.junit.Assert.*;

import java.util.List;

import hr.fer.zemris.java.filechecking.lexical.FilecheckingTokenizer;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilecheckingNode;

import org.junit.Test;

public class FilecheckingParserTest {
	
	@Test(expected=FilecheckingSyntaxException.class)
	public void ParserTestException() {
		String program = 	"unknown dir \"homework04\"";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		new FilecheckingParser(tokenizer);
	}
	
	@Test
	public void ParserTest1() {
		String program = 	"format zip \n" +
				"!exists dir \"VjezbaUpisi/.settings\"\n" +
				"def src \"homework04/src/main/java\"\n" +
				"exists dir \"homework04\"\n" +
				"filename i\"${jmbag}-dz1.zip\"\n" +
				"fail @\"Datoteka koju ste uploadali nije dozvoljenog formata.\"\n" +
				"terminate";
		
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingParser parser = new FilecheckingParser(tokenizer);
		List<FilecheckingNode> statements = parser.getProgramNode().getStatements();
		assertEquals(7, statements.size());
	}
	
	@Test
	public void ParserTest2() {
		String program = 	"format zip @\"Wrong format.\"{\n" +
				"!exists dir \"VjezbaUpisi/.settings\" @\"Does not exist\"\n" +
				"def src \"homework04/src/main/java\"\n" +
				"exists dir \"homework04\"\n" +
				"filename i\"${jmbag}-dz1.zip\"\n" +
				"fail @\"Datoteka koju ste uploadali nije dozvoljenog formata.\"\n" +
				"terminate\n" +
				"}";
		
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingParser parser = new FilecheckingParser(tokenizer);
		List<FilecheckingNode> statements = parser.getProgramNode().getStatements();
		assertEquals(1, statements.size());
	}
}
