package hr.fer.zemris.java.filechecking.lexical;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilecheckingTokenizerTest {
	
	@Test(expected=FilecheckingTokenizerException.class)
	public void TokenizerTestException1() {
		String program = "";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingToken token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.EOF, token.getTokenType());
		tokenizer.nextToken();
	}
	
	@Test(expected=FilecheckingTokenizerException.class)
	public void TokenizerTestException2() {
		String program = "";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		tokenizer.nextToken();
		tokenizer.nextToken();
	}
	
	@Test
	public void TokenizerTest1() {
		String program = 	"fail @\"Random message.\"\n" +
							"# line of comments   ";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingToken token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.KEYWORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.MONKEY, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.STRING_CONTENT, token.getTokenType());
		assertEquals(new String("Random message."), token.getValue());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.EOF, token.getTokenType());
	}
	
	@Test
	public void TokenizerTest2() {
		String program = 	"def jm_1bag \"0012345678\"" +
							"filename i\"${jm_1bag}-dz1.zip\"";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingToken token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.KEYWORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.IDENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.STRING_CONTENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.KEYWORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.IDENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.STRING_CONTENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.EOF, token.getTokenType());
	}
	
	@Test
	public void TokenizerTest3() {
		String program = 	"def src \"home\"" +
							"def index ${src}/index";
		FilecheckingTokenizer tokenizer = new FilecheckingTokenizer(program);
		FilecheckingToken token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.KEYWORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.IDENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.STRING_CONTENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.KEYWORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.IDENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.CURLY_BRACKET_RESOLVE, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.RESOLVE_WORD, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.CURLY_BRACKET_CLOSED, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.SLASH, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.IDENT, token.getTokenType());
		tokenizer.nextToken();
		token = tokenizer.getCurrentToken();
		assertEquals(FilecheckingTokenType.EOF, token.getTokenType());
	}
}
