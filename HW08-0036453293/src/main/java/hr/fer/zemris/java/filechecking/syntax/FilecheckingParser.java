/**
 * Package containing classes used in syntax analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.syntax;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.filechecking.lexical.FilecheckingTokenType;
import hr.fer.zemris.java.filechecking.lexical.FilecheckingTokenizer;
import hr.fer.zemris.java.filechecking.syntax.nodes.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ExistsStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FailStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilecheckingNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.FilenameStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.FormatStatement;
import hr.fer.zemris.java.filechecking.syntax.nodes.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.nodes.TerminateStatement;

/**
 * Class used to implement a parser for file checking.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingParser {

	/**
	 * Source code tokenizer.
	 */
	private FilecheckingTokenizer tokenizer;
	/**
	 * Parsing tree.
	 */
	private ProgramNode programNode;
	
	/**
	 * Class constructor.
	 * @param tokenizer source code tokenizer
	 * @throws VLangSyntaxException in case of an error during parsing
	 * @throws VLangTokenizerException in case of an error during tokenization
	 */
	public FilecheckingParser(FilecheckingTokenizer tokenizer) {
		this.tokenizer = tokenizer;
		programNode = parse();
	}

	/**
	 * Method used to retrieve the parsing tree.
	 * @return returns parsing tree
	 */
	public ProgramNode getProgramNode() {
		return programNode;
	}
	
	/**
	 * Method used to check whether the given token is of needed type.
	 * 
	 * @param type needed type
	 * @return  returns <code>true</code> if it is of the same type, <code>false</code> otherwise
	 */
	private boolean isTokenOfType(FilecheckingTokenType type) {
		return tokenizer.getCurrentToken().getTokenType() == type;
	}
	
	/**
	 * Method used to parse the source code.
	 * 
	 * @return parsing tree
	 */
	private ProgramNode parse() {
		List<FilecheckingNode> statements = new ArrayList<>();
		while(true) {
			// End of file
			if(isTokenOfType(FilecheckingTokenType.EOF)) {
				break;
			}
			boolean inverted = false;
			// "!"
			if(isTokenOfType(FilecheckingTokenType.EXCLAMATION)) {
				inverted = true;
				tokenizer.nextToken();
			}
			// Keyword
			if(!isTokenOfType(FilecheckingTokenType.KEYWORD)) {
				throw new FilecheckingSyntaxException("Keyword expected.");
			}
			// "def":
			if("def".equals(tokenizer.getCurrentToken().getValue())) {
				if(inverted) {
					throw new FilecheckingSyntaxException("Def can not be inverted.");
				}
				tokenizer.nextToken();
				statements.add(parseDef());
				continue;
			}
			// "terminate":
			if("terminate".equals(tokenizer.getCurrentToken().getValue())) {
				if(inverted) {
					throw new FilecheckingSyntaxException("Terminate can not be inverted.");
				}
				tokenizer.nextToken();
				statements.add(new TerminateStatement());
				continue;
			}
			// "exists":
			if("exists".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				statements.add(parseExists(inverted));
				continue;
			}
			// "filename":
			if("filename".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				statements.add(parseFilename(inverted));
				continue;
			}
			// "format":
			if("format".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				statements.add(parseFormat(inverted));
				continue;
			}
			// "fail":
			if("fail".equals(tokenizer.getCurrentToken().getValue())) {
				tokenizer.nextToken();
				statements.add(parseFail(inverted));
				continue;
			}
			// Unknown:
			throw new FilecheckingSyntaxException("Unexpected keyword found.");
		}
		// Finish
		return new ProgramNode(statements);
	}

	/**
	 * Method used to parse the "def" statement.
	 * 
	 * @return a node representing the "def" statement 
	 */
	private FilecheckingNode parseDef() {
		if(!isTokenOfType(FilecheckingTokenType.IDENT)) {
			throw new FilecheckingSyntaxException("Identifier was expected.");
		}
		String variableName = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		if(!isTokenOfType(FilecheckingTokenType.STRING_CONTENT)) {
			throw new FilecheckingSyntaxException("A string was expected.");
		}
		String variableValue = (String)tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		return new DefStatement(variableName, variableValue);
	}
	
	/**
	 * Method used to parse the "exists" statement.
	 * 
	 * @param inverted whether the statement is inverted
	 * @return a node representing the "exists" statement 
	 */
	private FilecheckingNode parseExists(boolean inverted) {
		if(!isTokenOfType(FilecheckingTokenType.IDENT)) {
			throw new FilecheckingSyntaxException("An identifier was expected as 1st argument of exists.");
		}
		String fileType = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		if(!isTokenOfType(FilecheckingTokenType.STRING_CONTENT)) {
			throw new FilecheckingSyntaxException("A string was expected as 2nd argument of exists.");
		}
		String filePath = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		String errorMessage = parseErrorMessage();
		List<FilecheckingNode> blockStatements = parseInstrucionBlock();
		return new ExistsStatement(fileType, filePath, errorMessage, blockStatements, inverted);
	}
	
	/**
	 * Method used to parse the "filename" statement.
	 * 
	 * @param inverted whether the statement is inverted
	 * @return a node representing the "filename" statement 
	 */
	private FilecheckingNode parseFilename(boolean inverted) {
		boolean caseInsensitive = false;
		if(isTokenOfType(FilecheckingTokenType.IDENT)) {
			if(!((String) tokenizer.getCurrentToken().getValue()).matches("i")) {
				throw new FilecheckingSyntaxException("Only letter 'i' can stand before a string in " +
						"command filename.");
			}
			caseInsensitive = true;
			tokenizer.nextToken();
		}
		if(!isTokenOfType(FilecheckingTokenType.STRING_CONTENT)) {
			throw new FilecheckingSyntaxException("A string was expected as the argument of command filename.");
		}
		String expectedFileName = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		String errorMessage = parseErrorMessage();
		List<FilecheckingNode> blockStatements = parseInstrucionBlock();
		return new FilenameStatement(expectedFileName, !caseInsensitive, errorMessage, 
				blockStatements, inverted);
	}
	
	/**
	 * Method used to parse the "format" statement.
	 * 
	 * @param inverted whether the statement is inverted
	 * @return a node representing the "format" statement 
	 */
	private FilecheckingNode parseFormat(boolean inverted) {
		if(!isTokenOfType(FilecheckingTokenType.IDENT)) {
			throw new FilecheckingSyntaxException("An identifier was expected as the argument of command format.");
		}
		String expectedFileName = (String) tokenizer.getCurrentToken().getValue();
		tokenizer.nextToken();
		String errorMessage = parseErrorMessage();
		List<FilecheckingNode> blockStatements = parseInstrucionBlock();
		return new FormatStatement(expectedFileName, errorMessage, 
				blockStatements, inverted);
	}
	
	/**
	 * Method used to parse the "fail" statement.
	 * 
	 * @param inverted whether the statement is inverted
	 * @return a node representing the "fail" statement 
	 */
	private FilecheckingNode parseFail(boolean inverted) {
		String errorMessage = parseErrorMessage();
		List<FilecheckingNode> blockStatements = parseInstrucionBlock();
		return new FailStatement(errorMessage, blockStatements, inverted);
	}
	
	/**
	 * Method used to parse the error message of the statement.
	 * 
	 * @return returns the string containing the error message
	 */
	private String parseErrorMessage() {
		if(isTokenOfType(FilecheckingTokenType.MONKEY)) {
			tokenizer.nextToken();
			if(!isTokenOfType(FilecheckingTokenType.STRING_CONTENT)) {
				throw new FilecheckingSyntaxException("A string was expected as error message.");
			}
			String errorMessage = (String) tokenizer.getCurrentToken().getValue();
			tokenizer.nextToken();
			return errorMessage;
		}
		return null;
	}
	
	/**
	 * Method used to parse the contents of a statement block.
	 * 
	 * @return returns the list of nodes representing the statements from the block
	 */
	private List<FilecheckingNode> parseInstrucionBlock() {
		List<FilecheckingNode> blockStatements = new ArrayList<>();
		if(isTokenOfType(FilecheckingTokenType.CURLY_BRACKET_OPEN)) {
			tokenizer.nextToken();
			while(!isTokenOfType(FilecheckingTokenType.CURLY_BRACKET_CLOSED)) {
				boolean invertedBlockStatement = false;
				// "!"
				if(isTokenOfType(FilecheckingTokenType.EXCLAMATION)) {
					invertedBlockStatement = true;
					tokenizer.nextToken();
				}
				// "def":
				if("def".equals(tokenizer.getCurrentToken().getValue())) {
					if(invertedBlockStatement) {
						throw new FilecheckingSyntaxException("Def can not be inverted.");
					}
					tokenizer.nextToken();
					blockStatements.add(parseDef());
					continue;
				}
				// "terminate":
				if("terminate".equals(tokenizer.getCurrentToken().getValue())) {
					if(invertedBlockStatement) {
						throw new FilecheckingSyntaxException("Terminate can not be inverted.");
					}
					tokenizer.nextToken();
					blockStatements.add(new TerminateStatement());
					continue;
				}
				// "exists":
				if("exists".equals(tokenizer.getCurrentToken().getValue())) {
					tokenizer.nextToken();
					blockStatements.add(parseExists(invertedBlockStatement));
					continue;
				}
				// "filename":
				if("filename".equals(tokenizer.getCurrentToken().getValue())) {
					tokenizer.nextToken();
					blockStatements.add(parseFilename(invertedBlockStatement));
					continue;
				}
				// "format":
				if("format".equals(tokenizer.getCurrentToken().getValue())) {
					tokenizer.nextToken();
					blockStatements.add(parseFormat(invertedBlockStatement));
					continue;
				}
				// "fail":
				if("fail".equals(tokenizer.getCurrentToken().getValue())) {
					tokenizer.nextToken();
					blockStatements.add(parseFail(invertedBlockStatement));
					continue;
				}
			}
			tokenizer.nextToken();
		}
		return blockStatements;
	}
}
