/**
 * Package holding the classes used in the lexical analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.lexical;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class used to perform the lexical analysis of the file and create the lexical tokens.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class FilecheckingTokenizer {
	
	/**
	 * Array of characters containing the original file.
	 */
	private char[] data;
	/**
	 * Cursor to the current position in the <code>data</code> array.
	 */
	private int curPos;
	/**
	 * The last created token.
	 */
	private FilecheckingToken currentToken;
	
	/**
	 * A map of tokens created from a single character.
	 */
	private static final Map<Character, FilecheckingTokenType> MAPPER;
	
	// Initialization of the map
	static {
		MAPPER = new HashMap<>();
		MAPPER.put(Character.valueOf(':'), FilecheckingTokenType.COLON);
		MAPPER.put(Character.valueOf(';'), FilecheckingTokenType.SEMICOLON);
		MAPPER.put(Character.valueOf(','), FilecheckingTokenType.COMMA);
		MAPPER.put(Character.valueOf('.'), FilecheckingTokenType.POINT);
		MAPPER.put(Character.valueOf('-'), FilecheckingTokenType.HYPHEN);
		MAPPER.put(Character.valueOf('!'), FilecheckingTokenType.EXCLAMATION);
		MAPPER.put(Character.valueOf('"'), FilecheckingTokenType.QUOTATION_MARK);
		MAPPER.put(Character.valueOf('/'), FilecheckingTokenType.SLASH);
		MAPPER.put(Character.valueOf('$'), FilecheckingTokenType.RESOLVE);
		MAPPER.put(Character.valueOf('{'), FilecheckingTokenType.CURLY_BRACKET_OPEN);
		MAPPER.put(Character.valueOf('}'), FilecheckingTokenType.CURLY_BRACKET_CLOSED);
		MAPPER.put(Character.valueOf('@'), FilecheckingTokenType.MONKEY);
	}
	
	/**
	 * Set of keywords.
	 */
	private static final Set<String> KEYWORDS;
	
	// Initialization of the set
	static {
		KEYWORDS = new HashSet<>();
		KEYWORDS.add("terminate");
		KEYWORDS.add("def");
		KEYWORDS.add("exists");
		KEYWORDS.add("filename");
		KEYWORDS.add("format");
		KEYWORDS.add("fail");
	}
	
	/**
	 * Class constructor. Constructor takes a single argument, the String representation 
	 * of the file.
	 * @param program the String representation of the file
	 * @throws VLangTokenizerException if an error occurred during the tokenization
	 */
	public FilecheckingTokenizer(String program) {
		data = program.toCharArray();
		curPos = 0;
		extractNextToken();
	}
	
	/**
	 * Method retrieves the current token. Multiple calls of the same method return the same token.
	 * 
	 * @return current token
	 */
	public FilecheckingToken getCurrentToken() {
		return currentToken;
	}
	
	/**
	 * Method used to create a new token. The token is set as the current token.
	 * 
	 * @throws VLangTokenizerException if an error occurred during the tokenization
	 */
	public FilecheckingToken nextToken() {
		extractNextToken();
		return getCurrentToken();
	}
	
	/**
	 * Method used to create a token.
	 * 
	 * @throws VLangTokenizerException if an error occurred during the tokenization
	 */
	private void extractNextToken() {
		// Error because EOF was already detected
		if(currentToken != null && currentToken.getTokenType() == FilecheckingTokenType.EOF) {
			throw new FilecheckingTokenizerException("No tokens available."); 
		}
		
		skipBlanks();
		
		// EOF token
		if(curPos >= data.length) {
			currentToken = new FilecheckingToken(FilecheckingTokenType.EOF, null);
			return;
		}
		
		// Skip comments
		while(data[curPos] == '#') {
			curPos++;
			while(curPos < data.length && data[curPos] != '\n' && data[curPos] != '\r') {
				curPos++;
			}
			if(curPos >= data.length) {
				currentToken = new FilecheckingToken(FilecheckingTokenType.EOF, null);
				return;
			}
			curPos++;
			skipBlanks();
		}
		
		skipBlanks();
		
		// Tokens created from a single character
		FilecheckingTokenType mappedType = MAPPER.get(Character.valueOf(data[curPos]));
		if(mappedType != null) {
			// Create the token
			currentToken = new FilecheckingToken(mappedType, null);
			// Increment the cursor
			curPos++;
			// resolve curly bracket, not normal curly bracket
			if(currentToken != null && currentToken.getTokenType() == FilecheckingTokenType.RESOLVE) {
				if(data[curPos] != '{') {
					throw new FilecheckingTokenizerException("Character '$' must be followed by a '{' character!");
				}
				currentToken = new FilecheckingToken(FilecheckingTokenType.CURLY_BRACKET_RESOLVE, null);
				curPos++;
				return;
			} else if(currentToken.getTokenType() == FilecheckingTokenType.COLON) {
				// package
				if(!Character.isLetter(data[curPos])) {
					throw new FilecheckingTokenizerException("Character ':' represents a package and package " +
							"must start with a letter!");
				}
				int startIndex = curPos;
				curPos++;
				while(curPos < data.length && (Character.isLetterOrDigit(data[curPos]) || 
						data[curPos] == '_' || data[curPos] == '.')) {
					curPos++;
				}
				int endIndex = curPos;
				String value = new String(data, startIndex, endIndex - startIndex);
				currentToken = new FilecheckingToken(FilecheckingTokenType.PACKAGE_IDENT, value);
				return;
			} else if(currentToken.getTokenType() == FilecheckingTokenType.QUOTATION_MARK) {
				// string
				if(data[curPos] == '"') {
					throw new FilecheckingTokenizerException("String can not be empty!");
				}
				int startIndex = curPos;
				curPos++;
				while(curPos < data.length && data[curPos] != '"') {
					curPos++;
				}
				int endIndex = curPos;
				String value = new String(data, startIndex, endIndex-startIndex);
				currentToken = new FilecheckingToken(FilecheckingTokenType.STRING_CONTENT, value);
				if(data[curPos] == '"') {
					curPos++;
				}
				return;
			}
			return;
		}
		
		// Something else
		boolean resolve = false;
		if(Character.isLetter(data[curPos]) || data[curPos] == '_') {
			if(currentToken != null && currentToken.getTokenType() == FilecheckingTokenType.CURLY_BRACKET_RESOLVE) {
				resolve = true;
			}
			
			int startIndex = curPos;
			curPos++;
			while(curPos < data.length && (Character.isLetterOrDigit(data[curPos]) || 
					data[curPos] == '_' || data[curPos] == '.')) {
				curPos++;
			}
			int endIndex = curPos;
			String value = new String(data, startIndex, endIndex - startIndex);
			if(KEYWORDS.contains(value)) {
				currentToken = new FilecheckingToken(FilecheckingTokenType.KEYWORD, value);
				return;
			}
			if(resolve) {
				currentToken = new FilecheckingToken(FilecheckingTokenType.RESOLVE_WORD, value);
			} else {
				currentToken = new FilecheckingToken(FilecheckingTokenType.IDENT, value);
			}
			return;
		}
		
		// Unknown
		throw new FilecheckingTokenizerException("Invalid character found: '" + data[curPos] + "'.");
	}
	
	/**
	 * Method used to skip all the blanks.
	 */
	private void skipBlanks() {
		while(curPos < data.length) {
			char c = data[curPos];
			if(c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				break;
			} else {
				curPos++;
			}
		}
	}
}
